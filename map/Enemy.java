package map;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.*;

import javax.imageio.ImageIO;

public class Enemy{
    public BufferedImage image;
    public double x;
    public double y;
    private long t0, timer = 0;
    public final int COOLDOWN = 1000;

    public Enemy(double x, double y) throws IOException {
        this.x = x;
        this.y = y;
        this.image=ImageIO.read(new File("ressources/images/ship.png"));
    }

    public double getRad(double vx, double vy){
        return -Math.atan2(this.x - vx, this.y -vy)-Math.toRadians(90);
    }

    public void fire(){

    }

    public boolean canShoot() {
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
}
