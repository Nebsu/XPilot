/** The goal is a red square that is drawn on the map */

package map;

import java.awt.*;

public final class Goal extends Obstacle {

    public Goal(int[] x, int[] y) {
        super(x, y);
    }
    
    @Override
    public final void draw(Graphics g){
        g.setColor(Color.RED);
        Polygon polygon=new Polygon(x,y,Math.min(x.length,y.length));
        g.fillPolygon(polygon);
    }

}