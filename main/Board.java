package main;

import map.*;
import object.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.geom.AffineTransform;

public class Board extends JPanel implements ActionListener{

    public SpaceShip spaceship;
    private Map map;
    private boolean ingame;
    AffineTransform af2 = new AffineTransform();
    AffineTransform af = new AffineTransform();
    Graphics2D g2;
    BufferedImage bgImage;
    private Keys k;
    public Minimap minimap;
    public List<Missile> missiles;

    public Board() throws IOException {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        ingame = true;
        setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));
        spaceship = new SpaceShip(Constants.ICRAFT_X, Constants.ICRAFT_Y);
        this.map = new Map();
        this.missiles = new ArrayList<>();
        this.k = new Keys(this);
        this.minimap = new Minimap(spaceship, map);
        Constants.timer = new Timer(20,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                spaceship.moveTime2 = System.currentTimeMillis();
                inGame();
                minimap.repaint();
                //checkcollision avant de move avec les ordonnees de move
                if(!checkCollision()){
                    updateShip();
                }   
                // System.out.println(spaceship.SPEED);
                updateMissiles();
                // updateBall();
                spaceship.rotateRight(spaceship.rightRotationFlag);
                spaceship.rotateLeft(spaceship.leftRotationFlag);
                repaint();
            }
        });
        Constants.timer.start();
    }

    @Override
    public void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        if (ingame) {
            try {
                drawObjects(g);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            drawGameOver(g);
        }
    }

    private void drawObjects(Graphics2D g) throws IOException {
        //create image comme une seconde image de map et renouvelle à chaque repaint
        BufferedImage image = new BufferedImage(2400,2400,BufferedImage.TYPE_INT_BGR);
        Graphics2D g3=image.createGraphics();
        g3.drawImage(map.img_map, 0, 0, null);

        bgImage= new BufferedImage(800,600,BufferedImage.TYPE_INT_BGR);
        g2 = bgImage.createGraphics();

        if (spaceship.isVisible()) {

            for (Missile missile : this.missiles) {
                //paint Missile dans la seconde image
                if(missile instanceof MissileDiffusion){
                    MissileDiffusion represent=(MissileDiffusion)missile;
                    for(MissileNormale tmp:represent.getDiffusion()){
                        g3.drawImage(tmp.getImage(), (int)tmp.getX(), (int)tmp.getY(),null);
                    }
                }
                else{
                    System.out.println("Missile: " + missile.getX() + " " + missile.getY());
                    g3.drawImage(missile.getImage(), (int)missile.getX(), (int)missile.getY(),null);
                }
            }

            //Camera affiche la position de vaisseau dans le seconde image
            g2.drawImage(image,(int)(-spaceship.getX())+400,(int)(-spaceship.getY())+300, null);

            //Vaisseau
            af.setToIdentity();
            af.translate(Constants.B_WIDTH/2, Constants.B_HEIGHT/2);
            af.rotate(Math.toRadians(spaceship.rotation),spaceship.getImage().getWidth(this)/2, spaceship.getImage().getHeight(this)/2);
            g2.drawImage(spaceship.getImage(),af,null);
            //Boule
            if(map.ball.isTaken()) g.drawImage(map.ball.getImage(), af2, null);
            af2.setToIdentity();
            af2.translate(Constants.B_WIDTH/2, Constants.B_HEIGHT/2+40);
            g2.drawString("Ball: " + map.ball.isTaken(), 2, 20);
            // g.setColor(Color.WHITE);

            //afficher dans g
            g.drawImage(bgImage, 0, 0, null);
        }
    }

    private void drawGameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (Constants.B_WIDTH - fm.stringWidth(msg)) / 2, Constants.B_HEIGHT / 2);
    }

    private void inGame() {
        if (!ingame) {
            Constants.timer.stop();
        }
    }

    private void updateShip() {
        if (spaceship.isVisible()) {
            spaceship.move();
            spaceship.acceleration();
            spaceship.deceleration();
        }
    }

    private void updateMissiles() {
        List<Missile> ms = this.missiles;
        for (Missile m : this.missiles) {
            if (m.isVisible()) {
                m.move();
            } else {
                ms.remove(m);
            }
        }
    }

    public void fire() {
        missiles.add(new MissileDiffusion(spaceship.getX(), spaceship.getY(), spaceship.rotation));
    }

    public boolean checkCollision(){
        List<Missile> ms = this.missiles;
        for (Obstacle obstacle : map.ListeObstacle) {
            Rectangle o=new Rectangle(obstacle.x[0],obstacle.y[0],obstacle.x[1]-obstacle.x[0],obstacle.y[2]-obstacle.y[1]);
            //ship apres le movement
            Rectangle s=new Rectangle((int)(spaceship.getX()+spaceship.SPEED * Math.cos(Math.toRadians(spaceship.rotation))),(int)(spaceship.getY()+spaceship.SPEED*Math.sin(Math.toRadians(spaceship.rotation))),16,16);
            if(s.intersects(o) && !(obstacle instanceof Goal) && !(obstacle instanceof Ball)){
                return true;
            }else if(s.intersects(o) && obstacle instanceof Ball){
                map.ball.take();
            }else if(s.intersects(o) && obstacle instanceof Goal && map.ball.isTaken()){
                ingame = false;
            }
            // for (Missile m : ms) {
            //     Rectangle r1 = m.getBounds();
            //     if(r1.getBounds().intersects(o)){
            //         m.rebounce++;
            //         return true;
            //     }
            // }
        }
        return false;
    }


    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            k.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            k.keyPressed(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    }

}