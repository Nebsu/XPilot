package main;

import menu.MenuPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.EventQueue;

public class Window extends JFrame {

    public Window(boolean isMenu) {
        if (isMenu) {
            this.setVisible(false);
            this.dispose();
            // this.setContentPane(new JPanel());
            MenuPanel menu = Constants.MENU;
            add(menu);
            setTitle("Xpilot");
            setResizable(false);
            pack();
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else {
            Constants.WINDOW.setVisible(false);
            Constants.WINDOW.dispose();
            this.setContentPane(new JPanel());
            Board game = Constants.GAME;
            add(game.minimap);
            add(game);
            setResizable(false);
            pack();
            setTitle("Xpilot");
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            try {
                game.playMusic();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Constants.WINDOW = new Window(true);
            Constants.WINDOW.setVisible(true);
        });
    }

}