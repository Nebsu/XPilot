package Xpilot;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
//import java.util.LinkedList;
import java.util.LinkedList;

import javax.imageio.*;


public class Map {
    public BufferedImage img; // image
    public BufferedImage img_map; //image map
    private LinkedList<Obstacle> ListeObstacle=new LinkedList<>();
    public SpaceShip ship;
    public Graphics2D g;
    public Graphics2D g2;
    public Map(){
        ship=new SpaceShip();
        try{
            this.img_map=ImageIO.read(new File("E://background.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        this.img=new BufferedImage(800,600,BufferedImage.TYPE_INT_BGR);
        g=img.createGraphics();
        g2=img_map.createGraphics();
        /*
        metObstacleRectangle();
        metObstacletriangle();
        metObstacleconbine();*/
        createMap();
        g.drawImage(img_map,0,0,null);
    }

    public void createMap(){
        //generateur pour les obstacle
        int x[]={0,600,800,1500,1500,1800,1800,2400,2400,0,0};          
        int y[]={600,600,800,800,1500,1500,300,300,2400,2400,600};
        Obstacle map=new Obstacle(x, y);
        ListeObstacle.add(map);
        map.draw(g2);
    }
    public void metObstacleRectangle(){
        int x[]={100,200,200,100,100};
        int y[]={100,100,200,200,100};
        Obstacle rectangle=new Obstacle(x, y);
        ListeObstacle.add(rectangle);
        rectangle.draw(g2);
    }
    public void metObstacletriangle(){
        int x[]={400,500,400,400};
        int y[]={300,400,400,300};
        Obstacle triangle=new Obstacle(x, y);
        ListeObstacle.add(triangle);
        triangle.draw(g2);
    }
    public void metObstacleconbine(){
        int x[]={200,300,400,200,200};
        int y[]={100,100,200,200,100};
        Obstacle poly=new Obstacle(x, y);
        ListeObstacle.add(poly);
        poly.draw(g2);
    }
}
