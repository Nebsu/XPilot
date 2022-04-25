package game;

import map.Map;
import object.Missile;
import object.SpaceShip;

import java.util.ArrayList;

public interface Game {

    SpaceShip getShip();
    Map getMap();
    ArrayList<Missile> getMissile();
    GameKeys getKeys();
    void update();
    public boolean hasGameStarted();
    public void gameStart();

}
