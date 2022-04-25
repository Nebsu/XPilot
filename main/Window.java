/**
 * The Window class is the main class of the game. It contains the main menu, the game panel, and the
 * settings panel
 */
package main;

import menu.Menu;
import game.GameView;

import java.awt.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public final class Window extends JFrame {

    public final static GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public final static GraphicsDevice device = env.getScreenDevices()[0];
    public static final Rectangle RECTANGLE = device.getDefaultConfiguration().getBounds();
    public static final int MAX_WIDTH = RECTANGLE.width;
    public static final int MAX_HEIGHT = RECTANGLE.height;
    public JPanel comp; // game
    public Menu menu; // menu

    public Window(JPanel comp, Menu menu) {
        super();
        this.comp = comp;
        this.menu = menu;
        this.setTitle("Xpilot");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.launchMenu(true);
    }
        
/**
 * Dispose the current frame and set the content pane to the menu
 */
    public final void launchMenu(boolean launch) {
        Constants.isMenu = true;
        this.setResizable(false);
        if (launch) this.dispose();
        this.setContentPane(menu);
        this.pack();
        this.setLocationRelativeTo(null);
        if (launch) {
            this.setVisible(true);
            menu.playMenuMusic();
        }
    }

    /**
 * This function is called when the user clicks the settings button. It removes all the other panels
 * from the window and replaces them with a new settings panel
 */

    public void settingsPanel() {
        this.setContentPane(menu.getSettingsPanel());
        pack();
        setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void helpPanel() {
        this.setContentPane(menu.getHelpPanel());
        pack();
        setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
 * This function launches the game
 */

    public void launchGame() throws IOException, LineUnavailableException {
        Constants.isMenu = false;
        menu.stopMenuMusic();
        setResizable(true);
        dispose();
        setContentPane(new JPanel());
        getContentPane().add(BorderLayout.CENTER,comp);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        repaint();
        ((GameView) comp).playGameMusic();
    }

    public void setDimensionsToFullScreen() {
        Constants.B_HEIGHT = MAX_HEIGHT;
        Constants.B_WIDTH = MAX_WIDTH;
    }

    public void setDimensionsToSmallScreen() {
        Constants.B_HEIGHT = 600;
        Constants.B_WIDTH = 800;
    }

    public void setSmallScreen(JPanel comp) {
        dispose();
        getContentPane().removeAll();
        setLayout(new BorderLayout());
        getContentPane().add(BorderLayout.CENTER,comp);
        comp.setPreferredSize(new Dimension(Constants.B_WIDTH , Constants.B_HEIGHT));
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        repaint();
    }

    public void setFullScreen(JPanel comp) {
        dispose();
        getContentPane().removeAll();
        getContentPane().add(BorderLayout.CENTER,comp);
        device.setFullScreenWindow(this);
        pack();
        setVisible(true);
        repaint();
    }

}