package com.vpolishchuk.tanks.launcher;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Vladimir on 10/02/18.
 **/
public class Launcher {
    public static MainFrame GAME_WINDOW;
    public static ResourceLoader RESOURCE_LOADER;
    public static String NICKNAME = "Player_" + new Random().nextInt(10000);

    public static void main(String[] args) {
        try {
            IntroFrame introFrame = new IntroFrame();
            introFrame.setVisible(true);
            RESOURCE_LOADER = new ResourceLoader();
            GAME_WINDOW = new MainFrame("Tanks");
            introFrame.dispose();
            GAME_WINDOW.setVisible(true);
        } catch (HeadlessException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Exception: \n\r" + e.getMessage(), "Java Exception", JOptionPane.ERROR_MESSAGE);
        }
    }
}
