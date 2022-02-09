package Xpilot;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements KeyListener{
    private BufferedImage img;
    private Graphics2D g2;
    private Map map;
    private final int screenWidth =800;
    private final int screenHeight =600;
    public SpaceShip ship=new SpaceShip();

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        img=new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_BGR);
        g2=img.createGraphics();
        this.map=new Map();
        Refresh Refresh=new Refresh(this);
        Refresh.start();
    }

    public void paintImage(){
        g2.drawImage(map.img,0,0,this); 
        g2.drawImage(map.ship.getImage(),map.ship.getX(), map.ship.getY(), this);
    }
    public void draw(){
        map.g.drawImage(map.img_map, 800-map.ship.getX()-400,600-map.ship.getY()-300,null);
        g2.drawImage(map.img,0,0,this);  
        g2.drawImage(map.ship.getImage(),map.ship.screenX, map.ship.screenY, this);
    }
    public void update(){
        //misa a jour 
    }

    public void paint(Graphics g){  
        update();
        if(map.ship.getX()>600||map.ship.getY()>800||map.ship.getX()<1800||map.ship.getY()<1600){
            draw();
        }else{
            paintImage();
        }
        g.drawImage(this.img,0,0, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            map.ship.setX(-10);
            System.out.println(map.ship.getX());
            repaint();
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            map.ship.setX(10);
            System.out.println(map.ship.getX());
            repaint();
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            map.ship.setY(-10);
            System.out.println(map.ship.getY());
            repaint();
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            map.ship.setY(10);
            System.out.println(map.ship.getY());
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            map.ship.setX(-10);
            System.out.println(map.ship.getX());
            repaint();
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            map.ship.setX(10);
            System.out.println(map.ship.getX());
            repaint();
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            map.ship.setY(-10);
            System.out.println(map.ship.getY());
            repaint();
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            map.ship.setY(10);
            System.out.println(map.ship.getY());
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
