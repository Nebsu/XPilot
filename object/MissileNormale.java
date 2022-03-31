package object;
public class MissileNormale extends Missile{

    private final int MISSILE_SPEED = 7;
    private double direction;
    public int rebounce = 0;


    public MissileNormale(double x, double y, int rotation) {
        super(x, y);
        direction = rotation;
    }

    public void move() {
        // if(rebounce%2==0){
            x += MISSILE_SPEED * Math.cos(Math.toRadians(direction));
            y += MISSILE_SPEED * Math.sin(Math.toRadians(direction));
        // }else{
        //     x -= MISSILE_SPEED * Math.cos(Math.toRadians(direction));
        //     y -= MISSILE_SPEED * Math.sin(Math.toRadians(direction));
        // }
    }

    public double getdirection(){
        return this.direction;
    }
    
}