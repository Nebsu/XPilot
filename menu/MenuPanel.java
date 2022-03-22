package menu;

import main.*;
import main.Window;
import sound.Music;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

	int currentChoice = 0;
	String[] options = {"Start","Help","Quit"};
	private Color titleColor;
	private Font titleFont;
	private Font font;
	private MenuKeys keys;
	private Music menuMusic;
	
	public MenuPanel() {
		super();
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		this.keys = new MenuKeys(this);
		setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));
		requestFocus();
		try {
			this.playMusic();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playMusic() throws LineUnavailableException, IOException {
		try {
			// Music :
			String filepath = "ressources/pokemon.wav";
			this.menuMusic = new Music(filepath);
			menuMusic.playMusic();
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
		// Fonts :
		titleColor = new Color(128, 0, 0);
		titleFont = new Font("Century Gothic", Font.PLAIN, 80);
		font = new Font("Arial", Font.PLAIN, 50);
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
			this.menuMusic.stopMusic();
			Constants.WINDOW = new Window(false);
            Constants.WINDOW.setVisible(true);
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