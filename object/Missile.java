package object;
public abstract class Missile extends Sprite {

    private double direction;


    public Missile(double x, double y) {
        super(x, y);
        loadImage("ressources/shot_h.png");
        getImageDimensions();
    }

    public abstract void move();
}