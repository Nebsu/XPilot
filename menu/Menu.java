package menu;

import main.Global;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.*;

public final class Menu extends JPanel {

	// Vue :
	private final BufferedImage logo;
	private final JButton start;
	private final JButton settings;
	private final JButton help;
	private final JButton quit;

	// Sections :
	private final Settings settingsPanel = new Settings();
	private final Help helpPanel = new Help();

	// Getters :
	public final Settings getSettingsPanel() {return settingsPanel;}
	public final Help getHelpPanel() {return helpPanel;}

	// Size constants :
	private static final int buttonWidth = 120;
	private static final int buttonHeight = 60;
	private static final int titleWidth = 550;
	private static final int titleHeight = 115;

	public Menu() throws IOException {
		// Initialisation du panel :
		super();
		setFocusable(true);
		setBackground(Color.BLACK);
		requestFocus();
		this.setLayout(null);
		this.setPreferredSize(new Dimension(Global.W_WIDTH(), Global.W_HEIGHT()));
		// Logo :
		this.logo = ImageIO.read(new File("ressources/images/logo1.png"));
		// Ajout des boutons :
		this.start = new TextButton("Start");
		this.settings = new TextButton("Settings");
		this.help = new TextButton("Help");
		this.quit = new TextButton("Quit");
		this.start.setBounds(Global.W_WIDTH()/2 - buttonWidth/2, Global.W_HEIGHT()/2 - (3*buttonHeight/2), buttonWidth, buttonHeight);
		this.settings.setBounds(Global.W_WIDTH()/2 - buttonWidth/2, Global.W_HEIGHT()/2, buttonWidth, buttonHeight);
		this.help.setBounds(Global.W_WIDTH()/2 - buttonWidth/2, Global.W_HEIGHT()/2 + (3*buttonHeight/2), buttonWidth, buttonHeight);
		this.quit.setBounds(Global.W_WIDTH()/2 - buttonWidth/2, Global.W_HEIGHT()/2 + (6*buttonHeight/2), buttonWidth, buttonHeight);
		this.add(start);
		this.add(settings);
		this.add(help);
		this.add(quit);
		this.invalidate();
		this.repaint();
		// Action Listener des boutons :
		this.start.addActionListener(e -> {
			try {
				Global.MAINGAME().getWindow().launchGame();
			} catch (IOException | LineUnavailableException e1) {
				e1.printStackTrace();
				System.out.println(e1);
				System.exit(1);
			}
		});
		this.settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Global.MAINGAME().getWindow().settingsPanel();
			}
		});
		this.help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Global.MAINGAME().getWindow().helpPanel();
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
		g.drawImage(logo, Global.W_WIDTH()/2 - titleWidth/2, Global.W_HEIGHT()/8 - titleHeight/8, null);
	}

	public final void setSettingsPanel() {
		add(BorderLayout.CENTER, settingsPanel);
        this.repaint();
	}

}