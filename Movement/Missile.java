package Movement;

public class Missile extends Sprite {

    private final int BOARD_WIDTH = 390;
    private final int MISSILE_SPEED = 1;
    private String direction;

    public Missile(int x, int y, String direction) {
        super(x, y);
        this.direction = direction;
        initMissile();
    }
    
    private void initMissile() {
        if(direction.equals("left")||direction.equals("right")){
            loadImage("img/shot_h.png");
        }else{
            loadImage("img/shot_v.png");
        }
        getImageDimensions();        
    }

    public void move() {
        
        switch(direction){
            case "right": x += MISSILE_SPEED; break;
            case "left": x -= MISSILE_SPEED; break;
            case "up": y -= MISSILE_SPEED; break;
            case "down": y += MISSILE_SPEED; break;
        }
        
        if (x > BOARD_WIDTH || x < 0 || y > 390 || x < 0)
            visible = false;
    }
}