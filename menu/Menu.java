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
	private final Music menuMusic;
	private final JButton start;
	private final JButton settings;
	private final JButton help;
	private final JButton quit;

	// Sections :
	private final Settings settingsPanel = new Settings();
	private final HelpPanel helpPanel = new HelpPanel();

	// Getters :
	public final Settings getSettingsPanel() {return settingsPanel;}
	public final HelpPanel getHelpPanel() {return helpPanel;}
	public final Music getMenuMusic() {return menuMusic;}

	public Menu() {
		// Initialisation du panel :
		super();
		setFocusable(true);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));
		requestFocus();
		this.titleColor = new Color(128, 0, 0);
		this.titleFont = new Font("Century Gothic", Font.PLAIN, 150);
		// Music :
		String filepath = "ressources/audio/menuMusic.wav";
		this.menuMusic = new Music(filepath);
		// Ajout des boutons :
		this.start = new TextButton("Start");
		this.settings = new TextButton("Settings");
		this.help = new TextButton("Help");
		this.quit = new TextButton("Quit");
		this.start.setBounds(350, 250, 100, 50);
		this.settings.setBounds(350, 325, 100, 50);
		this.help.setBounds(350, 400, 100, 50);
		this.quit.setBounds(350, 475, 100, 50);
		this.setLayout(null);
		this.add(start);
		this.add(settings);
		this.add(help);
		this.add(quit);
		this.invalidate();
		this.repaint();
		this.start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					GameLoop.win.launchGame();
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
		g.drawString("Xpilot", 200, 150);
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