package menu;

import main.Constants;
import main.Global;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.*;

public final class Menu extends JPanel {

	// View :
	private final BufferedImage logo;
	private final JButton start;
	private final JButton settings;
	private final JButton help;
	private final JButton quit;

	// Sub sections :
	private final Settings settingsPanel = new Settings();
	private final Help helpPanel = new Help();

	public Menu() throws IOException {
		// Panel initialisation :
		super();
		setFocusable(true);
		setBackground(Color.BLACK);
		requestFocus();
		this.setLayout(null);
		this.setPreferredSize(new Dimension(Global.W_WIDTH(), Global.W_HEIGHT()));
		// Logo :
		this.logo = ImageIO.read(new File("ressources/images/logo1.png"));
		// Buttons :
		this.start = new TextButton("Start");
		this.settings = new TextButton("Settings");
		this.help = new TextButton("Help");
		this.quit = new TextButton("Quit");
		this.start.setBounds(Global.W_WIDTH()/2 - Constants.BUTTON_WIDTH/2, 
							 Global.W_HEIGHT()/2 - (3*Constants.BUTTON_HEIGHT/2), 
							 Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
		this.settings.setBounds(Global.W_WIDTH()/2 - Constants.BUTTON_WIDTH/2, Global.W_HEIGHT()/2, 
								Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
		this.help.setBounds(Global.W_WIDTH()/2 - Constants.BUTTON_WIDTH/2, 
							Global.W_HEIGHT()/2 + (3*Constants.BUTTON_HEIGHT/2), 
							Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
		this.quit.setBounds(Global.W_WIDTH()/2 - Constants.BUTTON_WIDTH/2, 
							Global.W_HEIGHT()/2 + (6*Constants.BUTTON_HEIGHT/2), 
							Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
		this.add(start);
		this.add(settings);
		this.add(help);
		this.add(quit);
		this.invalidate();
		this.repaint();
		// Buttons action listeners :
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

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(logo, Global.W_WIDTH()/2 - Constants.TITLE_WIDTH/2, 
					Global.W_HEIGHT()/8 - Constants.TITLE_HEIGHT/8, null);
	}

	// Getters :
	public final Settings getSettingsPanel() {return settingsPanel;}
	public final Help getHelpPanel() {return helpPanel;}

}