/** The BallHolder class is an obstacle that can be taken by the player */

package map;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class BallHolder extends Obstacle {

    private boolean taken = false;
    
    public BallHolder(int[] x, int[] y) throws IOException {
        super(x, y);
    }
    
    public final void draw(Graphics g){
        g.setColor(Color.WHITE);
        Polygon polygon=new Polygon(x,y,Math.min(x.length,y.length));
        g.fillPolygon(polygon);
    } 

    public final void take(){
        this.taken = true;
    }

    public final boolean isTaken(){
        return taken;
    }

}