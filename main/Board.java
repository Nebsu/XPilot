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

    public static Timer timer;
    private SpaceShip spaceship;
    private Ball ball;
    private Map map;
    private final int BALL_X = 50;
    private final int BALL_Y = 70;
    private boolean ingame;
    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;
    AffineTransform identityTrans = new AffineTransform();
    AffineTransform af = new AffineTransform();
    Graphics2D g2dbf;
    BufferedImage bgImage;
    public static boolean rightRotationFlag = false;
    public static boolean leftRotationFlag = false;
    public static boolean moveFlag = false;

    public Board() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        ingame = true;
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);
        this.ball = new Ball(BALL_X,BALL_Y);
        this.map = new Map(spaceship);
        timer = new Timer(20,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                inGame();
                updateShip();
                updateMissiles();
                checkCollisions();
                spaceship.rotateRight(rightRotationFlag);
                spaceship.rotateLeft(leftRotationFlag);
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
        bgImage = new BufferedImage(B_WIDTH, B_HEIGHT, BufferedImage.TYPE_INT_RGB);
        g2dbf = bgImage.createGraphics();
        if (spaceship.isVisible()) {
            g2dbf.setPaint(Color.BLACK);
            af.setToIdentity();
            af.translate(spaceship.getX(), spaceship.getY());
            af.rotate(Math.toRadians(SpaceShip.rotation),spaceship.getImage().getWidth(this)/2, spaceship.getImage().getHeight(this)/2);
            g2dbf.drawImage(spaceship.getImage(),af,this);
            g2dbf.drawImage(ball.getImage(), BALL_X, BALL_Y, this);
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
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
    }

    private void inGame() {
        if (!ingame) {
            timer.stop();
        }
    }

    private void updateShip() {
        if (spaceship.isVisible()) {
            spaceship.move(moveFlag);
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