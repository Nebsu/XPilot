/**
 * The Window class is the main class of the game. It contains the main menu, the game view, and the
 * settings panel
 */
package main;

import game.GameView;
import menu.Menu;
import menu.SettingsPanel;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public final class Window extends JFrame {
    public final static GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public final static GraphicsDevice device = env.getScreenDevices()[0];
    public static final Rectangle RECTANGLE = device.getDefaultConfiguration().getBounds();
    public static final int MAX_WIDTH = RECTANGLE.width - 200;
    public static final int MAX_HEIGHT = RECTANGLE.height;

    public static final Menu MENU = new Menu();
    public static  Window WINDOW = new Window();
    private static GameView MAINGAME;

    public static final GameView getMainGame() {return MAINGAME;}

    public Window() {
        this.launchMenu();
    }

/**
 * Dispose the current frame and set the content pane to the menu
 */
    public void launchMenu() {
        this.dispose();
        this.setContentPane(MENU);
        // add(MENU);
        pack();
        setTitle("Xpilot");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MENU.playMenuMusic();
        this.setVisible(true);
    }

/**
 * This function is called when the user clicks the settings button. It removes all the other panels
 * from the window and replaces them with a new settings panel
 */
    public final void settingsSection() {
        removeAll();
        revalidate();
        repaint();
        this.setContentPane(new JPanel());
        add(new SettingsPanel());
    }

/**
 * This function launches the game
 */
    public final void launchGame() throws IOException, LineUnavailableException {
        this.dispose();
        MAINGAME = new GameView();
        setContentPane(new JPanel());
        getContentPane().add(MAINGAME.getRadar());
        getContentPane().add(MAINGAME);
        pack();
        setTitle("Xpilot");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MAINGAME.playGameMusic();
        this.setVisible(true);
    }
    public void setDimensionsToFullScreen() {
        Constants.B_HEIGHT = MAX_HEIGHT;
        Constants.B_WIDTH = MAX_WIDTH;
    }

    public void setDimensionsToSmallScreen() {
        Constants.B_HEIGHT = 600;
        Constants.B_WIDTH = 800;
    }

    public void setSmallScreen(GameView comp) {
        dispose();
        getContentPane().removeAll();
        getContentPane().add(comp.getRadar());
        getContentPane().add(comp);
        this.setUndecorated(false);
        this.pack();
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();


    }

    public void setFullScreen(GameView comp) {
        dispose();
        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(BorderLayout.EAST, comp.getRadar());
        getContentPane().add(BorderLayout.CENTER,comp);
        getContentPane().getComponent(1).setPreferredSize(new Dimension (Constants.B_WIDTH, Constants.B_HEIGHT));
        this.setUndecorated(true);
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Constants.B_WIDTH , Constants.B_HEIGHT );
        repaint();
    }

    public static void main(String[] args) {
        
    }

}