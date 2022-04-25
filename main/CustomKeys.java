/**
 * The CustomKeys class is a class that contains the keys used to control the game
 */
package main;

import java.awt.event.KeyEvent;

public final class CustomKeys {


    private int up = KeyEvent.VK_UP; // avancer
    private int left = KeyEvent.VK_LEFT; // rotation gauche
    private int right = KeyEvent.VK_RIGHT; // rotation droite
    private int shoot = KeyEvent.VK_SPACE; // trier missile
    private int shield = KeyEvent.VK_C; // bouclier
    private int mode = KeyEvent.VK_X; // changer le mode du missile
    private int fullScreen = KeyEvent.VK_F; //Basculer en mode plein ecran

    public final int getUp() {return up;}
    public final int getLeft() {return left;}
    public final int getRight() {return right;}
    public final int getShoot() {return shoot;}
    public final int getShield() {return shield;}
    public final int getSwitch() {return mode;}

    public final void WASD_MODE(boolean status) {
        Constants.WASD_MODE = status;
        if (status) {
            this.up = KeyEvent.VK_W;
            this.left = KeyEvent.VK_A;
            this.right = KeyEvent.VK_D;
            this.shoot = KeyEvent.VK_SPACE;
            this.shield = KeyEvent.VK_B;
            this.mode = KeyEvent.VK_M;
        } else {
            this.up = KeyEvent.VK_UP;
            this.left = KeyEvent.VK_LEFT;
            this.right = KeyEvent.VK_RIGHT;
            this.shoot = KeyEvent.VK_SPACE;
            this.shield = KeyEvent.VK_C;
            this.mode = KeyEvent.VK_X;
        }
    }

}