package spaceship;

import main.*;
import sound.SFX;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keys implements KeyListener {

    private SpaceShip spaceship;
    private SFX pew;

    public Keys(SpaceShip s){
        this.spaceship = s;
        this.pew = new SFX("ressources/pew.wav");
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
        if (key == Constants.SHOOT){
            Constants.TIMER.start();
            spaceship.fire();
            try {
                pew.playSound();
                pew.stopSound();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (key == Constants.UP){
            Constants.TIMER.start();
            if(spaceship.timerStartFlag){spaceship.timerStartFlag = false; Constants.TIMER.start();}
            if(spaceship.moveFlag == false)spaceship.moveTime = System.currentTimeMillis();
            spaceship.canDecelerate = false;
            spaceship.moveFlag = true;
            spaceship.canAccelerate = true;
        }
        if (key == Constants.LEFT) {
            if(spaceship.timerStartFlag){spaceship.timerStartFlag = false; Constants.TIMER.start();}
            spaceship.leftRotationFlag = true;
        }
        if (key == Constants.RIGHT) {
            if(spaceship.timerStartFlag){spaceship.timerStartFlag = false; Constants.TIMER.start();}
            spaceship.rightRotationFlag = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == Constants.RIGHT) {
            spaceship.rightRotationFlag = false;
        }
        if (key == Constants.LEFT) {
            spaceship.leftRotationFlag = false;
        }
        if (key == Constants.UP) {
            spaceship.canDecelerate = true;
            spaceship.canAccelerate = false;
            spaceship.moveFlag = false;
        }
    }
    
}