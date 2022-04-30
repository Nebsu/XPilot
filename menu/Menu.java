package menu;

import main.Global;

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
		this.titleFont = new Font("Century Gothic", Font.PLAIN, Global.W_WIDTH()/5);
		// Ajout des boutons :
		this.start = new TextButton("Start");
		this.settings = new TextButton("Settings");
		this.help = new TextButton("Help");
		this.quit = new TextButton("Quit");
		this.start.setBounds(Global.W_WIDTH()/2 - WIDTH/2, Global.W_HEIGHT()/2 - (3*HEIGHT/2), WIDTH, HEIGHT);
		this.settings.setBounds(Global.W_WIDTH()/2 - WIDTH/2, Global.W_HEIGHT()/2, WIDTH, HEIGHT);
		this.help.setBounds(Global.W_WIDTH()/2 - WIDTH/2, Global.W_HEIGHT()/2 + (3*HEIGHT/2), WIDTH, HEIGHT);
		this.quit.setBounds(Global.W_WIDTH()/2 - WIDTH/2, Global.W_HEIGHT()/2 + (6*HEIGHT/2), WIDTH, HEIGHT);
		this.setLayout(null);
		this.add(start);
		this.add(settings);
		this.add(help);
		this.add(quit);
		this.setPreferredSize(new Dimension(Global.W_WIDTH(), Global.W_HEIGHT()));
		this.invalidate();
		this.repaint();
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
		Graphics2D g2 = (Graphics2D) g;
		draw(g2);
	}

	public final void draw(Graphics2D g) {
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Xpilot", Global.W_WIDTH()/5 + 35, Global.W_HEIGHT()/4);
	}

	public final void setSettingsPanel() {
		add(BorderLayout.CENTER, settingsPanel);
        this.repaint();
	}

}