package menu;

import javax.swing.JButton;
import javax.swing.JComponent;

public final class TextButton extends JComponent {

    private final String text;
    public final JButton button;

    public final String getText() {return text;}

    public TextButton(String text) {
        this.text = text;
        this.button = new JButton(text);
    }

}