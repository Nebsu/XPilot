/**
 * The Radar class is a JPanel that displays the minimap of the game
 */
package game;

import map.Map;
import object.SpaceShip;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.image.*;
import java.io.*;

public final class Radar extends JPanel {

    private Map minimap;
    private SpaceShip ship;

    public Radar(SpaceShip s, Map m) {
        this.ship = s;
        this.minimap = m;
        this.setBackground(Color.WHITE);
        setFocusable(true);
        setPreferredSize(new Dimension(200,200));
    }

 /**
  * Draw the minimap and the ship
  * 
  * @param g The Graphics object that is used to draw the minimap.
  */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.drawMinimap(g);
        try {
            drawShip(g);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
            System.exit(1);
        }
    }

/**
 * Draws the ship on the minimap
 * 
 * @param g The Graphics object to draw on.
 */
    public void drawShip(Graphics g) throws IOException{
        BufferedImage bi = ImageIO.read(new File("ressources/images/minimap.png"));
        g.drawImage(bi,(int)ship.getX()/12,(int)ship.getY()/12,null);
    }

/**
 * Draw the map on the minimap
 * 
 * @param g The Graphics object that you want to draw on.
 */
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