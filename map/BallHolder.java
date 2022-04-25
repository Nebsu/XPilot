/**
 * The BallHolder class is an obstacle that can be taken by the player
 */
package map;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BallHolder extends Obstacle {
    private boolean taken = false;
    private BufferedImage ball_img;
    
    public BallHolder(int[] x, int[] y) throws IOException {
        super(x, y);
        this.ball_img = ImageIO.read(new File("ressources/images/ball.png"));
    }

    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        Polygon polygon=new Polygon(x,y,Math.min(x.length,y.length));
        g.fillPolygon(polygon);
    } 

    public void take(){
        this.taken = true;
    }

    public boolean isTaken(){
        return taken;
    }

    public BufferedImage getImage(){
        return ball_img;
    }

}