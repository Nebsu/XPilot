package spaceship;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.Constants;

public class Keys implements KeyListener{
    SpaceShip spaceship;
    public Keys(SpaceShip s){
        this.spaceship = s;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE){
            Constants.timer.start();
            spaceship.fire();
        }
        if (key == KeyEvent.VK_UP){
            Constants.timer.start();
            if(spaceship.timerStartFlag){spaceship.timerStartFlag = false; Constants.timer.start();}
            if(Constants.moveFlag == false)spaceship.moveTime = System.currentTimeMillis();
            Constants.moveFlag = true;
            Constants.canAccelerate = true;
        }
        if (key == KeyEvent.VK_LEFT) {
            if(spaceship.timerStartFlag){spaceship.timerStartFlag = false; Constants.timer.start();}
            Constants.leftRotationFlag = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            if(spaceship.timerStartFlag){spaceship.timerStartFlag = false; Constants.timer.start();}
            Constants.rightRotationFlag = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT) {
            Constants.rightRotationFlag = false;
        }
        if (key == KeyEvent.VK_LEFT) {
            Constants.leftRotationFlag = false;
        }
        if (key == KeyEvent.VK_UP) {
            Constants.canDecelerate = true;
            Constants.canAccelerate = false;
            Constants.moveFlag = false;
        }
    }
    
}
