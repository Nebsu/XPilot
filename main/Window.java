/**
 * The Window class is the main class of the game. It contains the main menu, the game panel, and the
 * settings panel
 */
package main;

import game.GameLoop;
import menu.Menu;

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

    private Menu menu;

    public Menu getMenu() {return menu;}

    public Window() {
        super();
        this.setTitle("Xpilot");
        setSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.launchMenu(true);
    }

    /**
     * Dispose the current frame and set the content pane to the menu
     * @throws IOException
     * @throws LineUnavailableException
     */
    public final void launchMenu(boolean launch) {
        Constants.isMenu = true;
        this.setResizable(false);
        getContentPane().removeAll();
        this.menu = new Menu();
        getContentPane().add(menu);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        if (launch) {
            try {
                Menu.menuMusic.playMusic();
            } catch (LineUnavailableException | IOException e) {
                e.printStackTrace();
                System.out.println(e);
				System.exit(1);
            }
        }
        repaint();
    }

    /**
     * This function is called when the user clicks the settings button. It removes all the other panels
     * from the window and replaces them with a new settings panel
     */

    public void settingsPanel() {
        dispose();
        getContentPane().removeAll();
        getContentPane().add(menu.getSettingsPanel());
        pack();
        setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void helpPanel() {
        dispose();
        getContentPane().removeAll();
        getContentPane().add(menu.getHelpPanel());
        pack();
        setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * This function launches the game
     */

    public void launchGame() throws LineUnavailableException, IOException {
        Menu.menuMusic.stopMusic();
        Constants.isMenu = false;
        dispose();
        getContentPane().removeAll();
        getContentPane().add(BorderLayout.CENTER, GameLoop.view);
        if (GameLoop.fullScreenMode)
            device.setFullScreenWindow(this);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        repaint();
        GameLoop.gameMusic.playMusic();
    }

    public void setDimensionsToFullScreen() {
        Constants.B_HEIGHT = MAX_HEIGHT;
        Constants.B_WIDTH = MAX_WIDTH;
        Constants.R_WIDTH = MAX_WIDTH / 800;
        Constants.R_HEIGHT = MAX_HEIGHT / 600;
    }

    public void setDimensionsToSmallScreen() {
        Constants.B_HEIGHT = 600;
        Constants.B_WIDTH = 800;
    }

    public void setSmallScreen(JPanel comp) {
        dispose();
        getContentPane().removeAll();
        getContentPane().add(BorderLayout.CENTER, comp);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        comp.repaint();
    }

    public void setFullScreen(JPanel comp) {
        dispose();
        getContentPane().removeAll();
        getContentPane().add(BorderLayout.CENTER, comp);
        this.setUndecorated(true);
        device.setFullScreenWindow(this);
        pack();
        setVisible(true);
        comp.repaint();
    }

}