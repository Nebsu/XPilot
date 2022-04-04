package main;

import map.*;
import sound.Music;
import object.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.geom.AffineTransform;


public final class Game extends JPanel implements ActionListener {

    // Données liées à la gameloop :
    private boolean ingame;
    private final Timer timer;
    private final GameKeys k;

    // Données liées à la vue :
    private final AffineTransform af = new AffineTransform();
    private Graphics2D g2;
    private BufferedImage bgImage;
    private final Music gameMusic;

    // Objets du jeu :
    private final SpaceShip spaceship; 
    private final Map map;   
    private final Radar minimap;
    private final List<Missile> missiles;
    
    // Getters :
    public final SpaceShip getSpaceShip() {return this.spaceship;}
    public final Timer getTimer() {return this.timer;}
    public final Radar getMinimap() {return this.minimap;}
    public final List<Missile> getMissiles() {return this.missiles;}

    public Game() {
        // Initialisation du panel :
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));
        // Initialisation des objets du jeu :
        this.spaceship = new SpaceShip(Constants.ICRAFT_X, Constants.ICRAFT_Y);
        this.map = new Map();
        this.missiles = new ArrayList<>();
        this.minimap = new Radar(spaceship, map);
        map.addBonus();
        // Initialisation de la musique :
        String filepath = "ressources/gamemusic.wav";
        this.gameMusic = new Music(filepath);
        // Initialisation du la gameloop :
        this.ingame = true;
        this.k = new GameKeys(this);
        this.timer = new Timer(20,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                inGame();
                minimap.repaint();
                // checkcollision avant de move avec les cordonnees de move
                if(!checkCollision()){
                    updateShip();
                }else{
                    // Quand il y a collision
                    if(spaceship.canTakeDamage()){
                        if(spaceship.shield.isActive()){
                            spaceship.shield.destroy();
                            spaceship.shield.disable();
                        }else{
                            spaceship.setHealth(spaceship.getHealth()-50);
                        }
                    }
                }
                spaceship.rotateRight(spaceship.rightRotationFlag);
                spaceship.rotateLeft(spaceship.leftRotationFlag);
                updateMissiles();
                updateBonus();
                repaint();
            }
        });
        this.timer.start();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                 FONCTIONS DU MODELE                                                     //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Vérifie les conditions d'arrêt du jeu :
    private void inGame() {
        if(spaceship.getFuel() <= 0 ||
            spaceship.getHealth() <= 0){
            ingame = false;
        }
        if (!ingame) {
            this.timer.stop();
        }
    }

    public void fire() {
        missiles.add(new MissileDiffusion(spaceship.getX(), spaceship.getY(), spaceship.rotation));
    }

    public boolean checkCollision(){
        Rectangle s=new Rectangle((int)(spaceship.getX()+spaceship.SPEED * Math.cos(Math.toRadians(spaceship.rotation))),(int)(spaceship.getY()+spaceship.SPEED*Math.sin(Math.toRadians(spaceship.rotation))),16,16);
        for (Obstacle obstacle : map.ListeObstacle) {
            Rectangle o=new Rectangle(obstacle.x[0],obstacle.y[0],obstacle.x[1]-obstacle.x[0],obstacle.y[2]-obstacle.y[1]);
            //ship apres le movement
            if(s.intersects(o) && !(obstacle instanceof Goal) && !(obstacle instanceof Ball) && !(obstacle instanceof Bonus)){
                return true;
            }else if(s.intersects(o)&& obstacle instanceof Ball){
                map.ball.take();
            }else if(s.intersects(o)&& obstacle instanceof Goal && map.ball.isTaken()){
                ingame = false;
            }
        }
        for (int i = 0; i < map.bonusList.size(); i++){
            Bonus b = map.bonusList.get(i);
            Rectangle r=new Rectangle(b.x[0],b.y[0],b.x[1]-b.x[0],b.y[2]-b.y[1]);
            if(s.intersects(r)){
                if(spaceship.getFuel()<spaceship.BASE_FUEL)spaceship.setFuel(spaceship.getFuel()+500);
                spaceship.shield.add();
                erase(b);
                map.bonusList.remove(b);
            }
        }
        return false;
    }

    // Fonctions Update :

    private void updateShip() {
        if (spaceship.isVisible()) {
            spaceship.move();
            spaceship.acceleration();
            spaceship.deceleration();
            if(spaceship.moveFlag)spaceship.consumeFuel();
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
    
    private void updateBonus(){
        if(System.currentTimeMillis() - map.lastTime > Constants.BONUS_SPAWNRATE){
            map.addBonus();
            drawBonus();
        }
    }

    // Inputs
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
        
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                   FONCTIONS DE LA VUE                                                   //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
        } else {
            drawGameOver(g);
        }
    }

    private void drawObjects(Graphics2D g) throws IOException {
        // Create image comme une seconde image de map et renouvelle à chaque repaint :
        BufferedImage image = new BufferedImage(2400,2400,BufferedImage.TYPE_INT_BGR);
        Graphics2D g3=image.createGraphics();
        g3.drawImage(map.img_map, 0, 0, null);
        bgImage= new BufferedImage(800,600,BufferedImage.TYPE_INT_BGR);
        g2 = bgImage.createGraphics();
        if (spaceship.isVisible()) {
            for (Missile missile : this.missiles) {
                // Paint Missile dans la seconde image :
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
            // Camera affiche la position de vaisseau dans le seconde image :
            g2.drawImage(image,(int)(-spaceship.getX())+400,(int)(-spaceship.getY())+300, null);
            // Vaisseau :
            af.setToIdentity();
            af.translate(Constants.B_WIDTH/2, Constants.B_HEIGHT/2);
            af.rotate(Math.toRadians(spaceship.rotation),spaceship.getImage().getWidth(this)/2, spaceship.getImage().getHeight(this)/2);
            g2.drawImage(spaceship.getImage(),af,null);
            // Boule :
            if(map.ball.isTaken()) g.drawImage(map.ball.getImage(), Constants.B_WIDTH/2, Constants.B_HEIGHT/2+40, null);
            // Textes :
            g2.setColor(Color.GREEN);
            g2.drawString("Ball: " + map.ball.isTaken(), 2, 20);
            g2.drawString("Shield: " + spaceship.shield.getQuantity(), 2, 35);
            if (spaceship.shield.isActive()){
                g2.setColor(Color.WHITE);
                g2.drawOval(Constants.B_WIDTH/2-8, Constants.B_HEIGHT/2-9, 33, 33);
            }
            // Afficher dans g :
            g.drawImage(bgImage, 0, 0, null);
        }
    }

    // Dessine la barre de vie :
    private void drawHealthBar(Graphics2D g){
        // Fond de la barre :
        g.setColor(Color.WHITE);
        g.drawString("HP", Constants.B_WIDTH/4-30, Constants.B_HEIGHT/50+12);
        g.fillRect(Constants.B_WIDTH/4, Constants.B_HEIGHT/50, Constants.B_WIDTH/2, 15);
        // Barre de vie :
        g.setColor(Color.RED);
        float width = (Constants.B_WIDTH/2-4) * ((float)spaceship.getHealth() / (float)spaceship.getMaxHealth());
        g.fillRect(Constants.B_WIDTH/4+2, Constants.B_HEIGHT/50+2, (int)Math.round(width), 15-4);
        // Nombre :
        g.setColor(Color.GRAY);
        g.drawString("" + spaceship.getHealth(), Constants.B_WIDTH/2-10, Constants.B_HEIGHT/50+12);
    }

    // Dessine la barre de fuel :
    private void drawFuelBar(Graphics2D g){
        // Fond de la barre :
        g.setColor(Color.WHITE);
        g.drawString("Fuel", Constants.B_WIDTH/4-30, Constants.B_HEIGHT/20+12);
        g.fillRect(Constants.B_WIDTH/4, Constants.B_HEIGHT/20, Constants.B_WIDTH/2, 15);
        // Barre de fuel :
        g.setColor(Color.GREEN);
        float width = (Constants.B_WIDTH/2-4) * ((float)spaceship.getFuel() / (float)spaceship.BASE_FUEL);
        g.fillRect(Constants.B_WIDTH/4+2, Constants.B_HEIGHT/20+2, (int)Math.round(width), 15-4);
        // Nombre :
        g.setColor(Color.GRAY);
        g.drawString("" + spaceship.getFuel(), Constants.B_WIDTH/2-10, Constants.B_HEIGHT/20+12);
    }
    // Dessine les bonus :
    private void drawBonus(){
        for(Bonus b : map.bonusList){
            map.g2.setColor(Color.ORANGE);
            map.g2.drawOval(b.x2+10, b.y2+10, 10, 10);
            map.g2.fillOval(b.x2+10, b.y2+10, 10, 10);
        }
    }

    // Efface les bonus pris :
    private void erase(Bonus b){
        map.g2.setColor(Color.BLACK);
        map.g2.drawOval(b.x2+10, b.y2+10, 10, 10);
        map.g2.fillOval(b.x2+10, b.y2+10, 10, 10);
    }

    // Ecran de fin :
    private void drawGameOver(Graphics g) {
        String msg = "Game Over";
        Font ft = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(ft);
        g.setColor(Color.white);
        g.setFont(ft);
        g.drawString(msg, (Constants.B_WIDTH - fm.stringWidth(msg)) / 2,
        Constants.B_HEIGHT / 2);
    }

    // Jouer la musique de fond :
    public void playGameMusic() {
        try {
            gameMusic.playMusic();
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
            System.out.println(e);
            System.exit(1);
        }
    }

    // Arrêter la musique :
    public void stopGameMusic() {
        this.gameMusic.stopMusic();
    }

}