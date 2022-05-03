package object;

public final class Rocket extends Missile {

    private final int MISSILE_SPEED = 30;
    private int direction;
    public int rebounce = 0;

    public Rocket(double x, double y, int rotation, int shooter) {
        super(x, y, shooter);
        direction = rotation;
    }

    public void move() {
        x += MISSILE_SPEED * Math.cos(Math.toRadians(direction));
        y += MISSILE_SPEED * Math.sin(Math.toRadians(direction));
    }

    @Override
    public int getDirection() {
        return this.direction;
    }
    
    public final void setDirection(int direction) {
        this.direction = direction;
    }

}