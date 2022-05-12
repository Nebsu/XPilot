/** This class is used to handle the keyboard inputs. */

package src.game;

import src.main.*;

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
     * * If the switch key is pressed, the spaceship changes its missile type.
     * 
     * @param e The KeyEvent object that contains information about the key that was pressed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (Globals.IS_MENU()) return;
        int key = e.getKeyCode();
        if (key==KeyEvent.VK_ESCAPE) {
            try {
                Globals.changeTopMenuState(true);
                Constants.GAME_MUSIC.stopMusic();
                Globals.MAINGAME().getWindow().launchMenu(true, false);
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println(e1);
                System.exit(1);
            }
            return;
        }
        if (key == Constants.CUSTOM_KEYS.getShoot()){
            Globals.MAINGAME().getView().fire();
        }
        if (key == Constants.CUSTOM_KEYS.getUp()){
            if(Globals.MAINGAME().getGame().getShip().getTimerStartFlag()){
                Globals.MAINGAME().getGame().getShip().setTimerStartFlag(false);
            }
            if(!Globals.MAINGAME().getGame().getShip().getMoveFlag())
                Globals.MAINGAME().getGame().getShip().setMoveTime( System.currentTimeMillis());
            Globals.MAINGAME().getGame().getShip().setCanDecelerate(false);
            Globals.MAINGAME().getGame().getShip().setMoveFlag(true);
            Globals.MAINGAME().getGame().getShip().setCanAccelerate(true);

        }
        if (key == Constants.CUSTOM_KEYS.getLeft()) {
            if(Globals.MAINGAME().getGame().getShip().getTimerStartFlag()){
                Globals.MAINGAME().getGame().getShip().setTimerStartFlag(false);
            }
            Globals.MAINGAME().getGame().getShip().setLeftRotationFlag(true);
        }
        if (key == Constants.CUSTOM_KEYS.getRight()) {
            if(Globals.MAINGAME().getGame().getShip().getTimerStartFlag()){
                Globals.MAINGAME().getGame().getShip().setTimerStartFlag(false);
            }
            Globals.MAINGAME().getGame().getShip().setRightRotationFlag(true);
        }
        if (key == Constants.CUSTOM_KEYS.getShield()){
            if(!Globals.MAINGAME().getGame().getShip().getShield().isActive() && 
                Globals.MAINGAME().getGame().getShip().getShield().getQuantity() > 0){
                Globals.MAINGAME().getGame().getShip().getShield().enable();
            }else{
                Globals.MAINGAME().getGame().getShip().getShield().disable();
            }
        }
        if (key == Constants.CUSTOM_KEYS.getSwitch()){
            Globals.MAINGAME().getGame().getShip().setMissileSwitch(
            Globals.MAINGAME().getGame().getShip().getMissileSwitch() + 1);
            if(Globals.MAINGAME().getGame().getShip().getMissileSwitch() > 2){
                Globals.MAINGAME().getGame().getShip().setMissileSwitch(1);
            }
        }
        if (key == Constants.CUSTOM_KEYS.getFullScreen()){
            Globals.MAINGAME().switchFullScreenMode();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (Globals.IS_MENU()) return;
        int key = e.getKeyCode();
        if (key == Constants.CUSTOM_KEYS.getRight()) {
            Globals.MAINGAME().getGame().getShip().setRightRotationFlag(false);
        }
        if (key == Constants.CUSTOM_KEYS.getLeft()) {
            Globals.MAINGAME().getGame().getShip().setLeftRotationFlag(false);
        }
        if (key == Constants.CUSTOM_KEYS.getUp()) {
            Globals.MAINGAME().getGame().getShip().setCanDecelerate(true);
            Globals.MAINGAME().getGame().getShip().setCanAccelerate(false);
            Globals.MAINGAME().getGame().getShip().setMoveFlag(false);
        }
    }
    
}