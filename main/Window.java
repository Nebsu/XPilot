/**
 * The Window class is the main class of the game. It contains the main menu, the game view, and the
 * settings panel
 */
package main;

import menu.*;
import game.GameView;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;


public final class Window extends JFrame {

    public static final Menu MENU = new Menu();
    public static final Window WINDOW = new Window();
    private static GameView MAINGAME;

    public static final GameView getMainGame() {return MAINGAME;}

    public Window() {
        this.launchMenu();
    }

/**
 * Dispose the current frame and set the content pane to the menu
 */
    public final void launchMenu() {
        this.dispose();
        this.setContentPane(MENU);
        // add(MENU);
        setResizable(false);
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
        this.setContentPane(new JPanel());
        MAINGAME = new GameView();
        add(MAINGAME.getRadar());
        add(MAINGAME);
        setResizable(false);
        pack();
        setTitle("Xpilot");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MAINGAME.playGameMusic();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        
    }

}