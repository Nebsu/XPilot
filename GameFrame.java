package Xpilot;


import javax.swing.JFrame;


public class GameFrame extends JFrame{
    public GameFrame(){
        this.setTitle("Xpilot");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        GamePanel panel=new GamePanel();
        this.add(panel);
        this.pack();
        this.addKeyListener(panel);
    }
}