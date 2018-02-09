import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Vladimir on 06/02/18.
 **/

public class GameDev {
    static int width = 800;
    static int height = 600;
    static Tank tank = new Tank(100, 100, 90);
    public static ArrayList<Bullet> bullets = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        FileLoader imageLoader = new FileLoader();
        MainFrame mainFrame = new MainFrame("Tanks");
        mainFrame.setVisible(true);
    }
}


