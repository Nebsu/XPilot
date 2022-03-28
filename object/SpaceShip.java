package object;

public class SpaceShip extends Sprite {

    public long moveTime;
    public boolean timerStartFlag = true;
    public Shield shield = new Shield();
    private final int MAX_HEALTH = 1000;
    private int health = MAX_HEALTH;
    //Rotation and Speed
    public int rotation;
    public float SPEED = 4;
    public boolean rightRotationFlag = false;
    public boolean leftRotationFlag = false;
    public boolean moveFlag = false,  canAccelerate = false, canDecelerate = false;
    private final int rotationRate = 8;
    private final float MAX_SPEED = 10, BASE_SPEED = 4;
    //Damage tick
    private long t0, timer = 0;
    public final long COOLDOWN = 1000;
    //Fuel
    public final int BASE_FUEL = 5000;
    private int fuel = BASE_FUEL;
    private long ft0, ftimer = 0;
    private final int CONSUME_SPEED = 1000;
    private final int CONSUME_RATE = 100;

    public SpaceShip(double x, double y){
        super(x, y);
        loadImage("ressources/player_right.png");
        getImageDimensions();
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return MAX_HEALTH;
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
            if(SPEED < MAX_SPEED)SPEED += (float)((double)System.currentTimeMillis()/1000-(double)moveTime/1000)/100;
            else SPEED = MAX_SPEED;
        }
    }

    public void deceleration(){
        if(canDecelerate){
            if(SPEED <= 0){
                SPEED = BASE_SPEED;
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

    public boolean canTakeDamage() {
        boolean res;
        long delta = System.currentTimeMillis() - t0;
        timer += delta;
        if (timer > COOLDOWN) {
            timer = 0;
            res = true;
        } else {
            res = false;
        }
        t0 = System.currentTimeMillis();
        return res;
    }

    public void consumeFuel(){
        long delta = System.currentTimeMillis() - ft0;
        ftimer += delta;
        if(ftimer > CONSUME_SPEED){
            fuel -= CONSUME_RATE;
            ftimer = 0;
        }
        ft0 = System.currentTimeMillis();
    }
}