package menu;

import main.Constants;
import main.Window;
import sound.Music;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JPanel;

public final class Menu extends JPanel {

	// Modèle :
	int currentChoice = 0;
	final String[] options = {"Start","Settings","Help","Quit"}; 
	final MenuKeys keys;

	// Vue :
	final Color titleColor;
	final Font titleFont;
	final Font font;
	final Music menuMusic;
	
	public Menu() {
		// Panel :
		super();
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		this.keys = new MenuKeys(this);
		setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));
		requestFocus();
		// Fonts :
		this.titleColor = new Color(128, 0, 0);
		this.titleFont = new Font("Century Gothic", Font.PLAIN, 80);
		this.font = new Font("Arial", Font.PLAIN, 50);
		// Music :
		String filepath = "ressources/audio/menuMusic.wav";
		this.menuMusic = new Music(filepath);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                 FONCTIONS DU MODELE                                                     //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Sélection :
	public final void select() throws IOException, LineUnavailableException {
		if (currentChoice == 0) {
			// start
			this.menuMusic.stopMusic();
			Window.WINDOW.launchGame();
		}
		if (currentChoice == 1) {
			// settings :
			Window.WINDOW.settingsSection();
		}
		if (currentChoice == 2) {
			// help
		}
		if (currentChoice == 3) {
			// quit :
			System.exit(0);
		}
	}

	// Inputs :
	private final class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            keys.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            keys.keyPressed(e);
        }
		
    }

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                   FONCTIONS DE LA VUE                                                   //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	protected final void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		draw(g2);
	}
	
	public final void draw(Graphics2D g) {
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

	public final void playMenuMusic() {
		try {
			this.menuMusic.playMusic();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			System.exit(1);
		}
	}

}