/**
 * A missile is a Sprite that moves in a direction
 */
package object;

public abstract class Missile extends Sprite {

    public Missile(double x, double y) {
        super(x, y);
        loadImage("ressources/images/shot.png");
        getImageDimensions();
    }

    public abstract void move();
    public abstract int getdirection();

}