package client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Vladimir on 06/02/18.
 **/

public class GameDev {
    static int width = 800;
    static int height = 600;
    static Tank tank = new Tank(100, 100, 90);
    public static ArrayList<Bullet> bullets = new ArrayList<>();
    public static ArrayList<Tank> otherTanks = new ArrayList<>();
    public static GameDevClient devClient;


    public static void main(String[] args) throws IOException, InterruptedException {
        devClient = new GameDevClient(InetAddress.getLocalHost(), 3345);
        FileLoader imageLoader = new FileLoader();
        MainFrame mainFrame = new MainFrame("Tanks");
        mainFrame.setVisible(true);
    }
}


