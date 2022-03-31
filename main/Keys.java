package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import sound.SFX;


public class Keys implements KeyListener{
    Board board;
    private SFX pew;

    public Keys(Board b){
        this.board = b;
        this.pew = new SFX("ressources/pew.wav");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key==KeyEvent.VK_ESCAPE) {
            try {
                Constants.TIMER.stop();
                Constants.WINDOW.setVisible(false);
                Constants.GAME.stopMusic();
                Constants.WINDOW = new Window(true);
                Constants.WINDOW.setVisible(true);
                Constants.MENU.playMusic();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return;
        }
        if (key == Constants.SHOOT){
            Constants.TIMER.start();
            board.fire();
            try {
                pew.playSound();
                // pew.stopSound();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (key == KeyEvent.VK_UP){
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
        if (key == KeyEvent.VK_C){
            if(!board.spaceship.shield.isActive() && board.spaceship.shield.getQuantity() > 0){
                board.spaceship.shield.enable();
            }else{
                board.spaceship.shield.disable();
            }
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
