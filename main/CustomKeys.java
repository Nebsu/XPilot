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

    public final int getUp() {return up;}
    public final int getLeft() {return left;}
    public final int getRight() {return right;}
    public final int getShoot() {return shoot;}
    public final int getShield() {return shield;}
    public final int getSwitch() {return mode;}

    public final void setUp(int up) {this.up = up;}
    public final void setLeft(int left) {this.left = left;}
    public final void setRight(int right) {this.right = right;}
    public final void setShoot(int shoot) {this.shoot = shoot;}
    public final void setShield(int shield) {this.shield = shield;}
    public final void setMode(int mode) {this.mode = mode;}

}