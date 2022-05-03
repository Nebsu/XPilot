package menu;

public final class TextButton extends MyButton {

    private final String text;

    public TextButton(String text) {
        this.text = text;
        this.setText(text);
    }

    public final String getText() {return text;}

}