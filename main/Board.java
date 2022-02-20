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

    public Board() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        ingame = true;
        setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));
        spaceship = new SpaceShip(Constants.ICRAFT_X, Constants.ICRAFT_Y);
        this.ball = new Ball(Constants.BALL_X,Constants.BALL_Y);
        this.map = new Map(spaceship);
        Constants.timer = new Timer(20,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(spaceship.SPEED > spaceship.MAX_SPEED){
                    Constants.canAccelerate = false; 
                }
                Constants.moveTime = System.currentTimeMillis();
                inGame();
                updateShip();
                updateMissiles();
                updateBall();
                checkCollisions();
                spaceship.rotateRight(Constants.rightRotationFlag);
                spaceship.rotateLeft(Constants.leftRotationFlag);
                spaceship.acceleration(Constants.moveFlag);
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
        bgImage = new BufferedImage(Constants.B_WIDTH, Constants.B_HEIGHT, BufferedImage.TYPE_INT_RGB);
        g2d = bgImage.createGraphics();
        if (spaceship.isVisible()) {
            g2d.setPaint(Color.BLACK);
            af.setToIdentity();
            af.translate(spaceship.getX(), spaceship.getY());
            af.rotate(Math.toRadians(SpaceShip.rotation),spaceship.getImage().getWidth(this)/2, spaceship.getImage().getHeight(this)/2);
            af2.setToIdentity();
            af2.translate(ball.getX(), ball.getY());
            g2d.drawImage(spaceship.getImage(),af,this);
            g2d.drawImage(ball.getImage(), af2, this);
            //image de map
            g.drawImage(bgImage,0,0,this);
        }
        List<Missile> ms = spaceship.getMissiles();
        for (Missile missile : ms) {
            if (missile.isVisible()) {
                g.drawImage(missile.getImage(), (int)missile.getX(), (int)missile.getY(), this);
            }
        }
        g.setColor(Color.WHITE);
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
            spaceship.move(Constants.moveFlag);
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

    public void checkCollisions() {
        Rectangle r3 = spaceship.getBounds();
        Rectangle b = ball.getBounds();
        List<Missile> ms = spaceship.getMissiles();
        for (Missile m : ms) {
            Rectangle r1 = m.getBounds();
        }
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            spaceship.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            spaceship.keyPressed(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    }

}