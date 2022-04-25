/**
 * A missile is a Sprite that moves in a direction
 */
package object;

public abstract class Missile extends Sprite {
    public int shooter;
    public Missile(double x, double y, int shooter) {
        super(x, y);
        this.shooter = shooter;
        loadImage("ressources/images/shot.png");
        getImageDimensions();
    }
    public abstract void move();
    public abstract int getdirection();

}