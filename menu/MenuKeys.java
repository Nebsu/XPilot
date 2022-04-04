package menu;

import java.awt.event.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;

public class MenuKeys implements KeyListener {

    private Menu menu;

    public MenuKeys(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if (k==KeyEvent.VK_ENTER) {
			try {
                menu.select();
            } catch (IOException | LineUnavailableException e1) {
                e1.printStackTrace();
            }
            return;
		}
		if (k==KeyEvent.VK_UP) {
			menu.currentChoice--;
			if (menu.currentChoice==-1) {
				menu.currentChoice = menu.options.length-1;
			}
		}
		if (k==KeyEvent.VK_DOWN) {
			menu.currentChoice++;
			if (menu.currentChoice==menu.options.length) {
				menu.currentChoice = 0;
			}
		}
        menu.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}