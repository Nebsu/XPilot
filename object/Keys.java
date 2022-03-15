package object;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.Board;

public class Keys implements KeyListener{
    Board board;
    public Keys(Board b){
        this.board = b;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE){
            board.fire();
        }
        if (key == KeyEvent.VK_UP){
            // Constants.timer.start();
            if(board.spaceship.timerStartFlag){board.spaceship.timerStartFlag = false;}
            if(board.spaceship.moveFlag == false)board.spaceship.moveTime = System.currentTimeMillis();
            board.spaceship.canDecelerate = false;
            board.spaceship.moveFlag = true;
            board.spaceship.canAccelerate = true;
        }
        if (key == KeyEvent.VK_LEFT) {
            if(board.spaceship.timerStartFlag){board.spaceship.timerStartFlag = false;}
            board.spaceship.leftRotationFlag = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            if(board.spaceship.timerStartFlag){board.spaceship.timerStartFlag = false;}
            board.spaceship.rightRotationFlag = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT) {
            board.spaceship.rightRotationFlag = false;
        }
        if (key == KeyEvent.VK_LEFT) {
            board.spaceship.leftRotationFlag = false;
        }
        if (key == KeyEvent.VK_UP) {
            board.spaceship.canDecelerate = true;
            board.spaceship.canAccelerate = false;
            board.spaceship.moveFlag = false;
        }
    }
    
}
