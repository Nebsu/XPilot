/**
 * A missile that moves in a straight line
 */

package src.object;

public final class MissileNormal extends Missile {

    private final int MISSILE_SPEED = 20;
    private int direction;

    public MissileNormal(double x, double y, int rotation, int shooter) {
        super(x, y, shooter);
        direction = rotation;
    }

    @Override
    public void move() {
        x += MISSILE_SPEED * Math.cos(Math.toRadians(direction));
        y += MISSILE_SPEED * Math.sin(Math.toRadians(direction));
    }

    @Override
    public int getDirection() {
        return this.direction;
    }
    
}