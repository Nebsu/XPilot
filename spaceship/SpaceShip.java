package spaceship;

import java.util.ArrayList;
import java.util.List;

public class SpaceShip extends Sprite {

    public long moveTime;
    public long moveTime2;
    private List<Missile> missiles;
    public boolean timerStartFlag = true;
    public int rotation;
    public final int rotationRate = 6;
    public float SPEED = 4;
    public final float MAX_SPEED = 10, BASE_SPEED = 4;
    public boolean rightRotationFlag = false;
    public boolean leftRotationFlag = false;
    public boolean moveFlag = false,  canAccelerate = false, canDecelerate = false;

    public SpaceShip(double x, double y){
        super(x, y);
        missiles = new ArrayList<>();
        loadImage("ressources/player_right.png");
        getImageDimensions();
    }

    public void rotateRight(boolean canRotate){
        if(canRotate){
           rotation += rotationRate;
           if(rotation > 360) rotation = rotationRate;
        }
    }

    public void rotateLeft(boolean canRotate){
        if(canRotate){
           rotation -= rotationRate;
           if(rotation == rotationRate) rotation = rotationRate;
        }
    }

    public void acceleration(){
        if(canAccelerate){
            if(SPEED < BASE_SPEED)SPEED = BASE_SPEED;
            if(SPEED < MAX_SPEED)SPEED += (float)((double)moveTime2/1000-(double)moveTime/1000)/100;
            else SPEED = MAX_SPEED;
        }
    }

    public void deceleration(){
        if(canDecelerate){
            if(SPEED <= 1){
                SPEED = BASE_SPEED;
                canDecelerate = false;
            }
            if(SPEED <= 2.5){
                SPEED -= 0.009;
            }else if(SPEED <= 6){
                SPEED -= 0.1;
            }else{
                SPEED -= 0.6;
            }
        }
    }

    public void move() {
        if(moveFlag || canDecelerate){
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