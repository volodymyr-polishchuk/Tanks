package launcher;

import java.io.IOException;

/**
 * Created by Vladimir on 10/02/18.
 **/
public class Launcher {
    public static MainFrame GAME_WINDOW;
    public static ResourceLoader RESOURCE_LOADER;

    public static void main(String[] args) throws IOException {
        RESOURCE_LOADER = new ResourceLoader();
        GAME_WINDOW = new MainFrame("Tanks");
        GAME_WINDOW.setVisible(true);
    }
}
