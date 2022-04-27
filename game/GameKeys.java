/**
 * This class is used to handle the keyboard inputs
 */
package game;

import main.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class GameKeys implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

    }

/**
 * * If the escape key is pressed, the GameLoop.game is stopped and the menu is launched.
 * * If the shoot key is pressed, the spaceship fires a missile.
 * * If the up key is pressed, the spaceship accelerates.
 * * If the left key is pressed, the spaceship rotates to the left.
 * * If the right key is pressed, the spaceship rotates to the right.
 * * If the shield key is pressed, the spaceship activates or deactivates its shield.
 * * If the switch key is pressed, the spaceship changes its missile type
 * 
 * @param e The KeyEvent object that contains information about the key that was pressed.
 */
    @Override
    public void keyPressed(KeyEvent e) {
        if (Constants.isMenu) return;
        int key = e.getKeyCode();
        if (key==KeyEvent.VK_ESCAPE) {
            try {
                GameLoop.gameMusic.stopMusic();
                GameLoop.win.launchMenu(true);
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println(e1);
                System.exit(1);
            }
            return;
        }
        if (key == Constants.CUSTOM_KEYS.getShoot()){
            GameLoop.view.fire();
        }
        if (key == Constants.CUSTOM_KEYS.getUp()){
            if(GameLoop.game.getShip().timerStartFlag){GameLoop.game.getShip().timerStartFlag = false;}
            if(!GameLoop.game.getShip().moveFlag)GameLoop.game.getShip().moveTime = System.currentTimeMillis();
            GameLoop.game.getShip().canDecelerate = false;
            GameLoop.game.getShip().moveFlag = true;
            GameLoop.game.getShip().canAccelerate = true;

        }
        if (key == Constants.CUSTOM_KEYS.getLeft()) {
            if(GameLoop.game.getShip().timerStartFlag){GameLoop.game.getShip().timerStartFlag = false;}
            GameLoop.game.getShip().leftRotationFlag = true;
        }
        if (key == Constants.CUSTOM_KEYS.getRight()) {
            if(GameLoop.game.getShip().timerStartFlag){GameLoop.game.getShip().timerStartFlag = false;}
            GameLoop.game.getShip().rightRotationFlag = true;
        }
        if (key == Constants.CUSTOM_KEYS.getShield()){
            if(!GameLoop.game.getShip().shield.isActive() && GameLoop.game.getShip().shield.getQuantity() > 0){
                GameLoop.game.getShip().shield.enable();
            }else{
                GameLoop.game.getShip().shield.disable();
            }
        }
        if (key == Constants.CUSTOM_KEYS.getSwitch()){
            GameLoop.game.getShip().missile_switch += 1;
            if(GameLoop.game.getShip().missile_switch > 2){
                GameLoop.game.getShip().missile_switch = 1;
            }
        }
        if (key == Constants.CUSTOM_KEYS.getFullScreen()){
            GameLoop.actFullScreen = true;
            GameLoop.fullScreenMode = !GameLoop.fullScreenMode;
            if (GameLoop.fullScreenMode && GameLoop.actFullScreen) {
                GameLoop.win.setDimensionsToFullScreen();
                GameLoop.actFullScreen = false;
                GameLoop.view = new GameView(GameLoop.game);
                GameLoop.win.setFullScreen(GameLoop.view);
            }
            if (!GameLoop.fullScreenMode && GameLoop.actFullScreen) {
                GameLoop.win.setDimensionsToSmallScreen();
                GameLoop.actFullScreen = false;
                GameLoop.view = new GameView(GameLoop.game);
                GameLoop.win.setSmallScreen(GameLoop.view);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (Constants.isMenu) return;
        int key = e.getKeyCode();
        if (key == Constants.CUSTOM_KEYS.getRight()) {
            GameLoop.game.getShip().rightRotationFlag = false;
        }
        if (key == Constants.CUSTOM_KEYS.getLeft()) {
            GameLoop.game.getShip().leftRotationFlag = false;
        }
        if (key == Constants.CUSTOM_KEYS.getUp()) {
            GameLoop.game.getShip().canDecelerate = true;
            GameLoop.game.getShip().canAccelerate = false;
            GameLoop.game.getShip().moveFlag = false;
        }
    }
    
}