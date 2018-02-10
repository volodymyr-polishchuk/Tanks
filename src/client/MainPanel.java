package client;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Vladimir on 06/02/18.
 **/
public class MainPanel extends JPanel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < (super.getWidth() / FileLoader.FON.getWidth()) + 1; i++) {
            for (int j = 0; j < (super.getHeight() / FileLoader.FON.getHeight()) + 1; j++) {
                g.drawImage(FileLoader.FON, i * FileLoader.FON.getHeight(), j * FileLoader.FON.getHeight(), null);
            }
        }
//        GameDev.tank.draw(g);
        for (Tank tank : GameDev.otherTanks) {
            tank.draw(g);
        }
        for (Bullet bullet : GameDev.bullets) {
            bullet.draw(g);
        }
    }
}