package game;

import sound.SFX;
import main.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class GameKeys implements KeyListener {

    private final GameView game;
    
    public GameKeys(GameView g) {
        this.game = g;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key==KeyEvent.VK_ESCAPE) {
            try {
                Window.getMainGame().getTimer().cancel();
                this.game.stopGameMusic();
                Window.WINDOW.launchMenu();
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println(e1);
               System.exit(1);
            }
            return;
        }
        if (key == Constants.CUSTOM_KEYS.getShoot()){
            game.fire();
            try {
                SFX pew = new SFX("ressources/audio/pew.wav");
                pew.playSound();
            } catch (Exception e2) {
                e2.printStackTrace();
                System.out.println(e2);
                System.exit(1);
            }
        }
        if (key == Constants.CUSTOM_KEYS.getUp()){
            if(game.getSpaceShip().timerStartFlag){game.getSpaceShip().timerStartFlag = false;}
            if(game.getSpaceShip().moveFlag == false)game.getSpaceShip().moveTime = System.currentTimeMillis();
            game.getSpaceShip().canDecelerate = false;
            game.getSpaceShip().moveFlag = true;
            game.getSpaceShip().canAccelerate = true;
        }
        if (key == Constants.CUSTOM_KEYS.getLeft()) {
            if(game.getSpaceShip().timerStartFlag){game.getSpaceShip().timerStartFlag = false;}
            game.getSpaceShip().leftRotationFlag = true;
        }
        if (key == Constants.CUSTOM_KEYS.getRight()) {
            if(game.getSpaceShip().timerStartFlag){game.getSpaceShip().timerStartFlag = false;}
            game.getSpaceShip().rightRotationFlag = true;
        }
        if (key == Constants.CUSTOM_KEYS.getShield()){
            if(!game.getSpaceShip().shield.isActive() && game.getSpaceShip().shield.getQuantity() > 0){
                game.getSpaceShip().shield.enable();
            }else{
                game.getSpaceShip().shield.disable();
            }
        }
        if (key == Constants.CUSTOM_KEYS.getSwitch()){
            game.getSpaceShip().missile_switch += 1;
            if(game.getSpaceShip().missile_switch > 2){
                game.getSpaceShip().missile_switch = 1;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == Constants.CUSTOM_KEYS.getRight()) {
            game.getSpaceShip().rightRotationFlag = false;
        }
        if (key == Constants.CUSTOM_KEYS.getLeft()) {
            game.getSpaceShip().leftRotationFlag = false;
        }
        if (key == Constants.CUSTOM_KEYS.getUp()) {
            game.getSpaceShip().canDecelerate = true;
            game.getSpaceShip().canAccelerate = false;
            game.getSpaceShip().moveFlag = false;
        }
    }
    
}