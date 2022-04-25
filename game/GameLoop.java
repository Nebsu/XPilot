/**
 * The GameLoop class is the main loop of the game. It is responsible for the game logic
 */
package game;

import map.*;
import object.*;

import java.io.IOException;
import java.util.ArrayList;

import main.Constants;
import main.Window;
import menu.Menu;

import java.awt.*;

public final class GameLoop implements Game, Runnable {
    public static boolean fullScreenMode = false;
    public static boolean actFullScreen = false;
    SpaceShip ship;
    Map map;
    ArrayList<Missile> missile;
    private static GameView view;
    public static Game game;
    public static Window win;
    boolean gameStarted = false;

    private final double updateRate = 1.0d / 60.0d;
    private int fps, ups;
    private boolean running;
    private long nextStatTime;

    public GameLoop() throws IOException {
        //initialisation des champs
        map = new Map();
        ship = new SpaceShip(Constants.ICRAFT_X, Constants.ICRAFT_Y);
        game = this;
        missile = new ArrayList<>();
        view = new GameView(game);
        GameKeys key = new GameKeys(game, view);
        win = new Window(view);
        win.addKeyListener(key);

    }

    /**
     * Game Loop
     */

    @Override
    public void run() {
        running = true;
        double accumulator = 0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;


        while (running) {

            win.requestFocus();

            currentTime = System.currentTimeMillis();
            double lastRenderTimeSeconds = (currentTime - lastUpdate) / 1000d;
            accumulator += lastRenderTimeSeconds;
            lastUpdate = currentTime;

            if (fullScreenMode && actFullScreen) {
                win.setDimensionsToFullScreen();
                try {
                    view = new GameView(game);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                win.setFullScreen(view);
            }
            if (!fullScreenMode && actFullScreen) {
                win.setDimensionsToSmallScreen();
                try {
                    view = new GameView(game);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                win.setSmallScreen(view);
            }
            actFullScreen = false;

            if (accumulator >= updateRate) {
                while (accumulator > updateRate) {
                    game.update();
                    accumulator -= updateRate;
                }
                repaint();
            }
            printStat();
        }
    }

    private void printStat() {
        if (System.currentTimeMillis() > nextStatTime) {
            System.out.println(String.format("FPS: %d, UPS: %d", fps, ups));
            fps = 0;
            ups = 0;
            nextStatTime = System.currentTimeMillis() + 1000;
        }
    }

    private void repaint() {
        fps++;
        view.repaint();
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
     *
     * @return Nothing.
     */
    public final boolean checkCollision() {
        Rectangle s = new Rectangle((int) (ship.getX() + ship.SPEED * Math.cos(Math.toRadians(ship.rotation))), (int) (ship.getY() + ship.SPEED * Math.sin(Math.toRadians(ship.rotation))), 16, 16);
        for (Obstacle obstacle : map.ListeObstacle) {
            Rectangle o = new Rectangle(obstacle.x[0], obstacle.y[0], obstacle.x[1] - obstacle.x[0], obstacle.y[2] - obstacle.y[1]);
            // Ship après le movement :
            if (s.intersects(o) && !(obstacle instanceof Goal) && !(obstacle instanceof BallHolder) && !(obstacle instanceof Bonus)) {
                return true;
            } else if (s.intersects(o) && obstacle instanceof BallHolder) {
                map.ball.take();
            } else if (s.intersects(o) && obstacle instanceof Goal && map.ball.isTaken()) {
                view.setInGame(false);
            }
        }

        for (int i = 0; i < b.getMap().bonusList.size(); i++){
            Bonus bo = b.getMap().bonusList.get(i);
            Rectangle r=new Rectangle(bo.x[0],bo.y[0],bo.x[1]-bo.x[0],bo.y[2]-bo.y[1]);
            if(s.intersects(r)){
                if(b.getSpaceShip().getFuel()<Constants.BASE_FUEL){
                    if(b.getSpaceShip().getFuel()>Constants.BASE_FUEL - 500){
                        b.getSpaceShip().setFuel(b.getSpaceShip().getFuel()+Constants.BASE_FUEL-b.getSpaceShip().getFuel());
                    }else{
                        b.getSpaceShip().setFuel(b.getSpaceShip().getFuel()+500);
                    }
                }
                b.getSpaceShip().shield.add();
                b.erase(bo);
                b.getMap().bonusList.remove(bo);
            }
        }
        return false;
    }

/**
 * Update the ship's position
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
    public void updateMissiles() {
        for (Missile m : missile) {
            if (m.isVisible()) {
                m.move();
            }
        }
        //detecter le collision du missile 
        for(int i=0;i<b.getMissiles().size();i++) {
            Missile m= b.getMissiles().get(i);
            Rectangle r=m.getBounds();
            Rectangle s = b.getSpaceShip().getBounds();
            if(r.intersects(s)){
                if(b.getMissiles().get(i).shooter == 2){
                    b.getMissiles().remove(m);
                    if(b.getSpaceShip().shield.isActive()){
                        b.getSpaceShip().shield.destroy();
                        b.getSpaceShip().shield.disable();
                    }else{
                        b.getSpaceShip().setHealth(b.getSpaceShip().getHealth()-100);
                    }
                }
            }
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
                    if(angle > 270){
                        // ((Rocket)m).setDirection(180-angle);
                        ((Rocket)m).setDirection(360-angle);
                    }else if(angle < 90){
                        // ((Rocket)m).setDirection(360-angle);
                        ((Rocket)m).setDirection(180-angle);
                    }else if(angle > 90 && angle < 180){
                        // ((Rocket)m).setDirection(360-angle);
                        ((Rocket)m).setDirection(180-angle);
                    }else if(angle > 180 && angle < 270){
                        // ((Rocket)m).setDirection(180-angle);
                        ((Rocket)m).setDirection(360-angle);
                    }
                }
            }
        }
    }

    public final void updateEnemies(){
        for(Enemy e : b.getMap().enemies){
            if(Math.sqrt(Math.pow(b.getSpaceShip().getX()-e.x, 2)+Math.pow(b.getSpaceShip().getY()-e.y,2))< Constants.RANGE && e.canShoot()){
                b.getMissiles().add(new MissileNormale(e.x, e.y, (int)Math.toDegrees(e.getRad(b.getSpaceShip().getX(), b.getSpaceShip().getY())),2));
            }
        }
    }

    public final void updateBonus(){
        if(System.currentTimeMillis() - b.getMap().lastTime > Constants.BONUS_SPAWNRATE){
            b.getMap().addBonus();
            b.drawBonus();
    public boolean hasGamestarted() {
        return gameStarted;
    }

    public void gameStart() {
        gameStarted = true;
    }

    @Override
    public SpaceShip getShip() {
        return ship;
    }

    @Override
    public Map getMap() {
        return map;
    }

    @Override
    public ArrayList<Missile> getMissile() {
        return this.missile;
    }

    @Override
    public GameKeys getKeys() {
        return null;
    }

    @Override
    public void update() {
        ups++;
        inGame();
        if (!checkCollision()) updateShip();
        else {
            if (ship.canTakeDamage()) {
                if (ship.shield.isActive()) {
                    ship.shield.destroy();
                    ship.shield.disable();
                } else {
                    ship.setHealth(ship.getHealth() - 50);
                }
            }
        }
        ship.rotateRight();
        ship.rotateLeft();
        updateMissiles();
        updateBonus();

    }

    @Override
    public boolean hasGameStarted() {
        return gameStarted;
    }
}