package main;

import menu.Menu;

import javax.swing.JFrame;
import javax.swing.JPanel;

public final class Window extends JFrame {

    public static final Menu MENU = new Menu();
    public static final Window WINDOW = new Window();
    private Game MAINGAME;

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

    // Démarrage du jeu principal lorsque "Start" est sélectionné dans le menu :
    public final void launchGame() {
        this.dispose();
        this.setContentPane(new JPanel());
        this.MAINGAME = new Game();
        add(MAINGAME.getMinimap());
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