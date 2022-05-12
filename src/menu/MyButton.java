package src.menu;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.*;

public abstract class MyButton extends JButton {

    protected boolean over;
    protected Color color;
    protected Color colorOver;
    protected Color borderColor;
    protected int radius;

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