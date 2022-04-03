package main;

import menu.*;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Timer;

public class Constants {
    //Coordonnées du vaisseau
    public static final int ICRAFT_X = 200;
    public static final int ICRAFT_Y = 200;
    //Taille de la fenêtre
    public static final int B_WIDTH = 800;
    public static final int B_HEIGHT = 600;
    //Coordonnées de la balle
    public static final int BALL_X = 100;
    public static final int BALL_Y = 200;
    //Temps d'apparition des bonus
    public static final int BONUS_SPAWNRATE = 5000;
    public static Timer TIMER;
    public static Window WINDOW;
    public static final MenuPanel MENU = new MenuPanel();
    public static final Board GAME = createBoard();
    private static Board createBoard(){
        try {
            return new Board();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // KEYS :
    public static int UP = KeyEvent.VK_UP; // avancer
    public static int LEFT = KeyEvent.VK_LEFT; // rotation gauche
    public static int RIGHT = KeyEvent.VK_RIGHT; // rotation droite
    public static int SHOOT = KeyEvent.VK_SPACE; // trier missile
    public static int SHIELD = KeyEvent.VK_C; // bouclier
    public static int SWITCH = KeyEvent.VK_S; // changer le mode du missile

}
