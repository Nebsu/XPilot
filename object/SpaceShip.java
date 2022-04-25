/**
 * The SpaceShip class is a Sprite that can move, rotate, take damage, and consume fuel
 */

package object;

import main.Constants;

public class SpaceShip extends Sprite {
    public boolean timerStartFlag = true;
    public Shield shield = new Shield();
    private int health = Constants.MAX_HEALTH;
    //Rotation and Speed
    public long moveTime;
    public int rotation;
    public float SPEED = 4;
    public boolean rightRotationFlag = false;
    public boolean leftRotationFlag = false;
    public boolean moveFlag = false,  canAccelerate = false, canDecelerate = false;
    //Damage tick
    private long t0, timer = 0;
    //Fuel
    private int fuel = Constants.BASE_FUEL;
    private long ft0, ftimer = 0;
    //Missile
    public int missile_switch = 1;
    public int missile_left = Constants.MAX_MISSILE_SHOT;

    public SpaceShip(double x, double y){
        super(x, y);
        loadImage("ressources/images/ship2.png");
        getImageDimensions();
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return Constants.MAX_HEALTH;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public int getFuel(){
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

/**
 * Rotate the camera to the right.
 */
    public void rotateRight(){
        if(rightRotationFlag){
           rotation += Constants.rotationRate;
           if(rotation > 360) rotation = Constants.rotationRate;
        }
    }

/**
 * Rotate the camera left.
 */
    public void rotateLeft(){
        if(leftRotationFlag){
           rotation -= Constants.rotationRate;
           if(rotation < -360) rotation = Constants.rotationRate;
        }
    }

    public void acceleration(){
        if(canAccelerate){
            if(SPEED < Constants.BASE_SPEED)SPEED = Constants.BASE_SPEED;
            if(SPEED < Constants.MAX_SPEED)SPEED += (float)((double)System.currentTimeMillis()/1000-(double)moveTime/1000)/100;
            else SPEED = Constants.MAX_SPEED;
        }
    }

    public void deceleration(){
        if(canDecelerate){
            if(SPEED <= 0){
                SPEED = Constants.BASE_SPEED;
                canDecelerate = false;
            }
            if(SPEED <= 2.5){
                SPEED -= 0.02;
            }else if(SPEED <= 6){
                SPEED -= 0.1;
            }else{
                SPEED -= 0.6;
            }
        }
    }

/**
 * Move the player in the direction it is facing
 */
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

/**
 * It checks if the cooldown period has passed.
 * 
 * @return The boolean value of whether or not the player can take damage.
 */
    public boolean canTakeDamage() {
        boolean res;
        long delta = System.currentTimeMillis() - t0;
        timer += delta;
        if (timer > Constants.COOLDOWN) {
            timer = 0;
            res = true;
        } else {
            res = false;
        }
        t0 = System.currentTimeMillis();
        return res;
    }

/**
 * Consume fuel at a rate of CONSUME_RATE every CONSUME_SPEED milliseconds
 */
    public void consumeFuel(){
        long delta = System.currentTimeMillis() - ft0;
        ftimer += delta;
        if(ftimer > Constants.CONSUME_SPEED){
            fuel -= Constants.CONSUME_RATE;
            ftimer = 0;
        }
        ft0 = System.currentTimeMillis();
    }
}