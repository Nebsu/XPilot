package menu;

import game.Game;
import game.GameLoop;
import game.GameView;
import main.Constants;
import main.Window;
import sound.Music;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public final class Menu extends JPanel {

	// Vue :
	final String[] options = {"Start","Settings","Help","Quit"};
	private final Color titleColor;
	private final Font titleFont;
	private final Music menuMusic;

	private Game game;
	// Sections :
	private final Settings settingsPanel;
	private final HelpPanel helpPanel;

	// Getters :
	public final Settings getSettingsPanel() {return settingsPanel;}
	public final HelpPanel getHelpPanel() {return helpPanel;}

	public Menu(Game game, GameView view) {
		// Initialisation du panel :
		super();
		this.game = game;
		setFocusable(true);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));
		requestFocus();
		// Initialisation des sections :
		this.settingsPanel = new Settings();
		this.helpPanel = new HelpPanel();
		// Fonts :
		this.titleColor = new Color(128, 0, 0);
		this.titleFont = new Font("Century Gothic", Font.PLAIN, 80);
		// Music :
		String filepath = "ressources/audio/menuMusic.wav";
		this.menuMusic = new Music(filepath);
		// Ajout des boutons :
		JButton start = new TextButton("Start");
		JButton settings = new TextButton("Settings");
		JButton help = new TextButton("Help");
		JButton quit = new TextButton("Quit");
		start.setBounds(350, 250, 100, 50);
		settings.setBounds(350, 325, 100, 50);
		help.setBounds(350, 400, 100, 50);
		quit.setBounds(350, 475, 100, 50);
		this.setLayout(null);
		this.add(start);
		this.add(settings);
		this.add(help);
		this.add(quit);
		this.repaint();
		start.addActionListener(e -> game.gameStart());
		settings.addActionListener(e -> view.settings = true);
		quit.addActionListener(e -> System.exit(0));
	}

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
		g.drawString("Xpilot", 300, 150);
	}

	public final void playMenuMusic() {
		try {
			this.menuMusic.playMusic();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	public void setSettingsPanel(){
		add(BorderLayout.CENTER,settingsPanel);
        this.repaint();
	}
	public final void stopMenuMusic() {
		this.menuMusic.stopMusic();
	}

}