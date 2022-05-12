/**
 * The Window class is the main Jframe of the game. 
 * It switches between the main menu view, the sub menus view and the main game view
 */

package src.main;

import src.menu.Menu;

import java.awt.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public final class Window extends JFrame {

    private Menu menu;

    public Window() {
        super();
        this.setTitle("Xpilot");
        setSize(new Dimension(Globals.W_WIDTH(), Globals.W_HEIGHT()));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.launchMenu(true, true);	
    }

    public final Menu getMenu() {return menu;}

    /**
     * Dispose the current frame and set the content pane to the menu
     * @throws IOException
     * @throws LineUnavailableException
     */
    public final void launchMenu(boolean music, boolean beginning) {
        Globals.changeMenuState(true);
        this.setResizable(false);
        getContentPane().removeAll();
        try {
            this.menu = new Menu(beginning);
        } catch (IOException e1) {
            e1.printStackTrace();
            System.out.println(e1);
            System.exit(1);
        }
        getContentPane().add(menu);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        if (music) {
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
     * This function is called when the user clicks the settings button. 
     * It removes all the other panels from the window and replaces them with the settings panel.
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
     * This function is called when the user clicks the start button. 
     * It removes all the other panels from the window and replaces them with the settings game panel.
     */
    public final void launchGame() throws LineUnavailableException, IOException {
        Constants.MENU_MUSIC.stopMusic();
        Globals.changeMenuState(false);
        dispose();
        getContentPane().removeAll();
        getContentPane().add(BorderLayout.CENTER, Globals.MAINGAME().getView());
        if (Globals.FULLSCREEN())
            Constants.DEVICE.setFullScreenWindow(this);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        repaint();
        Constants.GAME_MUSIC.playMusic();
    }

    // Fullscreen functions :
    public final void setDimensionsToFullScreen() {
        Globals.setW_HEIGHT(Constants.MAX_HEIGHT);
        Globals.setW_WIDTH(Constants.MAX_WIDTH);
        Globals.setR_WIDTH(Constants.MAX_WIDTH / 800);
        Globals.setR_HEIGHT(Constants.MAX_HEIGHT / 600);
    }

    public final void setDimensionsToSmallScreen() {
        Globals.setW_HEIGHT(600);
        Globals.setW_WIDTH(800);
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