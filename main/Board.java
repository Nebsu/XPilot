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
import java.util.Timer;
import java.awt.geom.AffineTransform;


public class Board extends JPanel implements ActionListener{

    SpaceShip spaceship; 
    Map map;
    boolean ingame;
    AffineTransform af = new AffineTransform();
    Graphics2D g2;
    BufferedImage bgImage;
    private Keys k;
    public Minimap minimap;
    public List<Missile> missiles;
    private Music gameMusic;

    public Board() throws IOException{
        //Initialisation
        boardInit();
        Constants.TIMER=new Timer();
        Constants.TIMER.schedule(new GameLoop(this), 0,20);
    }

    private void boardInit() throws IOException{
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        this.ingame = true;
        setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));
        this.spaceship = new SpaceShip(Constants.ICRAFT_X, Constants.ICRAFT_Y);
        this.map = new Map();
        this.missiles = new ArrayList<>();
        this.k = new Keys(this);
        this.minimap = new Minimap(spaceship, map);
        map.addBonus();
    }

    
    //Verifie les conditions d'arrêt du jeu
    public void inGame() {
        if(spaceship.getFuel() <= 0 ||
            spaceship.getHealth() <= 0){
            ingame = false;
        }
        if (!ingame) {
            Constants.TIMER.cancel();
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
            drawHealthBar(g);
            drawFuelBar(g);
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
                    g3.drawImage(missile.getImage(), (int)missile.getX(), (int)missile.getY(),null);
                }
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
                g2.drawOval(10, Constants.B_HEIGHT-35, 33, 33);
                g2.fillOval(10, Constants.B_HEIGHT-35, 33, 33);
            }else{
                g2.setColor(Color.WHITE);
                g2.drawOval(10, Constants.B_HEIGHT-35, 33, 33);
            }
            //Textes
            g2.setColor(Color.GREEN);
            g2.drawString("Ball: " + map.ball.isTaken(), 2, 20);
            g2.drawString("Shield: " + spaceship.shield.getQuantity(), 2, 35);
            if (spaceship.shield.isActive()){
                g2.setColor(Color.WHITE);
                g2.drawOval(Constants.B_WIDTH/2-8, Constants.B_HEIGHT/2-9, 33, 33);
            }
            //afficher dans g
            g.drawImage(bgImage, 0, 0, null);
        }
    }

    //Dessine la barre de vie
    private void drawHealthBar(Graphics2D g){
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

    //Dessine la barre de fuel
    private void drawFuelBar(Graphics2D g){
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
    //Dessine les bonus
    public void drawBonus(){
        for(Bonus b : map.bonusList){
            map.g2.setColor(Color.ORANGE);
            map.g2.drawOval(b.x2+10, b.y2+10, 10, 10);
            map.g2.fillOval(b.x2+10, b.y2+10, 10, 10);
        }
    }

    //Efface les bonus pris
    public void erase(Bonus b){
        map.g2.setColor(Color.BLACK);
        map.g2.drawOval(b.x2+10, b.y2+10, 10, 10);
        map.g2.fillOval(b.x2+10, b.y2+10, 10, 10);
    }

    //Ecran de fin
    private void drawGameOver(Graphics g) {
        String msg = "Game Over";
        Font ft = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(ft);
        g.setColor(Color.white);
        g.setFont(ft);
        g.drawString(msg, (Constants.B_WIDTH - fm.stringWidth(msg)) / 2,
        Constants.B_HEIGHT / 2);
    }
    
    //Autres fonctions

    public void fire() {
        MissileDiffusion s=new MissileDiffusion(spaceship.getX(), spaceship.getY(), spaceship.rotation);
        for(int i=0;i<5;i++){
            missiles.add(s.getDiffusion()[i]);
        }
    }

    public void playMusic() throws LineUnavailableException, IOException {
		try {
			// Music :
			String filepath = "ressources/gamemusic.wav";
			this.gameMusic = new Music(filepath);
			gameMusic.playMusic();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public void stopMusic() {
        this.gameMusic.stopMusic();
    }

    //Inputs
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
}