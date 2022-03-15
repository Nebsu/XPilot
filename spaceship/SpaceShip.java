package spaceship;

import main.Board;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.KeyListener;

public class SpaceShip extends Sprite implements KeyListener {

    private long moveTime;
    private List<Missile> missiles;
    boolean timerStartFlag = true;
    public static int rotation;
    int rotOneInc = 6;
    public float SPEED = 4;
    public final float MAX_SPEED = 10, BASE_SPEED = 4;

    public SpaceShip(double x, double y) {
        super(x, y);
        initCraft();
    }

    private void initCraft() {
        missiles = new ArrayList<>();
        loadImage("ressources/player_right.png");
        getImageDimensions();
    }

    public void rotateRight(boolean canRotate)
    {
        if(canRotate)
        {
           rotation += rotOneInc;
           if(rotation > 360) { rotation = rotOneInc;}
        }
    }

    public void rotateLeft(boolean canRotate)
    {
        if(canRotate)
        {
           rotation -= rotOneInc;
           if(rotation == rotOneInc) { rotation = rotOneInc;}
        }
    }

    public void acceleration(boolean canMove){
        if(canMove && Board.canAccelerate){
            SPEED = (((float)(Board.moveTime-moveTime)/1000)+BASE_SPEED);
        }
    }

    public void move(boolean canMove) {
        if(canMove){
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

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE){
            Board.timer.start();
            fire();
        }
        if (key == KeyEvent.VK_UP){
            Board.timer.start();
            if(timerStartFlag){timerStartFlag = false; Board.timer.start();}
            if(Board.moveFlag == false)moveTime = System.currentTimeMillis();
            Board.moveFlag = true;
            Board.canAccelerate = true;
        }
        if (key == KeyEvent.VK_LEFT) {
            if(timerStartFlag){timerStartFlag = false; Board.timer.start();}
            Board.leftRotationFlag = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            if(timerStartFlag){timerStartFlag = false; Board.timer.start();}
            Board.rightRotationFlag = true;
        }
    }

    public void fire() {
        missiles.add(new Missile(x + width, y + height / 2));
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT) {
            Board.rightRotationFlag = false;
        }
        if (key == KeyEvent.VK_LEFT) {
            Board.leftRotationFlag = false;
        }
        if (key == KeyEvent.VK_UP) {
            Board.canAccelerate = false;
            Board.moveFlag = false;
            SPEED = BASE_SPEED;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }
    
}