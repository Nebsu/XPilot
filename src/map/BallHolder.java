/** The BallHolder class is an obstacle that can be taken by the player */

package src.map;

import java.awt.*;

public final class BallHolder extends Obstacle {

    private boolean taken = false;
    
    public BallHolder(int[] x, int[] y){
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