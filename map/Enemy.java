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
}
