package menu;

import java.awt.event.*;

public final class MenuKeys implements KeyListener {

    private final Menu menu;

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
			menu.select();
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