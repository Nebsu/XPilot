package main;

import menu.*;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public final class Window extends JFrame {

    public static final Menu MENU = new Menu();
    public static final Window WINDOW = new Window();
    private Board MAINGAME;

    public Window() {
        this.launchMenu();
    }

    public final void launchMenu() {
        this.dispose();
        this.setContentPane(new JPanel());
        add(MENU);
        setResizable(false);
        pack();
        setTitle("Xpilot");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MENU.playMenuMusic();
        this.setVisible(true);
    }

    public final void settingsSection() {
        removeAll();
        revalidate();
        repaint();
        this.setContentPane(new JPanel());
        add(new SettingsPanel());
    }

    // Démarrage du jeu principal lorsque "Start" est sélectionné dans le menu :
    public final void launchGame() throws IOException, LineUnavailableException {
        this.dispose();
        this.setContentPane(new JPanel());
        this.MAINGAME = new Board();
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