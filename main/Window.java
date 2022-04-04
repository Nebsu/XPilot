package main;

import menu.*;
import game.GameView;

import java.awt.EventQueue;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public final class Window extends JFrame {

    public static final Menu MENU = new Menu();
    public static final Window WINDOW = new Window();
    private static GameView MAINGAME = null;

    public static final GameView getMainGame() {return MAINGAME;}

    public Window() {
        // Frame settings :
        this.setTitle("Xpilot");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        // Menu is launched first :
        this.launchMenu();
    }

    public final void launchMenu() {
        if (MAINGAME!=null) MAINGAME.stopGameMusic();
        this.dispose();
        this.setContentPane(MENU.getMenuPanel());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        MENU.playMenuMusic();
    }

    // Démarrage du jeu principal lorsque "Start" est sélectionné dans le menu :
    public final void launchGame() throws IOException, LineUnavailableException {
        MENU.stopMenuMusic();
        this.dispose();
        MAINGAME = new GameView();
        this.setContentPane(new JPanel());
        this.add(MAINGAME.getRadar());
        this.add(MAINGAME);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        MAINGAME.playGameMusic();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {});
    }

}