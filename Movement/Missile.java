public class Missile extends Sprite {

    private final int BOARD_WIDTH = 390;
    private final int MISSILE_SPEED = 2;
    private double direction;


    public Missile(double x, double y) {
        super(x, y);
        initMissile();
        direction = SpaceShip.rotation;
    }
    
    private void initMissile() {
        loadImage("img/shot_h.png");
        getImageDimensions();        
    }

    public void move() {
        x += MISSILE_SPEED * Math.cos(Math.toRadians(direction));
        y += MISSILE_SPEED * Math.sin(Math.toRadians(direction));
        
        if (x > BOARD_WIDTH || x < 0 || y > 390 || x < 0)
            visible = false;
    }
}