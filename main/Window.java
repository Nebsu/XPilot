/**
 * The Window class is the main class of the game. It contains the main menu, the game panel, and the
 * settings panel
 */

package main;

import menu.Menu;

import java.awt.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public final class Window extends JFrame {

    private Menu menu;

    public Menu getMenu() {return menu;}

    public Window() {
        super();
        this.setTitle("Xpilot");
        setSize(new Dimension(Global.W_WIDTH(), Global.W_HEIGHT()));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.launchMenu(true);
    }

    /**
     * Dispose the current frame and set the content pane to the menu
     * @throws IOException
     * @throws LineUnavailableException
     */
    public final void launchMenu(boolean launch) {
        Global.changeMenuState(true);
        this.setResizable(false);
        getContentPane().removeAll();
        this.menu = new Menu();
        getContentPane().add(menu);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        if (launch) {
            try {
                Constants.MENU_MUSIC.playMusic();
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
    public final void settingsPanel() {
        dispose();
        getContentPane().removeAll();
        getContentPane().add(menu.getSettingsPanel());
        pack();
        setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public final void helpPanel() {
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
    public final void launchGame() throws LineUnavailableException, IOException {
        Constants.MENU_MUSIC.stopMusic();
        Global.changeMenuState(false);
        dispose();
        getContentPane().removeAll();
        getContentPane().add(BorderLayout.CENTER, Global.MAINGAME().getView());
        if (Global.FULLSCREEN())
            Constants.DEVICE.setFullScreenWindow(this);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        repaint();
        Constants.GAME_MUSIC.playMusic();
    }

    public final void setDimensionsToFullScreen() {
        Global.setW_HEIGHT(Constants.MAX_HEIGHT);
        Global.setW_WIDTH(Constants.MAX_WIDTH);
        Global.setR_WIDTH(Constants.MAX_WIDTH / 800);
        Global.setR_HEIGHT(Constants.MAX_HEIGHT / 600);
    }

    public final void setDimensionsToSmallScreen() {
        Global.setW_HEIGHT(600);
        Global.setW_WIDTH(800);
    }

    public final void setSmallScreen(JPanel comp) {
        dispose();
        getContentPane().removeAll();
        getContentPane().add(BorderLayout.CENTER, comp);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        comp.repaint();
    }

    public final void setFullScreen(JPanel comp) {
        dispose();
        getContentPane().removeAll();
        getContentPane().add(BorderLayout.CENTER, comp);
        this.setUndecorated(true);
        Constants.DEVICE.setFullScreenWindow(this);
        pack();
        setVisible(true);
        comp.repaint();
    }

}