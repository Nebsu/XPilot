/**
 * This class is used to handle the keyboard inputs
 */
package game;

import main.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class GameKeys implements KeyListener {

    private final Game game;
    private final GameView view;
    
    public GameKeys(Game g, GameView view) {
        this.game = g;
        this.view = view;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

/**
 * * If the escape key is pressed, the game is stopped and the menu is launched.
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
                this.view.stopGameMusic();
                GameLoop.win.launchMenu(true);
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println(e1);
               System.exit(1);
            }
            return;
        }
        if (key == Constants.CUSTOM_KEYS.getShoot()){
            view.fire();
        }
        if (key == Constants.CUSTOM_KEYS.getUp()){
            if(game.getShip().timerStartFlag){game.getShip().timerStartFlag = false;}
            if(!game.getShip().moveFlag)game.getShip().moveTime = System.currentTimeMillis();
            game.getShip().canDecelerate = false;
            game.getShip().moveFlag = true;
            game.getShip().canAccelerate = true;

        }
        if (key == Constants.CUSTOM_KEYS.getLeft()) {
            if(game.getShip().timerStartFlag){game.getShip().timerStartFlag = false;}
            game.getShip().leftRotationFlag = true;
        }
        if (key == Constants.CUSTOM_KEYS.getRight()) {
            if(game.getShip().timerStartFlag){game.getShip().timerStartFlag = false;}
            game.getShip().rightRotationFlag = true;
        }
        if (key == Constants.CUSTOM_KEYS.getShield()){
            if(!game.getShip().shield.isActive() && game.getShip().shield.getQuantity() > 0){
                game.getShip().shield.enable();
            }else{
                game.getShip().shield.disable();
            }
        }
        if (key == Constants.CUSTOM_KEYS.getSwitch()){
            game.getShip().missile_switch += 1;
            if(game.getShip().missile_switch > 2){
                game.getShip().missile_switch = 1;
            }
        }
        if (key == Constants.CUSTOM_KEYS.getFullScreen()){
            GameLoop.actFullScreen = true;
            GameLoop.fullScreenMode = !GameLoop.fullScreenMode;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (Constants.isMenu) return;
        int key = e.getKeyCode();
        if (key == Constants.CUSTOM_KEYS.getRight()) {
            game.getShip().rightRotationFlag = false;
        }
        if (key == Constants.CUSTOM_KEYS.getLeft()) {
            game.getShip().leftRotationFlag = false;
        }
        if (key == Constants.CUSTOM_KEYS.getUp()) {
            game.getShip().canDecelerate = true;
            game.getShip().canAccelerate = false;
            game.getShip().moveFlag = false;
        }
    }
    
}