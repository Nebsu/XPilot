package main;

import game.GameLoop;

public final class Global {

    // Main game :
    private static GameLoop MAINGAME;
    public static GameLoop MAINGAME() {return MAINGAME;}
    public static void initMainGame(GameLoop g) {MAINGAME = g;}

    // Window dimensions :
    private static int W_WIDTH = 800;
    private static int W_HEIGHT = 600;
    public static final int W_WIDTH() {return W_WIDTH;}
    public static final void setW_WIDTH(int w) {W_WIDTH = w;}
    public static final int W_HEIGHT() {return W_HEIGHT;}
    public static final void setW_HEIGHT(int w) {W_HEIGHT = w;}

    // Screen ratio :
    private static int R_WIDTH = 1;
    private static int R_HEIGHT = 1;
    public static final int R_WIDTH() {return R_WIDTH;}
    public static final void setR_WIDTH(int r) {R_WIDTH = r;}
    public static final int R_HEIGHT() {return R_HEIGHT;}
    public static final void setR_HEIGHT(int r) {R_HEIGHT = r;}

    // Fullscreen :
    private static boolean FULLSCREEN = false;
    private static boolean ACT_FULLSCREEN = false;
    public static final boolean FULLSCREEN() {return FULLSCREEN;}
    public static final void setFULLSCREEN(boolean b) {FULLSCREEN = b;}
    public static final boolean ACT_FULLSCREEN() {return ACT_FULLSCREEN;}
    public static final void setACT_FULLSCREEN(boolean b) {ACT_FULLSCREEN = b;}

    // Menu State :
    private static boolean IS_MENU = true;
    public static final boolean IS_MENU() {return IS_MENU;}
    public static final void changeMenuState(boolean b) {IS_MENU = b;} 

    // Volumes :
    private static int MUSIC_VOLUME = 100;
    private static int SFX_VOLUME = 100;
    public static final int MUSIC_VOLUME() {return MUSIC_VOLUME;}
    public static final void setMUSIC_VOLUME(int v) {MUSIC_VOLUME = v;}
    public static final int SFX_VOLUME() {return SFX_VOLUME;}
    public static final void setSFX_VOLUME(int v) {SFX_VOLUME = v;}

    // WASD command mode :
    private static boolean WASD_MODE = false;
    public static final boolean WASD_MODE() {return WASD_MODE;}
    public static final void setWASD_MODE(boolean b) {WASD_MODE = b;}
    
}