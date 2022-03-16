package menu;

import main.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
// import javax.sound.sampled.AudioSystem;
// import javax.sound.sampled.Clip;
// import javax.sound.sampled.AudioInputStream;
// import java.net.URL;

public class MenuPanel extends JPanel {

	int currentChoice = 0;
	String[] options = {"Start","Help","Quit"};
	private Color titleColor;
	private Font titleFont;
	private Font font;
	private Game game;
	private MenuKeys keys;
	
	public MenuPanel(Game game) {
		super();
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		this.game = game;
		this.keys = new MenuKeys(this);
		setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));
		requestFocus();
		try {
			// Music :
			// URL url = Menu.class.getResource("music.wav");
			// AudioInputStream audio = AudioSystem.getAudioInputStream(url);
			// Clip music = AudioSystem.getClip();
			// music.open(audio);
			// music.loop(-1);
			// Fonts :
			titleColor = new Color(128, 0, 0);
			titleFont = new Font("Century Gothic", Font.PLAIN, 80);
			font = new Font("Arial", Font.PLAIN, 50);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		draw(g2);
	}
	
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
	
	public void select() {
		if (currentChoice == 0) {
			// start
			Game.GAME = new Game(false);
            Game.GAME.setVisible(true);
		}
		if (currentChoice == 1) {
			// help
		}
		if (currentChoice == 2) {
			// quit :
			System.exit(0);
		}
	}

	private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            keys.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            keys.keyPressed(e);
        }
		
    }

}