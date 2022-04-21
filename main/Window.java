/**
 * The Window class is the main class of the game. It contains the main menu, the game panel, and the
 * settings panel
 */
package main;


import game.GameView;
//import menu.Menu;
import menu.Menu;
import menu.Settings;

import javax.swing.*;
import java.awt.*;



public final class Window extends JFrame {
    public final static GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public final static GraphicsDevice device = env.getScreenDevices()[0];
    public static final Rectangle RECTANGLE = device.getDefaultConfiguration().getBounds();
    public static final int MAX_WIDTH = RECTANGLE.width;
    public static final int MAX_HEIGHT = RECTANGLE.height;
    

    public JPanel comp;
    public Menu menu;

    public Window(JPanel comp) {
        super();
        this.comp = comp;

        getContentPane().add(BorderLayout.CENTER,comp);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }

/**
 * Dispose the current frame and set the content pane to the menu
 */
    public void launchMenu() {
        this.dispose();
        getContentPane().removeAll();
        this.setContentPane(menu);
        pack();
        setTitle("Xpilot");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.playMenuMusic();
        this.setVisible(true);
    }

    public void launchGame(){
        dispose();
        getContentPane().removeAll();
        getContentPane().add(BorderLayout.CENTER,comp);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }
/**
 * This function is called when the user clicks the settings button. It removes all the other panels
 * from the window and replaces them with a new settings panel
 */


/**
 * This function launches the game
 */

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
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }

    public void setFullScreen(JPanel comp) {
        dispose();
        getContentPane().removeAll();
        getContentPane().add(BorderLayout.CENTER,comp);
        device.setFullScreenWindow(this);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }


}