package menu;

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
	private final JButton start;
	private final JButton settings;
	private final JButton help;
	private final JButton quit;

	// Sections :
	private final SettingsPanel settingsPanel;
	private final HelpPanel helpPanel;

	// Getters :
	public final SettingsPanel getSettingsPanel() {return settingsPanel;}
	public final HelpPanel getHelpPanel() {return helpPanel;}

	public Menu() {
		// Initialisation du panel :
		super();
		setFocusable(true);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));
		requestFocus();
		// Initialisation des sections :
		this.settingsPanel = new SettingsPanel();
		this.helpPanel = new HelpPanel();
		// Fonts :
		this.titleColor = new Color(128, 0, 0);
		this.titleFont = new Font("Century Gothic", Font.PLAIN, 80);
		// Music :
		String filepath = "ressources/audio/menuMusic.wav";
		this.menuMusic = new Music(filepath);
		// Ajout des boutons :
		this.start = new JButton("Start");
		this.settings = new JButton("Settings");
		this.help = new JButton("Help");
		this.quit = new JButton("Quit");
		this.add(start);
		this.add(settings);
		this.add(help);
		this.add(quit);
		this.start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Window.WINDOW.launchGame();
				} catch (IOException | LineUnavailableException e1) {
					e1.printStackTrace();
					System.out.println(e1);
					System.exit(1);
				}
			}
		});
		this.settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Window.MENU.setVisible(false);
				Window.WINDOW.settingsPanel();
			}
		});
		this.quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
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
		g.drawString("Xpilot", 300, 200);
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

	public final void stopMenuMusic() {
		this.menuMusic.stopMusic();
	}

}