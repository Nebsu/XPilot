package object;

public class MissileNormale extends Missile{

    private final int MISSILE_SPEED = 30;
    private int direction;


    public MissileNormale(double x, double y, int rotation) {
        super(x, y);
        direction = rotation;
    }

    public void move() {
        x += MISSILE_SPEED * Math.cos(Math.toRadians(direction));
        y += MISSILE_SPEED * Math.sin(Math.toRadians(direction));
    }

    @Override
    public int getdirection() {
        return this.direction;
    }
    
}