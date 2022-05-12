/**
 * The GameLoop class is the main loop of the game. 
 * It is responsible for the game logic.
 */

package game;

import map.*;
import object.*;
import main.Constants;
import main.Global;
import main.Window;

import java.util.ArrayList;
import java.awt.*;

public final class GameLoop implements Game, Runnable {

    // Frame :
    private Window win;

    // View :
    private GameView view;

    // Game Objects :
    private SpaceShip ship;
    private Map map;
    private ArrayList<Missile> missile;

    // Inputs :
    private GameKeys keys;

    // Gameloop :
    private Game game;
    private boolean gameStarted = false;
    private final double updateRate = 1.0d / 60.0d;
    private int fps, ups;
    private boolean running;
    private long nextStatTime;

    private int level = 1;

    public GameLoop()  {
        map = new Map(Levels.LEVEL1.pathname);
        ship = new SpaceShip(map.getShipX(), map.getShipY());
        game = this;
        missile = new ArrayList<>();
        view = new GameView(game);
        keys = new GameKeys();
        win = new Window();
        win.addKeyListener(keys);
        Global.initMainGame(this);
    }

    // Getters :
    @Override public Window getWindow() {return win;}
    @Override public GameView getView() {return view;}
    @Override public Game getGame() {return game;}
    @Override public void gameStart() {gameStarted = true;}
    @Override public SpaceShip getShip() {return ship;}
    @Override public Map getMap() {return map;}
    @Override public ArrayList<Missile> getMissiles() {return this.missile;}
    @Override public GameKeys getKeys() {return keys;}
    @Override public boolean hasGameStarted() {return gameStarted;}
    @Override public GameLoop getLoop() {return this;}

    /** Game Loop */
    @Override
    public void run() {
        running = true;
        double accumulator = 0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;
        while (running) {
            win.requestFocus();
            if (!Global.IS_MENU()) {
                currentTime = System.currentTimeMillis();
                double lastRenderTimeSeconds = (currentTime - lastUpdate) / 1000d;
                accumulator += lastRenderTimeSeconds;
                lastUpdate = currentTime;
                if (accumulator >= updateRate) {
                    while (accumulator > updateRate) {
                        game.update();
                        accumulator -= updateRate;
                    }
                    repaint();
                }
            }
            printStat();
        }
    }

    private final void printStat() {
        if (System.currentTimeMillis() > nextStatTime) {
            System.out.println(String.format("FPS: %d, UPS: %d", fps, ups));
            fps = 0;
            ups = 0;
            nextStatTime = System.currentTimeMillis() + 1000;
        }
    }

    private final void repaint() {
        fps++;
        view.repaint();
    }

    public void switchLevel(){
        this.level++;
        switch(this.level){
            case 1 : this.map = new Map(Levels.LEVEL1.pathname); this.ship = new SpaceShip(map.getShipX(), map.getShipY());break;
            case 2 : this.map = new Map(Levels.LEVEL2.pathname); this.ship = new SpaceShip(map.getShipX(), map.getShipY());break;
            case 3 : this.map = new Map(Levels.LEVEL3.pathname); this.ship = new SpaceShip(map.getShipX(), map.getShipY());break;
            case 4 : view.setInGame(false); break;
        }
    }

    public void resetLevel(){
        this.level = 0;
    }

    /**
     * If the player's health or fuel is less than or equal to zero, the game is over
     */
    public final void inGame() {
        if (ship.getFuel() <= 0 ||
                ship.getHealth() <= 0) {
            view.setInGame(false);
        }
    }

    /**
     * Check if the ship collides with an obstacle
     */
    public final boolean checkCollision() {
        Rectangle s = new Rectangle((int) (ship.getX() + ship.getSpeed() * Math.cos(Math.toRadians(ship.getRotation()))), 
                      (int) (ship.getY() + ship.getSpeed() * Math.sin(Math.toRadians(ship.getRotation()))), 16, 16);
        for (Obstacle obstacle : map.getObstacles()) {
            Rectangle o = new Rectangle(obstacle.getX()[0], obstacle.getY()[0], obstacle.getX()[1] - obstacle.getX()[0], 
                                        obstacle.getY()[2] - obstacle.getY()[1]);
            // Ship after move :
            if (s.intersects(o) && !(obstacle instanceof Goal) && 
                !(obstacle instanceof BallHolder) && !(obstacle instanceof Bonus)) {
                return true;
            } else if (s.intersects(o) && obstacle instanceof BallHolder) {
                map.getBall().take();
            } else if (s.intersects(o) && obstacle instanceof Goal && map.getBall().isTaken()) {
                switchLevel();
            }
        }
        for (BreakableWall bw : map.getListeBreakableWall()){
            Rectangle o = new Rectangle(bw.getX()[0], bw.getY()[0], bw.getX()[1] - bw.getX()[0], 
                                        bw.getY()[2] - bw.getY()[1]);
            if (s.intersects(o)){
                if(bw.getState()>0){
                    return true;
                }
            }
        }
        for (int i = 0; i < getMap().getBonuses().size(); i++) {
            Bonus bo = getMap().getBonuses().get(i);
            Rectangle r = new Rectangle(bo.getX()[0], bo.getY()[0], bo.getX()[1] - bo.getX()[0], bo.getY()[2] - bo.getY()[1]);
            if (s.intersects(r)) {
                if (getShip().getFuel() < Constants.BASE_FUEL) {
                    if (getShip().getFuel() > Constants.BASE_FUEL - 500) {
                        getShip().setFuel(getShip().getFuel() + Constants.BASE_FUEL - getShip().getFuel());
                    } else {
                        getShip().setFuel(getShip().getFuel() + 500);
                    }
                }
                view.erase(bo);
                getMap().getBonuses().remove(bo);
            }
        }
        return false;
    }

    /** Update the ship's position */
    public final void updateShip() {
        if (getShip().isVisible()) {
            getShip().move();
            getShip().acceleration();
            getShip().deceleration();
            if (getShip().getMoveFlag()) getShip().consumeFuel();
        }
    }

    /** Update the position of all the missiles in the game */
    public final void updateMissiles() {
        for (Missile m : missile) {
            if (m.isVisible()) {
                m.move();
            }
        }
        // Missile collision detection :
        for (int i = 0; i < getMissiles().size(); i++) {
            Missile m = getMissiles().get(i);
            Rectangle missiles = m.getBounds();
            Rectangle ship = getShip().getBounds();
            if (missiles.intersects(ship)) {
                if (getMissiles().get(i).getShooter() == 2) {
                    getMissiles().remove(m);
                    if (getShip().getShield().isActive()) {
                        getShip().getShield().destroy();
                        getShip().getShield().disable();
                    } else {
                        getShip().setHealth(getShip().getHealth() - 100);
                    }
                }
            }
            for (int j = 0; j < map.getObstacles().size(); j++) {
                Obstacle ob = map.getObstacles().get(j);
                Rectangle wall = new Rectangle(ob.getX()[0], ob.getY()[0], ob.getX()[1] - ob.getX()[0], ob.getY()[2] - ob.getY()[1]);
                if (missiles.intersects(wall) && (m instanceof MissileNormal || m instanceof MissileDiffusion)) {
                    getMissiles().remove(m);
                } else if (missiles.intersects(wall) && ob instanceof Obstacle && (m instanceof Rocket)) {
                    int angle;
                    if (((Rocket) m).rebounce == 3) {
                        getMissiles().remove(m);
                    }
                    ((Rocket) m).rebounce += 1;
                    if (((Rocket) m).getDirection() < 0) {
                        angle = ((Rocket) m).getDirection() + 360;
                    } else {
                        angle = ((Rocket) m).getDirection();
                    }
                    if (angle > 270) {
                        // ((Rocket)m).setDirection(180-angle);
                        ((Rocket) m).setDirection(360 - angle);
                    } else if (angle < 90) {
                        // ((Rocket)m).setDirection(360-angle);
                        ((Rocket) m).setDirection(180 - angle);
                    } else if (angle > 90 && angle < 180) {
                        // ((Rocket)m).setDirection(360-angle);
                        ((Rocket) m).setDirection(180 - angle);
                    } else if (angle > 180 && angle < 270) {
                        // ((Rocket)m).setDirection(180-angle);
                        ((Rocket) m).setDirection(360 - angle);
                    }
                }
            }
            for (int j = 0; j < map.getListeBreakableWall().size(); j++) {
                BreakableWall bw = map.getListeBreakableWall().get(j);
                Rectangle bwall = new Rectangle(bw.getX()[0], bw.getY()[0], bw.getX()[1] - bw.getX()[0], bw.getY()[2] - bw.getY()[1]);
                if (missiles.intersects(bwall)){
                    if(bw.getState() > 0){
                        bw.setState(bw.getState()-1);
                        getMissiles().remove(m);
                    }else{
                        map.getInforMap()[bw.getX()[0]/48][bw.getY()[0]/48] = '-';
                    }
                }
            }
            for (int j = 0; j < map.getEnemies().size(); j++){
                Rectangle en = new Rectangle((int)map.getEnemies().get(j).getX(), (int)map.getEnemies().get(j).getY(), 48, 48);
                if(missiles.intersects(en) && m.getShooter() != 2){
                    getMissiles().remove(m);
                    map.getEnemies().get(j).setHealth(map.getEnemies().get(j).getHealth()-50);
                    if(map.getEnemies().get(j).getHealth() <= 0){
                        map.getInforMap()[(int)(map.getEnemies().get(j).getX())/48][(int)(map.getEnemies().get(j).getY())/48] = '-';
                        map.getEnemies().remove(map.getEnemies().get(j));
                    }
                }
            }
        }
    }

    public final void updateEnemies() {
        for (Enemy e : getMap().getEnemies()) {
            double f = Math.sqrt(Math.pow(getShip().getX() - e.getX(), 2) + Math.pow(getShip().getY() - e.getY(), 2));
            if (f < Constants.RANGE && e.canShoot()) {
                getMissiles().add(new MissileNormal(e.getX(), e.getY(),
                                  (int) Math.toDegrees(e.getRad(getShip().getX(), getShip().getY())), 2));
            }
        }
    }

    public final void updateBonus() {
        if (System.currentTimeMillis() - getMap().getLastTime() > Constants.BONUS_SPAWNRATE) {
            getMap().addBonus();
            view.drawBonus();
        }
    }

    @Override
    public void update() {
        ups++;
        inGame();
        if (!checkCollision()) updateShip();
        else {
            if (ship.canTakeDamage()) {
                if (ship.getShield().isActive()) {
                    ship.getShield().destroy();
                    ship.getShield().disable();
                } else {
                    ship.setHealth(ship.getHealth() - 50);
                }
            }
        }
        ship.rotateRight();
        ship.rotateLeft();
        updateMissiles();
        updateBonus();
        updateEnemies();
    }

    public final void switchFullScreenMode() {
        Global.setACT_FULLSCREEN(true);
        Global.setFULLSCREEN(!Global.FULLSCREEN());
        if (Global.FULLSCREEN() && Global.ACT_FULLSCREEN()) {
            win.setDimensionsToFullScreen();
            Global.setACT_FULLSCREEN(false);
            view = new GameView(game);
            win.setFullScreen(view);
        }
        if (!Global.FULLSCREEN() && Global.ACT_FULLSCREEN()) {
            win.setDimensionsToSmallScreen();
            Global.setACT_FULLSCREEN(false);
            view = new GameView(game);
            win.setSmallScreen(view);
        }
    }

}