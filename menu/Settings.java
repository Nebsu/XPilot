package menu;

import game.Game;
import game.GameLoop;
import main.Constants;
import main.Window;

import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.JSlider;

import java.awt.*;

public class Settings extends JPanel {

	// Vue :
	private final Color titleColor;
	private final Font titleFont;
	private final IconButton backButton;
	private final JSlider musicVolume = new JSlider();
	private final JSlider sfxVolume = new JSlider();
	
	public Settings() {
		// Panel :
		super();
		setFocusable(true);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));
		requestFocus();
		// Fonts :
		this.titleColor = new Color(128, 0, 0);
		this.titleFont = new Font("Tahoma", Font.PLAIN, 100);
		// UI Components :
		this.backButton = new IconButton("ressources/images/backbutton.png");
		this.backButton.setBounds(10, 10, 50, 50);
		this.setLayout(null);
		this.add(this.backButton);
		setVisible(true);
		this.repaint();
		this.backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                 FONCTIONS DU MODELE                                                     //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



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
		g.drawString("Xpilot", 300, 100);
		// draw button :
		// g.drawRect()

	}

}