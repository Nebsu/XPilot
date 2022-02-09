package Xpilot;
import java.awt.Image;
import javax.swing.ImageIcon;

public class SpaceShip {
    private int dx;
    private int dy;
    private int x = 40;
    private int y = 60;
    private int w;
    private int h;
    private Image image;
    public final int screenX;
    public final int screenY;
    public SpaceShip() {
        loadImage();
        screenX=800/2-(40/2);
        screenY=600/2-(60/2);
    }
    private void loadImage() { 
        ImageIcon ii = new ImageIcon("E://E/craft.png");
        image = ii.getImage(); 
        w = image.getWidth(null);
        h = image.getHeight(null);
    }
    public void move() {
        x += dx;
        y += dy;
    }
    public void setX(int x) {
        this.x+=x;
    }
    public void setY(int y) {
        this.y+=y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWidth() {
        return w;
    }
    public int getHeight() {
        return h;
    }    
    public Image getImage() {
        return image;
    }
}