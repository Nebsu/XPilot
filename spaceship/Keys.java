package spaceship;

import main.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keys implements KeyListener {

    SpaceShip spaceship;

    public Keys(SpaceShip s){
        this.spaceship = s;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key==KeyEvent.VK_ESCAPE) {
            try {
                Constants.TIMER.stop();
                Constants.WINDOW.setVisible(false);
                Constants.GAME.stopMusic();
                Constants.WINDOW = new Window(true);
                Constants.WINDOW.setVisible(true);
                Constants.MENU.playMusic();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return;
        }
        if (key == KeyEvent.VK_SPACE){
            Constants.TIMER.start();
            spaceship.fire();
        }
        if (key == KeyEvent.VK_UP){
            Constants.TIMER.start();
            if(spaceship.timerStartFlag){spaceship.timerStartFlag = false; Constants.TIMER.start();}
            if(spaceship.moveFlag == false)spaceship.moveTime = System.currentTimeMillis();
            spaceship.canDecelerate = false;
            spaceship.moveFlag = true;
            spaceship.canAccelerate = true;
        }
        if (key == KeyEvent.VK_LEFT) {
            if(spaceship.timerStartFlag){spaceship.timerStartFlag = false; Constants.TIMER.start();}
            spaceship.leftRotationFlag = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            if(spaceship.timerStartFlag){spaceship.timerStartFlag = false; Constants.TIMER.start();}
            spaceship.rightRotationFlag = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT) {
            spaceship.rightRotationFlag = false;
        }
        if (key == KeyEvent.VK_LEFT) {
            spaceship.leftRotationFlag = false;
        }
        if (key == KeyEvent.VK_UP) {
            spaceship.canDecelerate = true;
            spaceship.canAccelerate = false;
            spaceship.moveFlag = false;
        }
    }
    
}