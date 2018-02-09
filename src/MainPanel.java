import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by Vladimir on 06/02/18.
 **/
public class MainPanel extends JPanel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < (super.getWidth() / ImageLoader.FON.getWidth()) + 1; i++) {
            for (int j = 0; j < (super.getHeight() / ImageLoader.FON.getHeight()) + 1; j++) {
                g.drawImage(ImageLoader.FON, i * ImageLoader.FON.getHeight(), j * ImageLoader.FON.getHeight(), null);
            }
        }
        GameDev.tank.draw(g);
        for (Bullet bullet : GameDev.bullets) {
            bullet.draw(g);
        }
    }
}