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
            if(spaceship.moveFlag == false)spaceship.moveTime = System.currentTimeMillis();
            spaceship.canDecelerate = false;
            spaceship.moveFlag = true;
            spaceship.canAccelerate = true;
        }
        if (key == KeyEvent.VK_LEFT) {
            if(spaceship.timerStartFlag){spaceship.timerStartFlag = false; Constants.timer.start();}
            spaceship.leftRotationFlag = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            if(spaceship.timerStartFlag){spaceship.timerStartFlag = false; Constants.timer.start();}
            spaceship.rightRotationFlag = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT) {
            spaceship.rightRotationFlag = false;
        }
        if (key == KeyEvent.VK_LEFT) {
            spaceship.leftRotationFlag = false;
        }
        if (key == KeyEvent.VK_UP) {
            spaceship.canDecelerate = true;
            spaceship.canAccelerate = false;
            spaceship.moveFlag = false;
        }
    }
    
}
