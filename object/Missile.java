package object;
public class Missile extends Sprite {

    private final int MISSILE_SPEED = 7;
    private double direction;
    public int rebounce = 0;


    public Missile(double x, double y, int rotation) {
        super(x, y);
        loadImage("ressources/shot_h.png");
        getImageDimensions();    
        direction = rotation;
    }

    public void move() {
        x += MISSILE_SPEED * Math.cos(Math.toRadians(direction));
        y += MISSILE_SPEED * Math.sin(Math.toRadians(direction));
    }
}