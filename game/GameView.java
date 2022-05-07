package game;

import map.*;
import menu.TextButton;
import sound.SFX;
import object.*;
import main.Constants;
import main.Global;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JPanel;
import java.awt.geom.AffineTransform;

public final class GameView extends JPanel implements ActionListener {

    // View elements :
    private AffineTransform af = new AffineTransform();
    private Graphics2D g2;
    private BufferedImage bgImage;
    private BufferedImage singleShot;
    private BufferedImage multiShot;

    // Processing elements :
    private final Game game;
    private boolean ingame;

    public GameView(Game game) {
        this.game = game;
        setBackground(Color.BLACK);
        this.ingame = true;
        setPreferredSize(new Dimension(Global.W_WIDTH(), Global.W_HEIGHT()));
        game.getMap().addBonus();
        try {
            this.singleShot = ImageIO.read(new File("ressources/images/overlay_single_shot.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            this.multiShot = ImageIO.read(new File("ressources/images/overlay_volley_shot.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            synchronized (game) {
                drawHealthBar(g);
                drawFuelBar(g);
                drawMissileIndicator(g);
                drawShield(g);
            }
            try {
                drawMinimap(g);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            drawGameOver(g);

        }
    }

    // Getters / Setters :
    public final boolean isInGame() {return this.ingame;}
    public final void setInGame(boolean inGame) {this.ingame = inGame;}

    /**
     * Draw the objects on the screen
     *
     * @param g the Graphics2D object that will be drawn on
     */
    private final void drawObjects(Graphics2D g) throws IOException {
        // Create a second image of the map and updates at each repaint :
        BufferedImage image = new BufferedImage(2400, 2400, BufferedImage.TYPE_INT_BGR);
        Graphics2D g3 = image.createGraphics();
        g3.drawImage(game.getMap().getImage(), 0, 0, null);
        bgImage = new BufferedImage(Global.W_WIDTH(), Global.W_HEIGHT(), BufferedImage.TYPE_INT_BGR);
        g2 = bgImage.createGraphics();
        AffineTransform af2 = new AffineTransform();
        if (game.getShip().isVisible()) {
            for (Missile missile : game.getMissiles()) {
                // Paint Missile in the second image :
                if (missile instanceof MissileDiffusion) {
                    MissileDiffusion represent = (MissileDiffusion) missile;
                    for (MissileNormal tmp : represent.getDiffusion()) {
                        af2.setToIdentity();
                        af2.translate((int) tmp.getX(), (int) tmp.getY());
                        af2.rotate(Math.toRadians(tmp.getDirection()), tmp.getImage().getWidth(this) / 2, 
                                   tmp.getImage().getHeight(this) / 2);
                        g3.drawImage(tmp.getImage(), af2, null);
                    }
                } else {
                    af2.setToIdentity();
                    af2.translate((int) missile.getX(), (int) missile.getY());
                    af2.rotate(Math.toRadians(missile.getDirection()), missile.getImage().getWidth(this) / 2, 
                               missile.getImage().getHeight(this) / 2);
                    g3.drawImage(missile.getImage(), af2, null);
                }
            }
            // Draw ennemies :
            for(Enemy e : game.getMap().getEnemies()){
                AffineTransform af3 = new AffineTransform();
                af3.setToIdentity();
                int cx = e.getImage().getWidth()/2;
                int cy = e.getImage().getHeight()/2;
                af3.translate(cx+e.getX(), cy+e.getY());
                af3.rotate(e.getRad(game.getShip().getX(), game.getShip().getY()));
                af3.translate(-cx,-cy);
                g3.drawImage(e.getImage(),af3,null);
            }
            // The camera displays the ship's position in the second image :
            g2.drawImage(image, (int) (-game.getShip().getX()) + Global.W_WIDTH() / 2, 
                         (int) (-game.getShip().getY()) + Global.W_HEIGHT() / 2, null);
            // Spaceship functions :
            af.setToIdentity();
            af.translate(Global.W_WIDTH() / 2, Global.W_HEIGHT() / 2);
            af.rotate(Math.toRadians(game.getShip().getRotation()), game.getShip().getImage().getWidth(this) / 2, 
                      game.getShip().getImage().getHeight(this) / 2);
            g2.drawImage(game.getShip().getImage(), af, null);
            // Ball functions :
            if (game.getMap().getBall().isTaken()) {
                g2.setColor(Color.WHITE);
                g2.drawOval(10, Global.W_HEIGHT() - 40, 33, 33);
                g2.fillOval(10, Global.W_HEIGHT() - 40, 33, 33);
            } else {
                g2.setColor(Color.WHITE);
                g2.drawOval(10, Global.W_HEIGHT() - 40, 33, 33);
            }
            // Draw everything in g :
            g.drawImage(bgImage, 0, 0, null);
        }
    }

    /**
     * Draw the shield of the game.getShip()
     *
     * @param g The Graphics2D object that is used to draw the shield.
     */
    private final void drawShield(Graphics2D g) {
        // Shield quantity :
        String msg = Integer.toString(game.getShip().getShield().getQuantity());
        g.setColor(Color.GREEN);
        Font ft = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics fm = getFontMetrics(ft);
        g.setFont(ft);
        if (game.getShip().getShield().getQuantity() >= 10) {
            g.drawString(msg, Global.W_WIDTH() - fm.stringWidth(msg) - 19, 38);
        } else {
            g.drawString(msg, Global.W_WIDTH() - fm.stringWidth(msg) - 24, 38);
        }
        // Shield indicator :
        Polygon p = new Polygon();
        for (int i = 0; i < 6; i++)
            p.addPoint((int) (Global.W_WIDTH() - 30 - 20 * Math.cos(i * 2 * Math.PI / 6)), 
                       (int) (30 + 20 * Math.sin(i * 2 * Math.PI / 6)));
        g.drawPolygon(p);
        // Shield protecting ship :
        Polygon p2 = new Polygon();
        if (game.getShip().getShield().isActive()) {
            // g.setColor(Color.WHITE);
            for (int i = 0; i < 6; i++)
                p2.addPoint((int) (Global.W_WIDTH() / 2 + 6 + 20 * Math.cos(i * 2 * Math.PI / 6)), 
                            (int) (Global.W_HEIGHT() / 2 + 6 + 20 * Math.sin(i * 2 * Math.PI / 6)));
            g.drawPolygon(p2);
        }
    }

    /**
     * Draws the health bar
     *
     * @param g The Graphics2D object that is used to draw the health bar.
     */
    private final void drawHealthBar(Graphics2D g) {
        // Base of the bar :
        g.setColor(Color.WHITE);
        g.drawString("HP", Global.W_WIDTH() / 4 - 30, Global.W_HEIGHT() / 50 + 12);
        g.fillRect(Global.W_WIDTH() / 4, Global.W_HEIGHT() / 50, Global.W_WIDTH() / 2, 15);
        // Health bar :
        g.setColor(Color.RED);
        float width = (Global.W_WIDTH() / 2 - 4) * ((float) game.getShip().getHealth() / (float) game.getShip().getMaxHealth());
        g.fillRect(Global.W_WIDTH() / 4 + 2, Global.W_HEIGHT() / 50 + 2, (int) Math.round(width), 15 - 4);
        // Number :
        g.setColor(Color.GRAY);
        g.drawString("" + game.getShip().getHealth(), Global.W_WIDTH() / 2 - 10, Global.W_HEIGHT() / 50 + 12);
    }

    /**
     * Draws the fuel bar
     *
     * @param g The Graphics2D object that is used to draw the fuel bar.
     */
    private final void drawFuelBar(Graphics2D g) {
        // Base of the bar :
        g.setColor(Color.WHITE);
        g.drawString("Fuel", Global.W_WIDTH() / 4 - 30, Global.W_HEIGHT() / 20 + 12);
        g.fillRect(Global.W_WIDTH() / 4, Global.W_HEIGHT() / 20, Global.W_WIDTH() / 2, 15);
        // Fuel bar :
        g.setColor(Color.GREEN);
        float width = (Global.W_WIDTH() / 2 - 4) * ((float) game.getShip().getFuel() / (float) Constants.BASE_FUEL);
        g.fillRect(Global.W_WIDTH() / 4 + 2, Global.W_HEIGHT() / 20 + 2, (int) Math.round(width), 15 - 4);
        // Number :
        g.setColor(Color.GRAY);
        g.drawString("" + game.getShip().getFuel(), Global.W_WIDTH() / 2 - 10, Global.W_HEIGHT() / 20 + 12);
    }

    /**
     * Draws the bonus
     */
    public final void drawBonus() {
        for (Bonus b : game.getMap().getBonuses()) {
            game.getMap().getG2().setColor(Color.ORANGE);
            game.getMap().getG2().drawOval(b.getX2() + 10, b.getY2() + 10, 10, 10);
            game.getMap().getG2().fillOval(b.getX2() + 10, b.getY2() + 10, 10, 10);
        }
    }

    /**
     * Erase the bonus from the map
     *
     * @param b the bonus object
     */
    public final void erase(Bonus b) {
        game.getMap().getG2().setColor(Color.BLACK);
        game.getMap().getG2().drawOval(b.getX2() + 10, b.getY2() + 10, 10, 10);
        game.getMap().getG2().fillOval(b.getX2() + 10, b.getY2() + 10, 10, 10);
    }

    /**
     * Draw the missile indicator
     *
     * @param g The Graphics2D object that is used to draw the image.
     */
    public final void drawMissileIndicator(Graphics2D g) {
        g.setColor(Color.GREEN);
        if (game.getShip().getMissileSwitch() == 1) {
            g.drawImage(singleShot, 10, 15, null);
        } else {
            g.drawImage(multiShot, 10, 15, null);
        }
        String msg = Integer.toString(game.getShip().getMissileLeft());
        g.setColor(Color.GREEN);
        Font ft = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics fm = getFontMetrics(ft);
        g.setFont(ft);
        if (game.getShip().getMissileLeft() >= 10) {
            g.drawString(msg, fm.stringWidth(msg) + 20, 37);
        } else {
            g.drawString(msg, fm.stringWidth(msg) + 35, 37);
        }
    }

    public final void drawMinimap(Graphics g) throws IOException {
        BufferedImage bi = ImageIO.read(new File("ressources/images/minimap.png"));
        g.translate(Global.W_WIDTH() - 150, Global.W_HEIGHT() - 150);
        for (int i = 0; i < game.getMap().getInforMap().length; i++) {
            for (int j = 0; j < game.getMap().getInforMap().length; j++) {
                if (game.getMap().getInforMap()[i][j] == '-' || game.getMap().getInforMap()[i][j] == 'X' || game.getMap().getInforMap()[i][j] == 'B' || game.getMap().getInforMap()[i][j] == 'S') {
                    g.setColor(Color.BLACK);
                    g.fillRect(i * 3, j * 3, 3, 3);
                }
                if (game.getMap().getInforMap()[i][j] == 'W') {
                    g.setColor(Color.YELLOW);
                    g.fillRect(i * 3, j * 3, 3, 3);
                } else if (game.getMap().getInforMap()[i][j] == 'G') {
                    g.setColor(Color.RED);
                    g.fillRect(i * 3, j * 3, 3, 3);
                }
                g.drawImage(bi, (int) game.getShip().getX() / 16, (int) game.getShip().getY() / 16, null);
            }
        }
    }

    /**
     * Draws the game over message
     *
     * @param g The Graphics object that we are drawing to.
     */
    private final void drawGameOver(Graphics g) {
        String msg = "Game Over";
        Font ft = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(ft);
        g.setFont(ft);
        g.setColor(Color.white);
        g.drawString(msg, (Global.W_WIDTH() - fm.stringWidth(msg)) / 2, Global.W_HEIGHT() / 2);
        
        setLayout(null);
        TextButton replay = new TextButton("Play Again");
        replay.setBounds(Global.W_WIDTH()/2 - Constants.BUTTON_WIDTH/2, 
                             Global.W_HEIGHT() - (3*Constants.BUTTON_HEIGHT/2), 
                             Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        add(replay);
        replay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getLoop().resetLevel();
                game.getLoop().switchLevel();
                setInGame(true);
                try {
                    Constants.GAME_MUSIC.stopMusic();
                    Global.MAINGAME().getWindow().launchMenu(true, true);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    System.out.println(e1);
                    System.exit(1);
                }
            }
        });
    }

    /** 
     *  Function called when the player is shooting missiles. 
     *  Activates the hit system.
     *  Draws and animate the missiles.
     *  Plays the shooting sound efffect.
     */
    public final void fire() {
        if (game.getShip().getMissileLeft()> 0) {
            if(game.getShip().getMissileSwitch() == 2 && game.getShip().getMissileLeft() >= 5){
                MissileDiffusion s = new MissileDiffusion(game.getShip().getX(), game.getShip().getY(), 
                                    game.getShip().getRotation(), 1);
                for(int i=0;i<5;i++){
                    game.getMissiles().add(s.getDiffusion()[i]);
                }
                game.getShip().setMissileLeft(game.getShip().getMissileLeft() - 5);
                try {
                    SFX pew = new SFX("ressources/audio/pew.wav");
                    pew.playSound();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    System.out.println(e2);
                    System.exit(1);
                }
            }else if(game.getShip().getMissileSwitch() == 1){
                game.getMissiles().add(new Rocket(game.getShip().getX(), game.getShip().getY(), 
                                                  game.getShip().getRotation(), 1));
                game.getShip().setMissileLeft(game.getShip().getMissileLeft() - 1);
                try {
                    SFX pew = new SFX("ressources/audio/pew.wav");
                    pew.playSound();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    System.out.println(e2);
                    System.exit(1);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

}