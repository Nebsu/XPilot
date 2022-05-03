/**
 * Bonus is a subclass of Obstacle. 
 */

package map;

public class Bonus extends Obstacle{

    private int x2;
    private int y2;

    public Bonus(int[]x1, int[]y1, int x, int y){
        super(x1,y1);
        this.x2 = x;
        this.y2 = y;
    }

    // Getters :
    public final int getX2() {return x2;}
    public final int getY2() {return y2;}

    public final String toString(){
        return "Bonus(" + x2 + ", " + y2 + ")";
    }

}