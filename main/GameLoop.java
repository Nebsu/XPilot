package main;

import java.util.TimerTask;
import java.awt.*;

import map.*;
import object.*;
/* Class timer pour refresh le board, on utilise la class timertask pour exécuter les événements
*/ 
public class GameLoop extends TimerTask{
    public Board b;

    public GameLoop(Board b){
        this.b=b;
    }
    
//methode principale run pour executer
    @Override
    public void run() {
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
        b.getSpaceShip().rotateRight();
        b.getSpaceShip().rotateLeft();
        updateMissiles();
        updateBonus();
        b.repaint();    
    }
    // pour arreter le timer
    public void inGame() {
        if(b.getSpaceShip().getFuel() <= 0 ||
            b.getSpaceShip().getHealth() <= 0){
            b.ingame = false;
        }
        if (!b.ingame) {
            Constants.TIMER.cancel();
        }
    }
    //detecter le collision pour le vaisseau
    public boolean checkCollision(){
        Rectangle s=new Rectangle((int)(b.getSpaceShip().getX()+b.getSpaceShip().SPEED * Math.cos(Math.toRadians(b.getSpaceShip().rotation))),(int)(b.getSpaceShip().getY()+b.getSpaceShip().SPEED*Math.sin(Math.toRadians(b.getSpaceShip().rotation))),16,16);
        for (Obstacle obstacle : b.map.ListeObstacle) {
            Rectangle o=new Rectangle(obstacle.x[0],obstacle.y[0],obstacle.x[1]-obstacle.x[0],obstacle.y[2]-obstacle.y[1]);
            //ship apres le movement
            if(s.intersects(o) && !(obstacle instanceof Goal) && !(obstacle instanceof Ball) && !(obstacle instanceof Bonus)){
                return true;
            }else if(s.intersects(o)&& obstacle instanceof Ball){
                b.map.ball.take();
            }else if(s.intersects(o)&& obstacle instanceof Goal && b.map.ball.isTaken()){
                b.ingame = false;
            }
        }

        for (int i = 0; i < b.map.bonusList.size(); i++){
            Bonus bo = b.map.bonusList.get(i);
            Rectangle r=new Rectangle(bo.x[0],bo.y[0],bo.x[1]-bo.x[0],bo.y[2]-bo.y[1]);
            if(s.intersects(r)){
                if(b.getSpaceShip().getFuel()<b.getSpaceShip().BASE_FUEL)b.getSpaceShip().setFuel(b.getSpaceShip().getFuel()+500);
                b.getSpaceShip().shield.add();
                b.erase(bo);
                b.map.bonusList.remove(bo);
            }
        }
        return false;
    }
    //mise a jour du vaisseau
    public void updateShip() {
        if (b.getSpaceShip().isVisible()) {
            b.getSpaceShip().move();
            b.getSpaceShip().acceleration();
            b.getSpaceShip().deceleration();
            if(b.getSpaceShip().moveFlag)b.getSpaceShip().consumeFuel();
        }
    }
    //mise a jour du missile et detection du collision
    public void updateMissiles() {
        for (Missile m : b.getMissiles()) {
            if (m.isVisible()) {
                m.move();
            }
        }
        //detecter le collision du missile 
        for(int i=0;i<b.getMissiles().size();i++) {
            Missile m= b.getMissiles().get(i);
            Rectangle r=m.getBounds();
            for(Obstacle ob:b.map.ListeObstacle){
                Rectangle o=new Rectangle(ob.x[0],ob.y[0],ob.x[1]-ob.x[0],ob.y[2]-ob.y[1]);
                if(r.intersects(o)){
                    b.getMissiles().remove(m);
                }
            }
        }
    }
    //mise a jour du bonus
    public void updateBonus(){
        if(System.currentTimeMillis() - b.map.lastTime > Constants.BONUS_SPAWNRATE){
            b.map.addBonus();
            b.drawBonus();
        }
    }

}