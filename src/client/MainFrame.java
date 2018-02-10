package client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Vladimir on 06/02/18.
 **/
public class MainFrame extends JFrame {
    private static boolean exit = false;

    public MainFrame(String title) throws HeadlessException {
        super(title);
        try {
            setIconImage(ImageIO.read(getClass().getResourceAsStream("/resource/iconlogo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setSize(new Dimension(GameDev.width, GameDev.height));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 1));
        MainPanel mainPanel = new MainPanel();
        add(mainPanel);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isShiftDown()) {
                    GameDev.tank.moveForce();
                }
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP: GameDev.tank.moveForward(); break;
                    case KeyEvent.VK_DOWN: GameDev.tank.moveBack(); break;
                    case KeyEvent.VK_LEFT: GameDev.tank.turnBodyLeft(); break;
                    case KeyEvent.VK_RIGHT: GameDev.tank.turnBodyRight(); break;
                    case KeyEvent.VK_Z: GameDev.tank.turnHeaderLeft(); break;
                    case KeyEvent.VK_C: GameDev.tank.turnHeaderRight(); break;
                    case KeyEvent.VK_SPACE: {
                        Bullet bullet = GameDev.tank.doShot();
                        if (bullet == null) break;
                        GameDev.bullets.add(bullet);
                        PlaySound.playSound(FileLoader.SHOT_AUDIO);
                        break;
                    }
                    case KeyEvent.VK_ESCAPE: exit = true; break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!e.isShiftDown()) {
                    GameDev.tank.stopForce();
                }
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP: case KeyEvent.VK_DOWN: GameDev.tank.moveStop(); break;
                    case KeyEvent.VK_LEFT: case KeyEvent.VK_RIGHT: GameDev.tank.turnBodyStop(); break;
                    case KeyEvent.VK_Z: case KeyEvent.VK_C: GameDev.tank.turnHeaderStop(); break;
                }
            }
        });

        requestFocus();

        Thread thread = new Thread(() -> {
            ArrayList<Integer> integers = new ArrayList<>();
            while (!exit) {
                long l = System.currentTimeMillis();
                GameDev.tank.update();
                try {
                    Tank[] tanks = GameDev.devClient.sendToServer(GameDev.tank);
                    GameDev.otherTanks.clear();
                    for (Tank tank: tanks) {
                        GameDev.otherTanks.add(tank);
                    }

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                ArrayList<Bullet> bullets = GameDev.bullets;
                for (int i = 0; i < bullets.size(); i++) {
                    bullets.get(i).update();
                    if (bullets.get(i).isDie()) {
                        integers.add(i);
                    }
                }

                mainPanel.repaint();

                try {
                    for (Integer integer: integers) {
                        bullets.remove(integer.intValue()).dieSound();
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                integers.clear();
                long l2 = System.currentTimeMillis() - l;
                try {
                    if (30 - l2 > 0)
                        Thread.sleep(30 - l2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.exit(0);
        });

        thread.start();
    }
}


