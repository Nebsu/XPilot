package menu;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.*;

public abstract class MyButton extends JButton {

    private boolean over;
    private Color color;
    private Color colorOver;
    private Color borderColor;
    private int radius;

    public final boolean isOver() {return over;}
    public final Color getColor() {return color;}
    public final Color getColorOver() {return colorOver;}
    public final Color getBorderColor() {return borderColor;}
    public final int getRadius() {return radius;}

    public final void setOver(boolean over) {this.over = over;}
    public final void setColor(Color color) {this.color = color;}
    public final void setColorOver(Color c) {this.colorOver = c;}
    public final void setBorderColor(Color color) {this.borderColor = color;}
    public final void setRadius(int radius) {this.radius = radius;}


    public MyButton() {
        this.radius = 0;
        // Init color :
        this.color = Color.WHITE;
        this.colorOver = new Color(240, 55, 55);
        this.borderColor = new Color(255, 0, 0);
        this.setContentAreaFilled(false);
        // Add event mouse :
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setBackground(colorOver);
                over = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setBackground(color);
                over = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (over) {
                    setBackground(colorOver);
                } else {
                    setBackground(color); 
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Paint border :
        g2.setColor(this.borderColor);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(getBackground());
        g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, radius, radius);
        super.paintComponent(g);
    }

}