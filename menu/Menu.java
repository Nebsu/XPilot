package menu;

import sound.Music;

import javax.swing.*;

public final class Menu extends JComponent {

	private final MenuPanel menuPanel;
	private final SettingsPanel settingsPanel;
	private final HelpPanel helpPanel;
	private final Music menuMusic;

	public final MenuPanel getMenuPanel() {return menuPanel;}
	public final SettingsPanel getSettingsPanel() {return settingsPanel;}
	public final HelpPanel getHelpPanel() {return helpPanel;}

	public Menu() {
		super();
		this.menuPanel = new MenuPanel();
		this.settingsPanel = new SettingsPanel();
		this.helpPanel = new HelpPanel();
		// Music :
		String filepath = "ressources/audio/menuMusic.wav";
		this.menuMusic = new Music(filepath);
	}
	
	public final void playMenuMusic() {
		try {
			this.menuMusic.playMusic();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			System.exit(1);
		}
	}

	public final void stopMenuMusic() {
		this.menuMusic.stopMusic();
	}

}