/**
 * This class is used to handle the keyboard inputs
 */
package game;

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
        int key = e.getKeyCode();
        if (key==KeyEvent.VK_ESCAPE) {
            try {
                Window.getMainGame().getTimer().cancel();
                this.game.stopGameMusic();
                Window.WINDOW.launchMenu(true);
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println(e1);
               System.exit(1);
            }
            return;
        }
        if (key == Constants.CUSTOM_KEYS.getShoot()){
            game.fire();
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