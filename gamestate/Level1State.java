package gamestate;

import titlemap.*;
import main.GamePanel;

import java.awt.*;

public class Level1State extends GameState {

    private TitleMap titleMap;

    public Level1State(GameStateManager gsm) {
        this.gsm = gsm;
        this.init();
    }

    @Override
    public void init() {
        this.titleMap = new TitleMap(30);
        titleMap.loadTiles("/Titlesets/grasstitleset.gif");
        titleMap.loadMap("/Maps/level1-1.map");
        titleMap.setPosition(0, 0);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void draw(Graphics2D g) {
        // clear the screen :
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        // draw the map :
        titleMap.draw(g);
    }

    @Override
    public void keyPressed(int k) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyReleased(int k) {
        // TODO Auto-generated method stub
        
    }
    
}