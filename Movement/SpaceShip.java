package Movement;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SpaceShip extends Sprite {

    private int dx;
    private int dy;
    private List<Missile> missiles;
    private String direction = "right";

    public SpaceShip(int x, int y) {
        super(x, y);

        initCraft();
    }

    private void initCraft() {
        
        missiles = new ArrayList<>();
        loadImage("img/player_right.png");
        getImageDimensions();
    }

    public void move() {

        x += dx;
        y += dy;

        if (x < 1) {
            x = 1;
        }

        if (y < 1) {
            y = 1;
        }
    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            fire();
        }

        if (key == KeyEvent.VK_LEFT) {
            direction = "left";
            loadImage("img/player_left.png");
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
            direction = "right";
            loadImage("img/player_right.png");
            dx = 2;
        }

        if (key == KeyEvent.VK_UP) {
            direction = "up";
            loadImage("img/player_up.png");
            dy = -2;
        }

        if (key == KeyEvent.VK_DOWN) {
            direction = "down";
            loadImage("img/player_down.png");
            dy = 2;
        }
        if (key == KeyEvent.VK_UP && key == KeyEvent.VK_RIGHT){
            System.out.println("Yes");
        }
    }

    public void fire() {
        if(direction.equals("right") || direction.equals("left"))missiles.add(new Missile(x + width, y + height / 2, direction));
        if(direction.equals("up") || direction.equals("down"))missiles.add(new Missile(x + width/2, y + height / 2, direction));

    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}