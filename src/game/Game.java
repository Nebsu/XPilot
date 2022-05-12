package src.game;

import src.map.Map;
import src.main.Window;
import src.object.Missile;
import src.object.SpaceShip;

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