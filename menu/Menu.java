package menu;

import game.GameLoop;
import main.Constants;
import sound.Music;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public final class Menu extends JPanel {

	// Vue :
	private final Color titleColor;
	private final Font titleFont;
	private final JButton start;
	private final JButton settings;
	private final JButton help;
	private final JButton quit;

	// Music :
	private static final String filepath = "ressources/audio/menuMusic.wav";
	public static final Music menuMusic = new Music(filepath);

	// Sections :
	private final Settings settingsPanel = new Settings();
	private final Help helpPanel = new Help();

	// Getters :
	public final Settings getSettingsPanel() {return settingsPanel;}
	public final Help getHelpPanel() {return helpPanel;}

	// Size constants :
	private static final int WIDTH = 120;
	private static final int HEIGHT = 60;

	public Menu() {
		// Initialisation du panel :
		super();
		setFocusable(true);
		setBackground(Color.BLACK);
		requestFocus();
		this.titleColor = new Color(128, 0, 0);
		this.titleFont = new Font("Century Gothic", Font.PLAIN, Constants.B_WIDTH/5);
		// Ajout des boutons :
		this.start = new TextButton("Start");
		this.settings = new TextButton("Settings");
		this.help = new TextButton("Help");
		this.quit = new TextButton("Quit");
		this.start.setBounds(Constants.B_WIDTH/2 - WIDTH/2, Constants.B_HEIGHT/2 - (3*HEIGHT/2), WIDTH, HEIGHT);
		this.settings.setBounds(Constants.B_WIDTH/2 - WIDTH/2, Constants.B_HEIGHT/2, WIDTH, HEIGHT);
		this.help.setBounds(Constants.B_WIDTH/2 - WIDTH/2, Constants.B_HEIGHT/2 + (3*HEIGHT/2), WIDTH, HEIGHT);
		this.quit.setBounds(Constants.B_WIDTH/2 - WIDTH/2, Constants.B_HEIGHT/2 + (6*HEIGHT/2), WIDTH, HEIGHT);
		this.setLayout(null);
		this.add(start);
		this.add(settings);
		this.add(help);
		this.add(quit);
		this.setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));
		this.invalidate();
		this.repaint();
		this.start.addActionListener(e -> {
			try {
				GameLoop.win.launchGame();
			} catch (IOException | LineUnavailableException e1) {
				e1.printStackTrace();
				System.out.println(e1);
				System.exit(1);
			}
		});
		this.settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameLoop.win.settingsPanel();
			}
		});
		this.help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameLoop.win.helpPanel();
			}
		});
		this.quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
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
		g.drawString("Xpilot", Constants.B_WIDTH/5 + 35, Constants.B_HEIGHT/4);
	}

	public void setSettingsPanel(){
		add(BorderLayout.CENTER,settingsPanel);
        this.repaint();
	}

}