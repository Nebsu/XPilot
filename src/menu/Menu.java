package src.menu;

import src.main.Constants;
import src.main.Globals;

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

	// Is main menu or level menu :
	private final boolean beginning;

	// Sub sections :
	private final Settings settingsPanel;
	private final Help helpPanel;

	public Menu(boolean b) throws IOException {
		// Panel initialisation :
		super();
		this.beginning = b;
		Globals.changeMenuState(this.beginning);
		this.settingsPanel = new Settings(this.beginning);
		this.helpPanel = new Help(this.beginning);
		setFocusable(true);
		setBackground(Color.BLACK);
		requestFocus();
		this.setLayout(null);
		this.setPreferredSize(new Dimension(Globals.W_WIDTH(), Globals.W_HEIGHT()));
		// Logo :
		this.logo = ImageIO.read(new File("ressources/images/logo1.png"));
		// Buttons :
		String s1 = (beginning)? "Start" : "Resume";
		this.start = new TextButton(s1);
		this.settings = new TextButton("Settings");
		this.help = new TextButton("Help");
		String s2 = (beginning)? "Exit" : "Quit Level";
		this.quit = new TextButton(s2);
		this.start.setBounds(Globals.W_WIDTH()/2 - Constants.BUTTON_WIDTH/2, 
							 Globals.W_HEIGHT()/2 - (3*Constants.BUTTON_HEIGHT/2), 
							 Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
		this.settings.setBounds(Globals.W_WIDTH()/2 - Constants.BUTTON_WIDTH/2, Globals.W_HEIGHT()/2, 
								Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
		this.help.setBounds(Globals.W_WIDTH()/2 - Constants.BUTTON_WIDTH/2, 
							Globals.W_HEIGHT()/2 + (3*Constants.BUTTON_HEIGHT/2), 
							Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
		this.quit.setBounds(Globals.W_WIDTH()/2 - Constants.BUTTON_WIDTH/2, 
							Globals.W_HEIGHT()/2 + (6*Constants.BUTTON_HEIGHT/2), 
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
				if(Globals.IS_TOP_MENU()){
					Globals.MAINGAME().getWindow().launchGame(false);
				}else{
					Globals.MAINGAME().getWindow().launchGame(true);
					Globals.MAINGAME().getLoop().resetLevel();
                	Globals.MAINGAME().getLoop().switchLevel();
				}
			} catch (IOException | LineUnavailableException e1) {
				e1.printStackTrace();
				System.out.println(e1);
				System.exit(1);
			}
		});
		this.settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Globals.MAINGAME().getWindow().settingsPanel();
			}
		});
		this.help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Globals.MAINGAME().getWindow().helpPanel();
			}
		});
		this.quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (beginning)
					System.exit(0);
				else 
					Globals.MAINGAME().getWindow().launchMenu(true, true);
			}
		});
	}

	public final boolean isBeginning() {return beginning;}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(logo, Globals.W_WIDTH()/2 - Constants.TITLE_WIDTH/2, 
					Globals.W_HEIGHT()/8 - Constants.TITLE_HEIGHT/8, null);
	}

	// Getters :
	public final Settings getSettingsPanel() {return settingsPanel;}
	public final Help getHelpPanel() {return helpPanel;}

}