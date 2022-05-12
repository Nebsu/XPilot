/** A missile is a Sprite that moves in a direction */

package src.object;

public abstract class Missile extends Sprite {

    protected int shooter;

    public Missile(double x, double y, int shooter) {
        super(x, y);
        this.shooter = shooter;
        loadImage("ressources/images/shot.png");
        getImageDimensions();
    }

    public final int getShooter() {return shooter;}

    public abstract void move();

    public abstract int getDirection();

}