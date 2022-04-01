package main;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.image.*;
import map.Map;
import object.SpaceShip;

import java.io.*;

public class Minimap extends JPanel{
    public Map minimap;
    public SpaceShip ship;
    public Minimap(SpaceShip s, Map m) {
        this.ship = s;
        this.minimap = m;
        this.setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(200,200));
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.drawMinimap(g);
        try {
            drawShip(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawShip(Graphics g) throws IOException{
        BufferedImage bi = ImageIO.read(new File("ressources/minimap.png"));
        g.drawImage(bi,(int)ship.getX()/12,(int)ship.getY()/12,null);
    }

	public void drawMinimap(Graphics g){
        for(int i=0;i<minimap.infor_map.length;i++){
            for(int j=0;j<minimap.infor_map.length;j++){
                if(minimap.infor_map[i][j]==1){
                    g.setColor(Color.YELLOW);
                    g.fillRect(i*4,j*4,4,4);
                }else if(minimap.infor_map[i][j]==2){
                    g.setColor(Color.RED);
                    g.fillRect(i*4,j*4,4,4);
                }
            }
        }
    }
}
