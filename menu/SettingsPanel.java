/**
 * The SettingsPanel class is a JPanel that contains a JLabel and a JButton
 */
package menu;

import main.Constants;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.awt.*;

public class SettingsPanel extends JPanel {

	// Vue :
	final Color titleColor;
	final Font titleFont;
	final Font font;
	
	public SettingsPanel() {
		
		// Panel :
		super();
		setFocusable(true);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));
		requestFocus();

		// Fonts :
		this.titleColor = new Color(128, 0, 0);
		this.titleFont = new Font("Century Gothic", Font.PLAIN, 80);
		this.font = new Font("Arial", Font.PLAIN, 50);

		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);

		JLabel donner = new JLabel("Donner");
		donner.setBounds(103, 27, 46, 14);
		this.add(donner);

		JLabel lblRecevoir = new JLabel("Recevoir");
		lblRecevoir.setBounds(354, 27, 46, 14);
		this.add(lblRecevoir);

		//Action du bouton Retour
		JButton retour = new JButton("Retour");
		retour.setBounds(354, 117, 89, 23);
		retour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                
			}
		});

		this.add(retour);
	}

	@Override
	protected final void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		draw(g2);
	}
	
/**
 * Draw the title of the game
 * 
 * @param g The Graphics2D object that is used to draw the text.
 */
	public final void draw(Graphics2D g) {
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Xpilot", 300, 100);

	}

}