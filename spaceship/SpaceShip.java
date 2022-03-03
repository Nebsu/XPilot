package spaceship;

import java.util.ArrayList;
import java.util.List;

public class SpaceShip extends Sprite {

    public long moveTime;
    private List<Missile> missiles;
    boolean timerStartFlag = true;
    public int rotation;
    int rotOneInc = 6;
    public float SPEED = 4;
    public final float MAX_SPEED = 10, BASE_SPEED = 4;
    public double hitX, hitY;
    public boolean collision = false;
    public boolean rightRotationFlag = false;
    public boolean leftRotationFlag = false;
    public boolean moveFlag = false,  canAccelerate = false, canDecelerate = false;
    public long moveTime2;

    public SpaceShip(double x, double y) {
        super(x, y);
        initCraft();
    }

    private void initCraft() {
        missiles = new ArrayList<>();
        loadImage("ressources/player_right.png");
        getImageDimensions();
    }

    public void rotateRight(boolean canRotate)
    {
        if(canRotate)
        {
           rotation += rotOneInc;
           if(rotation > 360) { rotation = rotOneInc;}
        }
    }

    public void rotateLeft(boolean canRotate)
    {
        if(canRotate)
        {
           rotation -= rotOneInc;
           if(rotation == rotOneInc) { rotation = rotOneInc;}
        }
    }

    public void acceleration(){
        if(canAccelerate){
            SPEED = (float)Math.exp(((double)((double)moveTime2/1000-(double)moveTime/1000))+BASE_SPEED)/10;
        }
    }

    public void deceleration(){
        if(canDecelerate){
            SPEED -= 1/SPEED;
        }
        if(SPEED <= 1){
            SPEED = BASE_SPEED;
            canDecelerate = false;
        }
    }

    public void move(boolean canMove) {
        if(canMove || canDecelerate){
            x += SPEED * Math.cos(Math.toRadians(rotation));
            y += SPEED * Math.sin(Math.toRadians(rotation));
            if (x < 1) {
                x = 1;
            }
            if (y < 1) {
                y = 1;
            }
        }
    }


    public List<Missile> getMissiles() {
        return missiles;
    }

    public void fire() {
        missiles.add(new Missile(400, 300, rotation));
    }

}