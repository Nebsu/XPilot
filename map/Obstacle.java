/** It draws a polygon with the given x and y coordinates. */

package map;

import java.awt.*;

public class Obstacle {

    protected int[] x;
    protected int[] y;

    public Obstacle(int x[],int y[]){
        this.x=x;
        this.y=y;
    }

    // Getters :
    public final int[] getX() {return x;}
    public final int[] getY() {return y;}

    public void draw(Graphics g){
        g.setColor(Color.YELLOW);
        Polygon polygon=new Polygon(x,y,Math.min(x.length,y.length));
        g.fillPolygon(polygon);
    }

}