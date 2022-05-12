package object;

import main.Constants;

import java.io.File;
import java.io.IOException;
import java.awt.image.*;
import javax.imageio.ImageIO;

public final class Enemy {
    
    private BufferedImage image;
    private double x;
    private double y;
    private long t0, timer = 0;
    private int health = 150;

    public Enemy(double x, double y) throws IOException {
        this.x = x;
        this.y = y;
        this.image=ImageIO.read(new File("ressources/images/ship.png"));
    }

    // Getters :
    public final BufferedImage getImage() {return image;}
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