package main;

import game.GameLoop;

public final class Global {

    // JEU PRINCIPAL :
    private static GameLoop MAINGAME;
    public static GameLoop MAINGAME() {return MAINGAME;}
    public static void initMainGame(GameLoop g) {MAINGAME = g;}

    // Dimensions de la fenêtre :
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

    // Commande en WASD :
    private static boolean WASD_MODE = false;
    public static final boolean WASD_MODE() {return WASD_MODE;}
    public static final void setWASD_MODE(boolean b) {WASD_MODE = b;}

    // Menu State :
    private static boolean isMenu = true;
    public static final boolean isMenu() {return isMenu;}
    public static final void changeMenuState(boolean b) {isMenu = b;} 
    
}