package menu;

import main.Constants;
import game.GameLoop;

import java.awt.event.*;
import javax.swing.JPanel;
import java.awt.*;

public class HelpPanel extends JPanel {

    // Vue :
	private final Color titleColor;
	private final Font titleFont;
    private final Color textColor;
    private final Font textFont;
	private final IconButton backButton;

	public HelpPanel() {
		// Panel :
		super();
		setFocusable(true);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));
		requestFocus();
		// Title :
		this.titleColor = new Color(128, 0, 0);
		this.titleFont = new Font("Century Gothic", Font.PLAIN, 150);
        // Text :
        this.textColor = new Color(148, 148, 148);
        this.textFont = new Font("Tahoma", Font.PLAIN, 30);
		// Back button :
		this.setLayout(null);
		this.backButton = new IconButton("ressources/images/backbutton.png");
		this.backButton.setBounds(10, 10, 50, 50);
		this.add(this.backButton);
		this.backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameLoop.win.launchMenu(false);
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
		Graphics2D g2 = (Graphics2D) g;
		draw(g2);
	}
	
	public final void draw(Graphics2D g) {
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Xpilot", 200, 150);
        // draw commands :
        String[] commands1 = {"Up Key", "Left Key", "Right Key", "Space", "C", "X"}; 
        String[] commands2 = {"W", "A", "D", "Space", "B", "M"};
        String[] commands = (Constants.WASD_MODE)? commands2 : commands1;
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