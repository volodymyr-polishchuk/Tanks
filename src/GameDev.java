import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Vladimir on 06/02/18.
 **/

public class GameDev {
    static int width = 800;
    static int height = 600;
    static Tank tank = new Tank(0, 0);
    public static ArrayList<Bullet> bullets = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ImageLoader imageLoader = new ImageLoader();
        MainFrame mainFrame = new MainFrame("GameDev2");
        mainFrame.setVisible(true);
    }
}


