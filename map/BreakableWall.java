package map;

import java.awt.*;

public class BreakableWall extends Obstacle{
    private int state = 3;

    public BreakableWall(int[] x, int[] y) {
        super(x, y);
    }

    public void setState(int state) {
        this.state = state;
    }
    public int getState() {
        return state;
    }
    
    @Override
    public final void draw(Graphics g){
        if(state == 3){
            g.setColor(Color.GREEN);
        }else if(state == 2){
            g.setColor(Color.ORANGE);
        }else if(state ==1){
            g.setColor(Color.RED);
        }else{
            g.setColor(Color.BLACK);
        }
        Polygon polygon=new Polygon(x,y,Math.min(x.length,y.length));
        g.fillPolygon(polygon);
    }
}
