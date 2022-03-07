package main;

import spaceship.*;
import map.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.geom.AffineTransform;

public class Board extends JPanel implements ActionListener{

    private SpaceShip spaceship;
    private Ball ball;
    private Map map;
    private boolean ingame;
    AffineTransform af2 = new AffineTransform();
    AffineTransform af = new AffineTransform();
    Graphics2D g2d;
    BufferedImage bgImage;
    private Keys k;
    public Minimap minimap;

    public Board() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        ingame = true;
        setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));
        spaceship = new SpaceShip(Constants.ICRAFT_X, Constants.ICRAFT_Y);
        this.ball = new Ball(Constants.BALL_X,Constants.BALL_Y);
        this.map = new Map();
        this.k = new Keys(spaceship);
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
                System.out.println(spaceship.SPEED);
                updateMissiles();
                updateBall();
                checkCollision();
                spaceship.rotateRight(spaceship.rightRotationFlag);
                spaceship.rotateLeft(spaceship.leftRotationFlag);
                spaceship.acceleration();
                spaceship.deceleration();
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (ingame) {
            drawObjects(g);
        } else {
            drawGameOver(g);
        }
    }

    private void drawObjects(Graphics g) {
        //g.drawImage(map.img_map, 0, 0, null);
        bgImage = new BufferedImage(800,600,BufferedImage.TYPE_INT_BGR);
        g2d = bgImage.createGraphics();
        if (spaceship.isVisible()) {
            g2d.drawImage(map.img_map,(int)(-spaceship.getX())+400,(int)(-spaceship.getY())+300, null);
            List<Missile> ms = spaceship.getMissiles();
            for (Missile missile : ms) {
                if (missile.isVisible()) {
                    g2d.drawImage(missile.getImage(), (int)missile.getX(), (int)missile.getY(), this);
                }
            }
            af.setToIdentity();
            af.translate(Constants.B_WIDTH/2, Constants.B_HEIGHT/2);
            af.rotate(Math.toRadians(spaceship.rotation),spaceship.getImage().getWidth(this)/2, spaceship.getImage().getHeight(this)/2);
            g2d.drawImage(spaceship.getImage(),af,null);
            g.setColor(Color.WHITE);
            g.drawImage(bgImage, 0, 0, null);
        }
    }

    private void drawGameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (Constants.B_WIDTH - fm.stringWidth(msg)) / 2,
        Constants.B_HEIGHT / 2);
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
            // System.out.println("CanMove = " + spaceship.moveFlag);
            // System.out.println("CanAccelerate = " + spaceship.canAccelerate);
            // System.out.println("CanDecelerate = " + spaceship.canDecelerate);
        }
    }

    private void updateBall(){
        if (ball.isTaken()){
            ball.move(spaceship.getX(), spaceship.getY());
        }
    }

    private void updateMissiles() {
        List<Missile> ms = spaceship.getMissiles();
        for (int i = 0; i < ms.size(); i++) {

            Missile m = ms.get(i);
            if (m.isVisible()) {
                m.move();
            } else {
                ms.remove(i);
            }
        }
    }

    public boolean checkCollision(){
        List<Missile> ms = spaceship.getMissiles();
        for (Obstacle obstacle : map.ListeObstacle) {
            Rectangle o=new Rectangle(obstacle.x[0],obstacle.y[0],obstacle.x[1]-obstacle.x[0],obstacle.y[2]-obstacle.y[1]);
            //ship apres le movement
            Rectangle s=new Rectangle((int)(spaceship.getX()+spaceship.SPEED * Math.cos(Math.toRadians(spaceship.rotation))),(int)(spaceship.getY()+spaceship.SPEED*Math.sin(Math.toRadians(spaceship.rotation))),16,16);
            if(s.intersects(o)){
                return true;
            }
            for (Missile m : ms) {
                Rectangle r1 = m.getBounds();
                if(r1.getBounds().intersects(o)){
                    m.rebounce++;
                    return true;
                }
            }
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