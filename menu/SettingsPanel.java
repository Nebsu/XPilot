package menu;

import main.Constants;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.*;

public class SettingsPanel extends JPanel {

	// Vue :
	private final Color titleColor;
	private final Font titleFont;
	private final JButton backButton;
	
	public SettingsPanel() {
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
		IconButton b = new IconButton("ressources/images/backbutton.png");
		this.backButton = b.button;
		this.add(this.backButton);
		this.backButton.setSize(50, 50);
		this.backButton.setLocation(10, 10);
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