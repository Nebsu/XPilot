/** The CustomKeys class is a class that contains the keys used to control the game.
 *  It's main feature is to manage the WASD mode, which has differents control keys
 *  designed for left handed users.
 */

package main;

import java.awt.event.KeyEvent;

public final class CustomKeys {

    // Default keys :
    private int up = KeyEvent.VK_UP;
    private int left = KeyEvent.VK_LEFT;
    private int right = KeyEvent.VK_RIGHT;
    private int shoot = KeyEvent.VK_SPACE;
    private int shield = KeyEvent.VK_C;
    private int mode = KeyEvent.VK_X;
    private int fullScreen = KeyEvent.VK_F;

    public final int getUp() {return up;}
    public final int getLeft() {return left;}
    public final int getRight() {return right;}
    public final int getShoot() {return shoot;}
    public final int getShield() {return shield;}
    public final int getSwitch() {return mode;}
    public final int getFullScreen() {return fullScreen;}

    public final void WASD_MODE(boolean status) {
        Global.setWASD_MODE(status);
        if (status) {
            this.up = KeyEvent.VK_W;
            this.left = KeyEvent.VK_A;
            this.right = KeyEvent.VK_D;
            this.shoot = KeyEvent.VK_ENTER;
            this.shield = KeyEvent.VK_L;
            this.mode = KeyEvent.VK_M;
            this.fullScreen = KeyEvent.VK_P;
        } else {
            this.up = KeyEvent.VK_UP;
            this.left = KeyEvent.VK_LEFT;
            this.right = KeyEvent.VK_RIGHT;
            this.shoot = KeyEvent.VK_SPACE;
            this.shield = KeyEvent.VK_C;
            this.mode = KeyEvent.VK_X;
            this.fullScreen = KeyEvent.VK_F;
        }
    }

}