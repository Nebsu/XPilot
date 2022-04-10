package game;

import map.*;
import sound.Music;
import object.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JPanel;

import main.Constants;

import java.util.Timer;
import java.awt.geom.AffineTransform;

public final class GameView extends JPanel implements ActionListener {

    // GameObjects :
    private SpaceShip spaceship; 
    private List<Missile> missiles;
    private Map map;

    // Variables vue :
    private AffineTransform af = new AffineTransform();
    private Graphics2D g2;
    private BufferedImage bgImage;
    private Radar minimap;
    private BufferedImage singleShot;
    private BufferedImage multiShot;
    private Music gameMusic;

    // Varaibles modèle :
    private boolean ingame;
    private GameKeys k;
    private Timer timer;

    public final SpaceShip getSpaceShip() {return this.spaceship;}
    public final List<Missile> getMissiles() {return this.missiles;}
    public final Map getMap() {return this.map;}
    public final Radar getRadar() {return this.minimap;}
    public final Timer getTimer() {return this.timer;}
    public final boolean isInGame() {return this.ingame;}
    public final void setInGame(boolean inGame) {this.ingame = inGame;}

    public GameView() throws IOException {
        //Initialisation
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        this.ingame = true;
        setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));
        this.spaceship = new SpaceShip(Constants.ICRAFT_X, Constants.ICRAFT_Y);
        this.map = new Map();
        this.missiles = new ArrayList<>();
        this.k = new GameKeys(this);
        this.minimap = new Radar(spaceship, map);
        map.addBonus();
        try {
            BufferedImage singleShot = ImageIO.read(new File("ressources/images/overlay_single_shot.png"));
            this.singleShot = singleShot;
            BufferedImage multiShot = ImageIO.read(new File("ressources/images/overlay_volley_shot.png"));
            this.multiShot = multiShot;
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.timer = new Timer();
        this.timer.schedule(new GameLoop(this), 0,20);
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
            drawHealthBar(g);
            drawFuelBar(g);
            drawMissileIndicator(g);
            drawShield(g);
        } else {
            drawGameOver(g);
        }
    }

/**
 * Draw the objects on the screen
 * 
 * @param g the Graphics2D object that will be drawn on
 */
    private final void drawObjects(Graphics2D g) throws IOException {
        //create image comme une seconde image de map et renouvelle à chaque repaint
        BufferedImage image = new BufferedImage(2400,2400,BufferedImage.TYPE_INT_BGR);
        Graphics2D g3=image.createGraphics();
        g3.drawImage(map.img_map, 0, 0, null);

        bgImage= new BufferedImage(800,600,BufferedImage.TYPE_INT_BGR);
        g2 = bgImage.createGraphics();
        AffineTransform af2 = new AffineTransform();
        if (spaceship.isVisible()) {
            for (Missile missile : this.missiles) {
                //paint Missile dans la seconde image
                if(missile instanceof MissileDiffusion){
                    MissileDiffusion represent=(MissileDiffusion)missile;
                    for(MissileNormale tmp:represent.getDiffusion()){
                        af2.setToIdentity();
                        af2.translate((int)tmp.getX(), (int)tmp.getY());
                        af2.rotate(Math.toRadians(tmp.getdirection()),tmp.getImage().getWidth(this)/2, tmp.getImage().getHeight(this)/2);
                        g3.drawImage(tmp.getImage(), af2, null);
                    }
                }
                else{
                    af2.setToIdentity();
                    af2.translate((int)missile.getX(), (int)missile.getY());
                    af2.rotate(Math.toRadians(missile.getdirection()),missile.getImage().getWidth(this)/2, missile.getImage().getHeight(this)/2);
                    g3.drawImage(missile.getImage(), af2,null);
                }
            }
            //Dessine les ennemis
            for(Enemy e : map.enemies){
                AffineTransform af3 = new AffineTransform();
                af3.setToIdentity();
                int cx = e.image.getWidth()/2;
                int cy = e.image.getHeight()/2;
                af3.translate(cx+e.x, cy+e.y);
                af3.rotate(e.getRad(spaceship.getX(), spaceship.getY()));
                af3.translate(-cx,-cy);
                g3.drawImage(e.image,af3,null);
            }

            //Camera affiche la position de vaisseau dans le seconde image
            g2.drawImage(image,(int)(-spaceship.getX())+Constants.B_WIDTH/2,(int)(-spaceship.getY())+Constants.B_HEIGHT/2, null);

            //Vaisseau
            af.setToIdentity();
            af.translate(Constants.B_WIDTH/2, Constants.B_HEIGHT/2);
            af.rotate(Math.toRadians(spaceship.rotation),spaceship.getImage().getWidth(this)/2, spaceship.getImage().getHeight(this)/2);
            g2.drawImage(spaceship.getImage(),af,null);

            //Boule
            if(map.ball.isTaken()) {
                g2.setColor(Color.WHITE);
                g2.drawOval(10, Constants.B_HEIGHT-40, 33, 33);
                g2.fillOval(10, Constants.B_HEIGHT-40, 33, 33);
            }else{
                g2.setColor(Color.WHITE);
                g2.drawOval(10, Constants.B_HEIGHT-40, 33, 33);
            }
            //afficher dans g
            g.drawImage(bgImage, 0, 0, null);
        }
    }

/**
 * Draw the shield of the spaceship
 * 
 * @param g The Graphics2D object that is used to draw the shield.
 */
    private final void drawShield(Graphics2D g){
        //Quantité de bouclier
        String msg = Integer.toString(spaceship.shield.getQuantity());
        g.setColor(Color.GREEN);
        Font ft = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics fm = getFontMetrics(ft);
        g.setFont(ft);
        if(spaceship.shield.getQuantity() >= 10){
            g.drawString(msg, Constants.B_WIDTH - fm.stringWidth(msg) - 19, 38);
        }else{
            g.drawString(msg, Constants.B_WIDTH - fm.stringWidth(msg) - 24, 38);
        }

        //Indicateur du bouclier :
        Polygon p = new Polygon();
        for (int i = 0; i < 6; i++)
        p.addPoint((int) (Constants.B_WIDTH - 30 - 20 * Math.cos(i * 2 * Math.PI / 6)),(int) (30 + 20 * Math.sin(i * 2 * Math.PI / 6)));        
        g.drawPolygon(p);  

        //Bouclier autour du vaisseau :
        Polygon p2 = new Polygon();
        if (spaceship.shield.isActive()){
            // g.setColor(Color.WHITE);
            for (int i = 0; i < 6; i++)p2.addPoint((int) (Constants.B_WIDTH/2+6 + 20 * Math.cos(i * 2 * Math.PI / 6)),(int) (Constants.B_HEIGHT/2+6 + 20 * Math.sin(i * 2 * Math.PI / 6)));        
            g.drawPolygon(p2);  
        }
    }

/**
 * Draws the health bar
 * 
 * @param g The Graphics2D object that is used to draw the health bar.
 */
    private final void drawHealthBar(Graphics2D g){
        //Fond de la barre
        g.setColor(Color.WHITE);
        g.drawString("HP", Constants.B_WIDTH/4-30, Constants.B_HEIGHT/50+12);
        g.fillRect(Constants.B_WIDTH/4, Constants.B_HEIGHT/50, Constants.B_WIDTH/2, 15);
        //Barre de vie
        g.setColor(Color.RED);
        float width = (Constants.B_WIDTH/2-4) * ((float)spaceship.getHealth() / (float)spaceship.getMaxHealth());
        g.fillRect(Constants.B_WIDTH/4+2, Constants.B_HEIGHT/50+2, (int)Math.round(width), 15-4);
        //Nombre
        g.setColor(Color.GRAY);
        g.drawString("" + spaceship.getHealth(), Constants.B_WIDTH/2-10, Constants.B_HEIGHT/50+12);
    }

/**
 * Draws the fuel bar
 * 
 * @param g The Graphics2D object that is used to draw the fuel bar.
 */
    private final void drawFuelBar(Graphics2D g){
        //Fond de la barre
        g.setColor(Color.WHITE);
        g.drawString("Fuel", Constants.B_WIDTH/4-30, Constants.B_HEIGHT/20+12);
        g.fillRect(Constants.B_WIDTH/4, Constants.B_HEIGHT/20, Constants.B_WIDTH/2, 15);
        //Barre de fuel
        g.setColor(Color.GREEN);
        float width = (Constants.B_WIDTH/2-4) * ((float)spaceship.getFuel() / (float)spaceship.BASE_FUEL);
        g.fillRect(Constants.B_WIDTH/4+2, Constants.B_HEIGHT/20+2, (int)Math.round(width), 15-4);
        //Nombre
        g.setColor(Color.GRAY);
        g.drawString("" + spaceship.getFuel(), Constants.B_WIDTH/2-10, Constants.B_HEIGHT/20+12);
    }

/**
 * Draws the bonus
 */
    public final void drawBonus(){
        for(Bonus b : map.bonusList){
            Color c = new Color(255,165,0,127);
            map.g2.setColor(c);
            map.g2.drawOval(b.x2+10, b.y2+10, 10, 10);
            map.g2.fillOval(b.x2+10, b.y2+10, 10, 10);
        }
    }

/**
 * Erase the bonus from the map
 * 
 * @param b the bonus object
 */
    public final void erase(Bonus b){
        map.g2.setColor(Color.BLACK);
        map.g2.drawOval(b.x2+10, b.y2+10, 10, 10);
        map.g2.fillOval(b.x2+10, b.y2+10, 10, 10);
    }

/**
 * Draw the missile indicator
 * 
 * @param g The Graphics2D object that is used to draw the image.
 */
    public final void drawMissileIndicator(Graphics2D g){
        g.setColor(Color.GREEN);

        if(spaceship.missile_switch == 1){
            g.drawImage(singleShot, 10, 15, null);
        }else{
            g.drawImage(multiShot, 10, 15, null);
        }
        String msg = Integer.toString(spaceship.missile_left);
        g.setColor(Color.GREEN);
        Font ft = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics fm = getFontMetrics(ft);
        g.setFont(ft);
        if(spaceship.missile_left >= 10){
            g.drawString(msg, fm.stringWidth(msg) + 20 , 37);
        }else{
            g.drawString(msg, fm.stringWidth(msg) + 35 , 37);
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
        g.drawString(msg, (Constants.B_WIDTH - fm.stringWidth(msg)) / 2,
        Constants.B_HEIGHT / 2);
    }
    
    //Autres fonctions

    public final void fire() {
        if(spaceship.missile_left > 0){
            if(spaceship.missile_switch == 2 && spaceship.missile_left >= 5){
                MissileDiffusion s=new MissileDiffusion(spaceship.getX(), spaceship.getY(), spaceship.rotation);
                for(int i=0;i<5;i++){
                    missiles.add(s.getDiffusion()[i]);
                }
                spaceship.missile_left -= 5;
            }else if(spaceship.missile_switch == 1){
                missiles.add(new MissileNormale(spaceship.getX(), spaceship.getY(), spaceship.rotation));
                spaceship.missile_left -= 1;
            }
        }
    }

/**
 * Plays the game music.
 */
    public final void playGameMusic() throws LineUnavailableException, IOException {
		try {
			// Music :
			String filepath = "ressources/audio/gamemusic.wav";
			this.gameMusic = new Music(filepath);
			gameMusic.playMusic();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

/**
 * Stops the game music
 */
    public final void stopGameMusic() {
        this.gameMusic.stopMusic();
    }

    //Inputs
    private final class TAdapter extends KeyAdapter {

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

    }

}