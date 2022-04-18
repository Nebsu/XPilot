/**
 * A missile that moves in a straight line
 */
package object;

public class MissileNormale extends Missile{

    private final int MISSILE_SPEED = 20;
    private int direction;
    public int shooter;


    public MissileNormale(double x, double y, int rotation, int shooter) {
        super(x, y, shooter);
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