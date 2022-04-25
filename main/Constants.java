/**
 * The Constants class contains all the constants used in the game
 */
package main;

public final class Constants {

    // Dimensions de la fenêtre :
    public static int B_WIDTH = 800;
    public static int B_HEIGHT = 600;
    
    // Coordonnées du vaisseau :
    public static final int ICRAFT_X = 200;
    public static final int ICRAFT_Y = 200;

    // Spaceship Health :
    public static final int MAX_HEALTH = 1000;
    
    // Coordonnées de la balle :
    public static final int BALL_X = 100;
    public static final int BALL_Y = 200;
    
    // Temps d'apparition des bonus :
    public static final int BONUS_SPAWNRATE = 5000;
    
    // Speed and Rotation :
    public static final int rotationRate = 8;
    public static final float MAX_SPEED = 10, BASE_SPEED = 4;

    // Ennemis :
    public static final int RANGE = 100;
    public static final int SHOOT_RATE = 1000;

    // Missiles :
    public static final int MAX_MISSILE_SHOT = 99;

    // Fuel :
    public static final int BASE_FUEL = 5000;
    public static final int CONSUME_SPEED = 1000;
    public static final int CONSUME_RATE = 100;

    // Damage tick :
    public static final long COOLDOWN = 1000;
    
    // Commandes :
    public static final CustomKeys CUSTOM_KEYS = new CustomKeys();
    public static boolean WASD_MODE = false;

    public static boolean isMenu = true;
    
}