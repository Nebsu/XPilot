package map;

public class Bonus extends Obstacle{
    public int x2;
    public int y2;
    public Bonus(int[]x1, int[]y1, int x, int y){
        super(x1,y1);
        this.x2 = x;
        this.y2 = y;
    }

    public String toString(){
        return "Bonus(" + x2 + ", " + y2 + ")";
    }
}
