package map;
import java.awt.*;

public class Bonus extends Obstacle{
    int x;
    int y;
    Bonus(int[]x1, int[]y1, int x, int y){
        super(x1,y1);
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g){
        g.setColor(Color.ORANGE);
        g.drawOval(x, y, 10, 10);
        g.fillOval(x, y, 10, 10);
    }
}
