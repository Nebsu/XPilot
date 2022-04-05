package menu;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public final class IconButton extends JComponent {

    private final Image iconImage;
    private final Icon icon;
    public final JButton button;

    public final Icon getIcon() {return icon;}

    public IconButton(String filepath) {
        this.iconImage = setImageFile(filepath);
        this.icon = new ImageIcon(this.iconImage);
        this.button = new JButton(this.icon);
    }
    
    private final Image setImageFile(String imagePath) {
        try {
            return ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
            System.exit(1);
        }
        return null;
    }

}