package menu;

import java.awt.event.*;

public class MenuKeys implements KeyListener {

    private MenuPanel menu;

    public MenuKeys(MenuPanel menu) {
        this.menu = menu;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("Key Typed");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed");
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
        System.out.println("Key Released");
    }
    
}