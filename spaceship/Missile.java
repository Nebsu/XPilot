package spaceship;
public class Missile extends Sprite {

    private final int BOARD_WIDTH = 390;
    private final int MISSILE_SPEED = 7;
    private double direction;
    public int rebounce = 0;


    public Missile(double x, double y, int rotation) {
        super(x, y);
        initMissile();
        direction = rotation;
    }
    
    private void initMissile() {
        loadImage("ressources/shot_h.png");
        getImageDimensions();        
    }

    public void move() {
        if(rebounce%2==0){
            x += MISSILE_SPEED * Math.cos(Math.toRadians(direction));
            y += MISSILE_SPEED * Math.sin(Math.toRadians(direction));
        }else{
            x -= MISSILE_SPEED * Math.cos(Math.toRadians(direction));
            y -= MISSILE_SPEED * Math.sin(Math.toRadians(direction));
        }
    }
    
}