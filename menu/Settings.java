package menu;

import main.Constants;
import main.Window;
import sound.Music;
import sound.SFX;

import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import java.awt.*;

public class Settings extends JPanel {

	// Vue :
	private final Color titleColor;
	private final Font titleFont;
	private final IconButton backButton;
	private final JSlider musicVolumeSlider;
	private final JSlider sfxVolumeSlider;
	private final JLabel musicVolumeLabel;
	private final JLabel sfxVolumeLabel;
	private final TextButton wasdMode;
	private final JLabel wasdLabel;

	public Settings() {
		// Panel :
		super();
		setFocusable(true);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(Constants.B_WIDTH, Constants.B_HEIGHT));
		requestFocus();
		// Title :
		this.titleColor = new Color(128, 0, 0);
		this.titleFont = new Font("Century Gothic", Font.PLAIN, 150);
		// Back button :
		this.setLayout(null);
		this.backButton = new IconButton("ressources/images/backbutton.png");
		this.backButton.setBounds(10, 10, 50, 50);
		this.add(this.backButton);
		this.backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Window.WINDOW.launchMenu(false);
			}
		});
		// Music Sliders :
		this.musicVolumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
		this.sfxVolumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
		this.musicVolumeSlider.setMajorTickSpacing(10);
		this.sfxVolumeSlider.setMajorTickSpacing(10);
		// this.musicVolumeSlider.setPaintTicks(true);
		// this.sfxVolumeSlider.setPaintTicks(true);
		// this.musicVolumeSlider.setSnapToTicks(true);
		// this.sfxVolumeSlider.setSnapToTicks(true);
		this.musicVolumeSlider.setBackground(Color.BLACK);
		this.sfxVolumeSlider.setBackground(Color.BLACK);
		this.musicVolumeSlider.setBounds(300, 250, 200, 50);
		this.sfxVolumeSlider.setBounds(300, 350, 200, 50);
		this.musicVolumeLabel = new JLabel("Music Volume : 100");
		this.sfxVolumeLabel = new JLabel("SFX Volume : 100");
		this.musicVolumeLabel.setBounds(350, 200, 200, 50);
		this.sfxVolumeLabel.setBounds(350, 300, 200, 50);
		this.add(musicVolumeSlider);
		this.add(sfxVolumeSlider);
		this.add(musicVolumeLabel);
		this.add(sfxVolumeLabel);
		MusEvent e1 = new MusEvent();
		musicVolumeSlider.addChangeListener(e1);
		SFXEvent e2 = new SFXEvent();
		sfxVolumeSlider.addChangeListener(e2);
		// WASD Mode button :
		this.wasdMode = new TextButton("WASD MODE");
		this.wasdMode.setBounds(325, 450, 150, 50);
		this.add(this.wasdMode);
		wasdMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Constants.WASD_MODE) {
					Constants.CUSTOM_KEYS.WASD_MODE(false);
					wasdLabel.setText("Status : OFF");
				} else {
					Constants.CUSTOM_KEYS.WASD_MODE(true);
					wasdLabel.setText("Status : ON");
				}
			}
		});
		this.wasdLabel = new JLabel("Status : OFF");
		this.wasdLabel.setBounds(362, 475, 100, 100);
		this.add(this.wasdLabel);
		// Repaint :
		this.invalidate();
		this.repaint();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                 FONCTIONS DU MODELE                                                     //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public class MusEvent implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			int value = musicVolumeSlider.getValue();
			musicVolumeLabel.setText("Music Volume : "+value);
			setSoundVolume(value, true);
		}

	}

	public class SFXEvent implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			int value = sfxVolumeSlider.getValue();
			sfxVolumeLabel.setText("SFX Volume : "+value);
			setSoundVolume(value, false);
		}

	}

	public void setSoundVolume(int value, boolean isMusic) {
		float volume = -80.0f;
		if (value==0) volume = -80.0f; // muet
		else if (value==100) volume = 0.0f; // son à max
		else volume = -20.0f + (float) value / 10 * 2.0f; // valeur slider
		if (isMusic) {
			Music.setMusicVolume(volume);
			Window.MENU.getMenuMusic().changeGain(volume);
			if (Window.getMainGame()!=null) 
				Window.getMainGame().getGameMusic().changeGain(volume);
		} else {
			SFX.setMusicVolume(volume);
		}
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
	}

}