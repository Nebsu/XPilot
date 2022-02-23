package spaceship;

import java.util.ArrayList;
import java.util.List;

import main.Constants;

public class SpaceShip extends Sprite {

    long moveTime;
    private List<Missile> missiles;
    boolean timerStartFlag = true;
    public static int rotation;
    int rotOneInc = 6;
    public float SPEED = 4;
    public final float MAX_SPEED = 10, BASE_SPEED = 4;

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

    public void acceleration(boolean canMove){
        if(canMove && Constants.canAccelerate){
            SPEED = (((float)(Constants.moveTime-moveTime)/1000)+BASE_SPEED);
        }
    }

    public void deceleration(){
        if(Constants.canDecelerate){
            SPEED -= 0.2;
        }
        if(SPEED <= BASE_SPEED){
            SPEED = BASE_SPEED;
            Constants.canDecelerate = false;
        }
    }

    public void move(boolean canMove) {
        if(canMove || Constants.canDecelerate){
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
        missiles.add(new Missile(x + width, y + height / 2));
    }

}