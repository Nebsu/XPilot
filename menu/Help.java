package menu;

import main.Constants;
import main.Global;

import java.awt.event.*;
import javax.swing.JPanel;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JLabel;

public final class Help extends JPanel {

	private final BufferedImage logo;
	private final IconButton backButton;
	private final ArrayList<JLabel> textLabels;
	private final Font textFont;
	private final Color textColor;

	public Help(boolean beginning) throws IOException {
		// Panel :
		super();
		setFocusable(true);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(Global.W_WIDTH(), Global.W_HEIGHT()));
		requestFocus();
		this.setLayout(null);
		// Logo :
		this.logo = ImageIO.read(new File("ressources/images/logo1.png"));
		// Back button :
		this.backButton = new IconButton("ressources/images/backbutton.png");
		this.backButton.setBounds(Global.W_WIDTH()/80, Global.W_HEIGHT()/60, 50, 50);
		this.add(this.backButton);
		this.backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Global.MAINGAME().getWindow().launchMenu(false, beginning);
			}
		});
		// Text lines :
		this.textFont = new Font("Tahoma", Font.PLAIN, 30);
		this.textColor = new Color(148, 148, 148);
		this.textLabels = new ArrayList<JLabel>();
		String[] commands1 = {"Up Key", "Left Key", "Right Key", "Space", "C", "X", "F"}; 
        String[] commands2 = {"W", "A", "D", "Enter", "L", "M", "P"};
        String[] commands = (Global.WASD_MODE())? commands2 : commands1;
		textLabels.add(new JLabel("Move : " + commands[0]));
		textLabels.add(new JLabel("RotateLeft : " + commands[1]));
		textLabels.add(new JLabel("RotateRight : " + commands[2]));
		textLabels.add(new JLabel("Shoot Missile : " + commands[3]));
		textLabels.add(new JLabel("Activate Shield : " + commands[4]));
		textLabels.add(new JLabel("Switch Missile Mode : " + commands[5]));
		textLabels.add(new JLabel("Switch Fullscreen : " + commands[6]));
		int xPos = Global.W_WIDTH() / 3;
		int yPos = Global.W_HEIGHT() / 3;
		for (JLabel lab : textLabels) {
			lab.setFont(this.textFont);
			lab.setBackground(Color.RED);
			lab.setForeground(this.textColor);
			lab.setBounds(xPos, yPos, textW(), textH());
			this.add(lab);
			yPos += Global.W_HEIGHT() / (2 * textLabels.size());
		}
		// Repaint :
		this.invalidate();
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(logo, Global.W_WIDTH() / 2 - Constants.TITLE_WIDTH / 2, 
					Global.W_HEIGHT() / 8 - Constants.TITLE_HEIGHT / 8, null);
	}

	private static final int textW() {
		return Global.W_WIDTH() - (Global.W_WIDTH() / 4);
	}

	private static final int textH() {
		return Global.W_HEIGHT() / 12;
	}

}