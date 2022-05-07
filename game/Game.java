package game;

import map.Map;
import main.Window;
import object.Missile;
import object.SpaceShip;

import java.util.ArrayList;

public interface Game {

    public Window getWindow();
    public GameView getView();
    public Game getGame();
    public SpaceShip getShip();
    public Map getMap();
    public ArrayList<Missile> getMissiles();
    public GameKeys getKeys();
    public void update();
    public boolean hasGameStarted();
    public void gameStart();
    public GameLoop getLoop();

}