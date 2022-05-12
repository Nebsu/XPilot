/**
 * The SpaceShip class is a Sprite that can move, rotate, take damage, and consume fuel
 */

package object;

import main.Constants;

public final class SpaceShip extends Sprite {

    // Shield and hits :
    private boolean timerStartFlag = true;
    private Shield shield = new Shield();
    private int health = Constants.MAX_HEALTH;

    // Rotation and speed :s
    private long moveTime;
    private int rotation;
    private float SPEED = 4;
    private boolean rightRotationFlag = false;
    private boolean leftRotationFlag = false;
    private boolean moveFlag = false,  canAccelerate = false, canDecelerate = false;

    // Damage tick
    private long t0, timer = 0;

    // Fuel
    private int fuel = Constants.BASE_FUEL;
    private long ft0, ftimer = 0;

    // Missiles :
    private int missile_switch = 1;
    private int missile_left = Constants.MAX_MISSILE_SHOT;

    public SpaceShip(double x, double y){
        super(x, y);
        loadImage("ressources/images/ship2.png");
        getImageDimensions();
    }

    // Getters / Setters :
    public final boolean getTimerStartFlag() {return timerStartFlag;}
    public final Shield getShield() {return shield;}
    public final int getHealth() {return health;}
    public final long getMoveTime() {return moveTime;}
    public final int getRotation() {return rotation;}
    public final float getSpeed() {return SPEED;}
    public final boolean getRightRotationFlag() {return rightRotationFlag;}
    public final boolean getLeftRotationFlag() {return leftRotationFlag;}
    public final boolean getMoveFlag() {return moveFlag;}
    public final boolean canAccelerate() {return canAccelerate;}
    public final boolean canDecelerate() {return canDecelerate;}
    public final int getMaxHealth() {return Constants.MAX_HEALTH;}
    public final int getFuel() {return fuel;}
    public final int getMissileSwitch() {return missile_switch;}
    public final int getMissileLeft() {return missile_left;}
    public final void setTimerStartFlag(boolean b) {timerStartFlag  = b;}
    public final void setHealth(int health) {this.health = health;}
    public final void setRightRotationFlag(boolean r) {rightRotationFlag = r;}
    public final void setLeftRotationFlag(boolean l) {leftRotationFlag = l;}
    public final void setMoveTime(long t) {moveTime = t;}
    public final void setMoveFlag(boolean b) {moveFlag = b;}
    public final void setCanAccelerate(boolean b) {canAccelerate = b;}
    public final void setCanDecelerate(boolean b) {canDecelerate = b;}
    public final void setFuel(int fuel) {this.fuel = fuel;}
    public final void setMissileSwitch(int m) {this.missile_switch = m;}
    public final void setMissileLeft(int m) {this.missile_left = m;}

    /** Rotate the camera to the right. */
    public final void rotateRight(){
        if(rightRotationFlag){
           rotation += Constants.ROTATION_RATE;
           if(rotation > 360) rotation = Constants.ROTATION_RATE;
        }
    }

    /** Rotate the camera left. */
    public final void rotateLeft(){
        if(leftRotationFlag){
           rotation -= Constants.ROTATION_RATE;
           if(rotation < -360) rotation = Constants.ROTATION_RATE;
        }
    }

    public final void acceleration(){
        if(canAccelerate){
            if(SPEED < Constants.BASE_SPEED) SPEED = Constants.BASE_SPEED;
            if(SPEED < Constants.MAX_SPEED)
                SPEED += (float)((double)System.currentTimeMillis()/1000-(double)moveTime/1000)/100;
            else SPEED = Constants.MAX_SPEED;
        }
    }

    public final void deceleration(){
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

    /** Move the player in the direction it is facing. */
    public final void move() {
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
    public final boolean canTakeDamage() {
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
    public final void consumeFuel(){
        long delta = System.currentTimeMillis() - ft0;
        ftimer += delta;
        if(ftimer > Constants.CONSUME_SPEED){
            fuel -= Constants.CONSUME_RATE;
            ftimer = 0;
        }
        ft0 = System.currentTimeMillis();
    }
}