package main;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.EventQueue;

public class Game extends JFrame {
    private JPanel principal=new JPanel();
    public Game() {
        initUI();
    }
    
    private void initUI() {
        this.setContentPane(principal);
        Board game = new Board();
        add(game.minimap);
        add(game);
        setResizable(false);
        pack();
        setTitle("PEWPEW");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Game g = new Game();
            g.setVisible(true);
        });
    }

}