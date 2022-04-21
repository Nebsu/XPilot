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

        for (int i = 0; i < map.bonusList.size(); i++) {
            Bonus bo = map.bonusList.get(i);
            Rectangle r = new Rectangle(bo.x[0], bo.y[0], bo.x[1] - bo.x[0], bo.y[2] - bo.y[1]);
            if (s.intersects(r)) {
                if (ship.getFuel() < ship.BASE_FUEL) ship.setFuel(ship.getFuel() + 500);
                ship.shield.add();
                view.erase(bo);
                map.bonusList.remove(bo);
            }
        }
        return false;
    }

    /**
     * Update the ship's position and velocity
     */
    public void updateShip() {
        if (ship.isVisible()) {
            ship.move();
            ship.acceleration();
            ship.deceleration();
            if (ship.moveFlag) ship.consumeFuel();
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
        for (int i = 0; i < missile.size(); i++) {
            Missile m = missile.get(i);
            Rectangle r = m.getBounds();
            for (Obstacle ob : map.ListeObstacle) {
                Rectangle o = new Rectangle(ob.x[0], ob.y[0], ob.x[1] - ob.x[0], ob.y[2] - ob.y[1]);
                if (r.intersects(o)) {
                    missile.remove(m);
                }
            }
        }
    }

    public boolean hasGamestarted() {
        return gameStarted;
    }

    public void gameStart() {
        gameStarted = true;
    }

    public void updateBonus() {
        if (System.currentTimeMillis() - map.lastTime > Constants.BONUS_SPAWNRATE) {
            map.addBonus();
            view.drawBonus();
        }
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