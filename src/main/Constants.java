/** The Constants class contains all the global constants variables used in the game */

package src.main;

import src.sound.Music;

import java.awt.*;

import src.game.CustomKeys;

public final class Constants {

    // Fullscreen variables :
    public final static GraphicsEnvironment ENV = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public final static GraphicsDevice DEVICE = ENV.getScreenDevices()[0];
    public static final Rectangle RECTANGLE = DEVICE.getDefaultConfiguration().getBounds();
    public static final int MAX_WIDTH = RECTANGLE.width;
    public static final int MAX_HEIGHT = RECTANGLE.height;

    // Music :
    public static final Music GAME_MUSIC = new Music("ressources/audio/gamemusic.wav");
    public static final Music MENU_MUSIC = new Music("ressources/audio/menuMusic.wav");

    // Menu title properties :
    public static final int TITLE_WIDTH = 550;
	public static final int TITLE_HEIGHT = 115;

    // Buttons properties :
    public static final int BUTTON_WIDTH = 120;
	public static final int BUTTON_HEIGHT = 60;
    public static final int WASD_B_WIDTH = 150;
	public static final int WASD_B_HEIGHT = 50;

    // Commands :
    public static final CustomKeys CUSTOM_KEYS = new CustomKeys();

    // Spaceship position :
    public static final int ICRAFT_X = 200;
    public static final int ICRAFT_Y = 200;

    // Spaceship Health :
    public static final int MAX_HEALTH = 1000;
    
    // Ball position :
    public static final int BALL_X = 100;
    public static final int BALL_Y = 200;
    
    // Bonus spawnrate :
    public static final int BONUS_SPAWNRATE = 5000;
    
    // Speed and Rotation :
    public static final int ROTATION_RATE = 6;
    public static final float MAX_SPEED = 10, BASE_SPEED = 4;

    // Ennemies :
    public static final int RANGE = 350;
    public static final int SHOOT_RATE = 1000;
    public static final int DAMAGE = 50;

    // Missiles :
    public static final int MAX_MISSILE_SHOT = 50;

    // Fuel :
    public static final int BASE_FUEL = 5000;
    public static final int CONSUME_SPEED = 1000;
    public static final int CONSUME_RATE = 100;

    // Damage tick :
    public static final long COOLDOWN = 1000;
    
}