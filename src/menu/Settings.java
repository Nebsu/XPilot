package src.menu;

import src.main.Constants;
import src.main.Globals;
import src.sound.Music;
import src.sound.SFX;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import javax.swing.JLabel;
import java.io.*;
import java.awt.Color;

public final class Settings extends JPanel {

	private final BufferedImage logo;
	private final IconButton backButton;
	private final JSlider musicVolumeSlider;
	private final JSlider sfxVolumeSlider;
	private final JLabel musicVolInfo;
	private final JLabel sfxVolInfo;
	private final TextButton wasdMode;
	private final JLabel wasdInfo;
	private final Font textFont;
	private final Color textColor;

	public Settings(boolean beginning) throws IOException {
		// Panel :
		super();
		setFocusable(true);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(Globals.W_WIDTH(), Globals.W_HEIGHT()));
		requestFocus();
		this.setLayout(null);
		// Logo :
		this.logo = ImageIO.read(new File("ressources/images/logo1.png"));
		// Back button :
		this.backButton = new IconButton("ressources/images/backbutton.png");
		this.backButton.setBounds(Globals.W_WIDTH()/80, Globals.W_HEIGHT()/60, 50, 50);
		this.add(this.backButton);
		this.backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Globals.MAINGAME().getWindow().launchMenu(false, beginning);
			}
		});
		// Music Sliders :
		this.musicVolumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, Globals.MUSIC_VOLUME());
		this.sfxVolumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, Globals.SFX_VOLUME());
		this.musicVolumeSlider.setMajorTickSpacing(10);
		this.sfxVolumeSlider.setMajorTickSpacing(10);
		// this.musicVolumeSlider.setPaintTicks(true);
		// this.sfxVolumeSlider.setPaintTicks(true);
		// this.musicVolumeSlider.setSnapToTicks(true);
		// this.sfxVolumeSlider.setSnapToTicks(true);
		this.musicVolumeSlider.setBackground(Color.BLACK);
		this.sfxVolumeSlider.setBackground(Color.BLACK);
		this.musicVolumeSlider.setBounds(slider1X(), slider1Y(), slider1W(), slider1H());
		this.sfxVolumeSlider.setBounds(slider2X(), slider2Y(), slider2W(), slider2H());
		this.add(musicVolumeSlider);
		this.add(sfxVolumeSlider);
		MusEvent e1 = new MusEvent();
		musicVolumeSlider.addChangeListener(e1);
		SFXEvent e2 = new SFXEvent();
		sfxVolumeSlider.addChangeListener(e2);
		// WASD Mode button :
		this.wasdMode = new TextButton("WASD MODE");
		this.wasdMode.setBounds(bX(), bY(), Constants.WASD_B_WIDTH, Constants.WASD_B_HEIGHT);
		this.add(this.wasdMode);
		wasdMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Globals.WASD_MODE()) {
					Constants.CUSTOM_KEYS.WASD_MODE(false);
					wasdInfo.setText("Status : OFF");
				} else {
					Constants.CUSTOM_KEYS.WASD_MODE(true);
					wasdInfo.setText("Status : ON");
				}
				wasdInfo.repaint();
				repaint();
			}
		});
		// Text labels :
		this.textFont = new Font("Tahoma", Font.PLAIN, 22);
		this.textColor = new Color(148, 148, 148);
		this.musicVolInfo = new JLabel("Music Volume : " + String.valueOf(Globals.MUSIC_VOLUME()));
		this.musicVolInfo.setBounds(slider1X(), text1Y(), slider1W(), 30);
		this.musicVolInfo.setFont(this.textFont);
		this.musicVolInfo.setForeground(this.textColor);
		this.add(this.musicVolInfo);
		this.sfxVolInfo = new JLabel("SFX Volume : " + String.valueOf(Globals.SFX_VOLUME()));
		this.sfxVolInfo.setBounds(slider2X(), text2Y(), slider2W(), 30);
		this.sfxVolInfo.setFont(this.textFont);
		this.sfxVolInfo.setForeground(this.textColor);
		this.add(this.sfxVolInfo);
		String status = (Globals.WASD_MODE())? "ON" : "OFF";
		this.wasdInfo = new JLabel("Status : " + status);
		this.wasdInfo.setBounds(bX(), text3Y(), Constants.WASD_B_WIDTH, 30);
		this.wasdInfo.setFont(this.textFont);
		this.wasdInfo.setForeground(this.textColor);
		this.add(this.wasdInfo);
		// Repaint :
		this.invalidate();
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(logo, Globals.W_WIDTH() / 2 - Constants.TITLE_WIDTH / 2, 
					Globals.W_HEIGHT() / 8 - Constants.TITLE_HEIGHT / 8, null);
	}

	private final class MusEvent implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			int value = musicVolumeSlider.getValue();
			musicVolInfo.setText("Music Volume : " + String.valueOf(value));
			setSoundVolume(value, true);
			Globals.setMUSIC_VOLUME(value);
			musicVolInfo.repaint();
			repaint();
		}

	}

	private final class SFXEvent implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			int value = sfxVolumeSlider.getValue();
			sfxVolInfo.setText("SFX Volume : " + String.valueOf(value));
			setSoundVolume(value, false);
			Globals.setSFX_VOLUME(value);
			sfxVolInfo.repaint();
			repaint();
		}

	}

	public final void setSoundVolume(int value, boolean isMusic) {
		float volume = -80.0f;
		if (value==0) volume = -80.0f; // mute
		else if (value==100) volume = 0.0f; // maximum volume (cliping above 0 db)
		else volume = -20.0f + (float) value / 10 * 2.0f; // slider value
		if (isMusic) {
			Music.setMusicVolume(volume);
			Constants.MENU_MUSIC.changeGain(volume);
			if (Globals.MAINGAME().getView()!=null) 
				Constants.GAME_MUSIC.changeGain(volume);
		} else {
			SFX.setMusicVolume(volume);
		}
	}

	private static final int slider1X() {
		return Globals.W_WIDTH() / 2 - (Globals.W_WIDTH() / 4) / 2;
	}

	private static final int slider1Y() {
		return (Globals.W_HEIGHT() / 2) - ((Globals.W_HEIGHT() / 12) / 2);
	}

	private static final int slider1W() {
		return Globals.W_WIDTH() / 4;
	}

	private static final int slider1H() {
		return Globals.W_HEIGHT() / 32;
	}
	
	private static final int slider2X() {
		return Globals.W_WIDTH() / 2 - (Globals.W_WIDTH() / 4) / 2;
	}

	private static final int slider2Y() { 
		return (Globals.W_HEIGHT() / 2 - (Globals.W_HEIGHT() / 12) / 2) + Globals.W_HEIGHT() / 6;
	}

	private static final int slider2W() {
		return Globals.W_WIDTH() / 4;
	}

	private static final int slider2H() {
		return Globals.W_HEIGHT() / 32;
	}

	private static final int bX() {
		return Globals.W_WIDTH()/2 - Constants.WASD_B_WIDTH/2;
	}

	private static final int bY(){
		return slider2Y() + Globals.W_WIDTH() / 12;
	}

	private static final int text1Y() {
		return slider1Y() - Globals.W_HEIGHT() / 16;
	}

	private static final int text2Y() {
		return slider2Y() - Globals.W_HEIGHT() / 16;
	}

	private static final int text3Y() {
		return bY() + Globals.W_HEIGHT() / 12;
	}

}