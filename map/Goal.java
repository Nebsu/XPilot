/**
 * The goal is a red square that is drawn on the map
 */
package map;
import java.awt.*;

public class Goal extends Obstacle{

    public Goal(int[] x, int[] y) {
        super(x, y);
    }
    
    public void draw(Graphics g){
        g.setColor(Color.RED);
        Polygon polygon=new Polygon(x,y,Math.min(x.length,y.length));
        g.fillPolygon(polygon);
    }
}
