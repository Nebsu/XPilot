package spaceship;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import main.Constants;

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
        if(canMove && Constants.canAccelerate){
            SPEED = (((float)(Constants.moveTime-moveTime)/1000)+BASE_SPEED);
        }
    }

    public void deceleration(){
        System.out.println(Constants.canDecelerate);
        if(Constants.canDecelerate){
            SPEED -= 0.2;
        }
        if(SPEED <= BASE_SPEED){
            SPEED = BASE_SPEED;
            Constants.canDecelerate = false;
        }
    }

    public void move(boolean canMove) {
        if(canMove || Constants.canDecelerate){
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
            Constants.timer.start();
            fire();
        }
        if (key == KeyEvent.VK_UP){
            Constants.timer.start();
            if(timerStartFlag){timerStartFlag = false; Constants.timer.start();}
            if(Constants.moveFlag == false)moveTime = System.currentTimeMillis();
            Constants.moveFlag = true;
            Constants.canAccelerate = true;
        }
        if (key == KeyEvent.VK_LEFT) {
            if(timerStartFlag){timerStartFlag = false; Constants.timer.start();}
            Constants.leftRotationFlag = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            if(timerStartFlag){timerStartFlag = false; Constants.timer.start();}
            Constants.rightRotationFlag = true;
        }
    }

    public void fire() {
        missiles.add(new Missile(x + width, y + height / 2));
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT) {
            Constants.rightRotationFlag = false;
        }
        if (key == KeyEvent.VK_LEFT) {
            Constants.leftRotationFlag = false;
        }
        if (key == KeyEvent.VK_UP) {
            Constants.canDecelerate = true;
            Constants.canAccelerate = false;
            Constants.moveFlag = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }
    
}