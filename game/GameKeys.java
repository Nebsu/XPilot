/**
 * This class is used to handle the keyboard inputs
 */

package game;

import main.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class GameKeys implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * * If the escape key is pressed, the Global.MAINGAME().getGame() is stopped and the menu is launched.
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
        if (Global.isMenu()) return;
        int key = e.getKeyCode();
        if (key==KeyEvent.VK_ESCAPE) {
            try {
                Constants.GAME_MUSIC.stopMusic();
                Global.MAINGAME().getWindow().launchMenu(true);
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println(e1);
                System.exit(1);
            }
            return;
        }
        if (key == Constants.CUSTOM_KEYS.getShoot()){
            Global.MAINGAME().getView().fire();
        }
        if (key == Constants.CUSTOM_KEYS.getUp()){
            if(Global.MAINGAME().getGame().getShip().timerStartFlag){Global.MAINGAME().getGame().getShip().timerStartFlag = false;}
            if(!Global.MAINGAME().getGame().getShip().moveFlag)Global.MAINGAME().getGame().getShip().moveTime = System.currentTimeMillis();
            Global.MAINGAME().getGame().getShip().canDecelerate = false;
            Global.MAINGAME().getGame().getShip().moveFlag = true;
            Global.MAINGAME().getGame().getShip().canAccelerate = true;

        }
        if (key == Constants.CUSTOM_KEYS.getLeft()) {
            if(Global.MAINGAME().getGame().getShip().timerStartFlag){Global.MAINGAME().getGame().getShip().timerStartFlag = false;}
            Global.MAINGAME().getGame().getShip().leftRotationFlag = true;
        }
        if (key == Constants.CUSTOM_KEYS.getRight()) {
            if(Global.MAINGAME().getGame().getShip().timerStartFlag){Global.MAINGAME().getGame().getShip().timerStartFlag = false;}
            Global.MAINGAME().getGame().getShip().rightRotationFlag = true;
        }
        if (key == Constants.CUSTOM_KEYS.getShield()){
            if(!Global.MAINGAME().getGame().getShip().shield.isActive() && Global.MAINGAME().getGame().getShip().shield.getQuantity() > 0){
                Global.MAINGAME().getGame().getShip().shield.enable();
            }else{
                Global.MAINGAME().getGame().getShip().shield.disable();
            }
        }
        if (key == Constants.CUSTOM_KEYS.getSwitch()){
            Global.MAINGAME().getGame().getShip().missile_switch += 1;
            if(Global.MAINGAME().getGame().getShip().missile_switch > 2){
                Global.MAINGAME().getGame().getShip().missile_switch = 1;
            }
        }
        if (key == Constants.CUSTOM_KEYS.getFullScreen()){
            Global.MAINGAME().switchFullScreenMode();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (Global.isMenu()) return;
        int key = e.getKeyCode();
        if (key == Constants.CUSTOM_KEYS.getRight()) {
            Global.MAINGAME().getGame().getShip().rightRotationFlag = false;
        }
        if (key == Constants.CUSTOM_KEYS.getLeft()) {
            Global.MAINGAME().getGame().getShip().leftRotationFlag = false;
        }
        if (key == Constants.CUSTOM_KEYS.getUp()) {
            Global.MAINGAME().getGame().getShip().canDecelerate = true;
            Global.MAINGAME().getGame().getShip().canAccelerate = false;
            Global.MAINGAME().getGame().getShip().moveFlag = false;
        }
    }
    
}