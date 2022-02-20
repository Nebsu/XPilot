package gamestate;

import java.util.ArrayList;
import java.awt.Graphics2D;

public class GameStateManager {
	
	protected ArrayList<GameState> states;
	protected int currentState;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	
	public GameStateManager() {
		states = new ArrayList<GameState>();
		currentState = MENUSTATE;
		states.add(new MenuState(this));
		states.add(new Level1State(this));
	}
	
	public void setState(int state) {
		currentState = state;
		states.get(currentState).init();
	}
	
	public void update() {
		states.get(currentState).update();
	}
	
	public void draw(Graphics2D g) {
		states.get(currentState).draw(g);
	}
	
	public void keyPressed(int k) {
		states.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k) {
		states.get(currentState).keyReleased(k);
	}
	
}