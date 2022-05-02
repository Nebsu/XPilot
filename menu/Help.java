package menu;

import main.Global;

import java.awt.event.*;
import javax.swing.JPanel;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Help extends JPanel {

    // Vue :
	private final BufferedImage logo;
    private final Color textColor;
    private final Font textFont;
	private final IconButton backButton;

	// Size constants :
	private static final int titleWidth = 550;
	private static final int titleHeight = 115;

	public Help() throws IOException {
		// Panel :
		super();
		setFocusable(true);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(Global.W_WIDTH(), Global.W_HEIGHT()));
		requestFocus();
		// Logo :
		this.logo = ImageIO.read(new File("ressources/images/logo1.png"));
        // Text :
        this.textColor = new Color(148, 148, 148);
        this.textFont = new Font("Tahoma", Font.PLAIN, 30);
		// Back button :
		this.setLayout(null);
		this.backButton = new IconButton("ressources/images/backbutton.png");
		this.backButton.setBounds(Global.W_WIDTH()/80, Global.W_WIDTH()/80, Global.W_WIDTH()/16, Global.W_WIDTH()/16);
		this.add(this.backButton);
		this.backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Global.MAINGAME().getWindow().launchMenu(false);
			}
		});
		// Repaint :
		this.invalidate();
		this.repaint();
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                   FONCTIONS DE LA VUE                                                   //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	protected final void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(logo, Global.W_WIDTH()/2 - titleWidth/2, Global.W_HEIGHT()/8 - titleHeight/8, null);
		Graphics2D g2 = (Graphics2D) g;
		draw(g2);
	}
	
	public final void draw(Graphics2D g) {
        // draw commands :
        String[] commands1 = {"Up Key", "Left Key", "Right Key", "Space", "C", "X"}; 
        String[] commands2 = {"W", "A", "D", "Space", "B", "M"};
        String[] commands = (Global.WASD_MODE())? commands2 : commands1;
        g.setColor(textColor);
        g.setFont(textFont);
        g.drawString("Keys :", 350, 250);
        g.drawString("Move :", 150, 330);
        g.drawString("Rotate Left :", 150, 360);
        g.drawString("Rotate Right :", 150, 390);
        g.drawString("Shoot Missile :", 150, 420);
        g.drawString("Activate Shield :", 150, 450);
        g.drawString("Switch Missile Mode :", 150, 480);
        int i = 330;
        for (String s : commands) {
            g.drawString(s, 500, i);
            i += 30;
        }
	}

}