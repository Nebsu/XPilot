package gamestate;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {
	
	private int currentChoice = 0;
	private String[] options = {"Start","Help","Quit"};
	private Color titleColor;
	private Font titleFont;
	private Font font;
	
	public MenuState(GameStateManager gsm) {
		this.gsm = gsm;
		try {
			titleColor = new Color(128, 0, 0);
			titleFont = new Font("Century Gothic", Font.PLAIN, 80);
			font = new Font("Arial", Font.PLAIN, 50);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {}
	
	public void update() {}
	
	public void draw(Graphics2D g) {
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Xpilot", 300, 200);
		// draw menu options
		g.setFont(font);
		for (int i=0; i<options.length; i++) {
			if(i==currentChoice) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 350, 300+i*50);
		}
		
	}
	
	private void select() {
		if (currentChoice == 0) {
			// start :
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if (currentChoice == 1) {
			// help
		}
		if (currentChoice == 2) {
			// quit :
			System.exit(0);
		}
	}
	
	public void keyPressed(int k) {
		if (k==KeyEvent.VK_ENTER) {
			select();
		}
		if (k==KeyEvent.VK_UP) {
			currentChoice--;
			if (currentChoice==-1) {
				currentChoice = options.length-1;
			}
		}
		if (k==KeyEvent.VK_DOWN) {
			currentChoice++;
			if (currentChoice==options.length) {
				currentChoice = 0;
			}
		}
	}

	public void keyReleased(int k) {}
	
}