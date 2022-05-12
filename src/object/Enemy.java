package src.object;

import src.main.Constants;

import java.io.IOException;

public final class Enemy extends Sprite{
    
    private long t0, timer = 0;
    private int health = 150;

    public Enemy(double x, double y) throws IOException {
        super(x,y);
        loadImage("ressources/images/ship.png");
        getImageDimensions();
    }

    // Getters :
    public final double getX() {return x;}
    public final double getY() {return y;}
    public int getHealth() {return health;}
    public void setHealth(int health) {this.health = health;
}

    public final double getRad(double vx, double vy){
        return -Math.atan2(this.x - vx, this.y -vy)-Math.toRadians(90);
    }

    public final void fire() {}

    public final boolean canShoot() {
        boolean res;
        long delta = System.currentTimeMillis() - t0;
        timer += delta;
        if (timer > Constants.SHOOT_RATE) {
            timer = 0;
            res = true;
        } else {
            res = false;
        }
        t0 = System.currentTimeMillis();
        return res;
    }

}