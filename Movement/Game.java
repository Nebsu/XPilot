import java.awt.EventQueue;
import javax.swing.JFrame;

public class Game extends JFrame {

    public Game() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Board());
        
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