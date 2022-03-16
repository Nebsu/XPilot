package main;

import menu.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.EventQueue;

public class Game extends JFrame {

    public static Game GAME;

    public Game(boolean isMenu) {
        if (isMenu) {
            this.setVisible(false);
            this.dispose();
            this.setContentPane(new JPanel());
            MenuPanel menu = new MenuPanel(this);
            add(menu);
            setTitle("Xpilot");
            setResizable(false);
            pack();
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else {
            GAME.setVisible(false);
            GAME.dispose();
            this.setContentPane(new JPanel());
            Board game = new Board();
            add(game.minimap);
            add(game);
            setResizable(false);
            pack();
            setTitle("Xpilot");
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GAME = new Game(true);
            GAME.setVisible(true);
        });
    }

}