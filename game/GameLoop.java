/**
 * The GameLoop class is the main loop of the game. It is responsible for the game logic
 */
package game;

import map.*;
import object.*;

import java.util.TimerTask;

import main.Constants;
import main.Window;

import java.awt.*;

public final class GameLoop extends TimerTask {

    private GameView b;

    public GameLoop(GameView b){
        this.b=b;
    }
    
 /**
  * Game Loop
  */
    @Override
    public void run() {
        System.out.println(b.getSpaceShip().rotation);
        inGame();
        b.getRadar().repaint();
        if(!checkCollision())updateShip();
        else{
            if(b.getSpaceShip().canTakeDamage()){
                if(b.getSpaceShip().shield.isActive()){
                    b.getSpaceShip().shield.destroy();
                    b.getSpaceShip().shield.disable();
                }else{
                    b.getSpaceShip().setHealth(b.getSpaceShip().getHealth()-50);
                }
            }
        }
        updateEnemies();
        b.getSpaceShip().rotateRight();
        b.getSpaceShip().rotateLeft();
        updateMissiles();
        updateBonus();
        b.repaint();    
    }

/**
 * If the player's health or fuel is less than or equal to zero, the game is over
 */
    public final void inGame() {
        if(b.getSpaceShip().getFuel() <= 0 ||
            b.getSpaceShip().getHealth() <= 0){
            b.setInGame(false);
        }
        if (!b.isInGame()) {
            Window.getMainGame().getTimer().cancel();
        }
    }

/**
 * Check if the ship collides with an obstacle
 * 
 * @return Nothing.
 */
    public final boolean checkCollision(){
        Rectangle s=new Rectangle((int)(b.getSpaceShip().getX()+b.getSpaceShip().SPEED * Math.cos(Math.toRadians(b.getSpaceShip().rotation))),(int)(b.getSpaceShip().getY()+b.getSpaceShip().SPEED*Math.sin(Math.toRadians(b.getSpaceShip().rotation))),16,16);
        for (Obstacle obstacle : b.getMap().ListeObstacle) {
            Rectangle o=new Rectangle(obstacle.x[0],obstacle.y[0],obstacle.x[1]-obstacle.x[0],obstacle.y[2]-obstacle.y[1]);
            // Ship après le movement :
            if(s.intersects(o) && !(obstacle instanceof Goal) && !(obstacle instanceof BallHolder) && !(obstacle instanceof Bonus)){
                return true;
            }else if(s.intersects(o)&& obstacle instanceof BallHolder){
                b.getMap().ball.take();
            }else if(s.intersects(o)&& obstacle instanceof Goal && b.getMap().ball.isTaken()){
                b.setInGame(false);
            }
        }

        for (int i = 0; i < b.getMap().bonusList.size(); i++){
            Bonus bo = b.getMap().bonusList.get(i);
            Rectangle r=new Rectangle(bo.x[0],bo.y[0],bo.x[1]-bo.x[0],bo.y[2]-bo.y[1]);
            if(s.intersects(r)){
                if(b.getSpaceShip().getFuel()<b.getSpaceShip().BASE_FUEL)b.getSpaceShip().setFuel(b.getSpaceShip().getFuel()+500);
                b.getSpaceShip().shield.add();
                b.erase(bo);
                b.getMap().bonusList.remove(bo);
            }
        }
        return false;
    }

/**
 * Update the ship's position and velocity
 */
    public final void updateShip() {
        if (b.getSpaceShip().isVisible()) {
            b.getSpaceShip().move();
            b.getSpaceShip().acceleration();
            b.getSpaceShip().deceleration();
            if(b.getSpaceShip().moveFlag)b.getSpaceShip().consumeFuel();
        }
    }

/**
 * Update the position of all the missiles in the game
 */
    public final void updateMissiles() {
        for (Missile m : b.getMissiles()) {
            if (m.isVisible()) {
                m.move();
            }
        }
        //detecter le collision du missile 
        for(int i=0;i<b.getMissiles().size();i++) {
            Missile m= b.getMissiles().get(i);
            Rectangle r=m.getBounds();
            for(Obstacle ob:b.getMap().ListeObstacle){
                Rectangle o=new Rectangle(ob.x[0],ob.y[0],ob.x[1]-ob.x[0],ob.y[2]-ob.y[1]);
                if(r.intersects(o) && (m instanceof MissileNormale || m instanceof MissileDiffusion)){
                    b.getMissiles().remove(m);
                }else if(r.intersects(o) && (m instanceof Rocket)){
                    int angle;
                    if(((Rocket)m).rebounce == 3){
                        b.getMissiles().remove(m);
                    }
                    ((Rocket)m).rebounce += 1;
                    if(((Rocket)m).getdirection() < 0){
                        angle = ((Rocket)m).getdirection() + 360;
                    }else{
                        angle = ((Rocket)m).getdirection();
                    }
                    //Si le missile touche un mur sur la gauche ou la droite
                    ((Rocket)m).setDirection(180-angle);
                    //Si le missile touche un mur sur haut ou le bas
                    ((Rocket)m).setDirection(360-angle);
                }
            }
        }
    }

    public final void updateEnemies(){
        for(Enemy e : b.getMap().enemies){
            if(Math.sqrt(Math.pow(b.getSpaceShip().getX()-e.x, 2)+Math.pow(b.getSpaceShip().getY()-e.y,2))< 200 && e.canShoot()){
                b.getMissiles().add(new MissileNormale(e.x, e.y, (int)Math.toDegrees(e.getRad(b.getSpaceShip().getX(), b.getSpaceShip().getY()))));
            }
        }
    }

    public final void updateBonus(){
        if(System.currentTimeMillis() - b.getMap().lastTime > Constants.BONUS_SPAWNRATE){
            b.getMap().addBonus();
            b.drawBonus();
        }
    }

}