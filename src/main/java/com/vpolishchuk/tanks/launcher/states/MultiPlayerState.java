package com.vpolishchuk.tanks.launcher.states;

import com.vpolishchuk.tanks.client.Bullet;
import com.vpolishchuk.tanks.client.GameDevClient;
import com.vpolishchuk.tanks.client.PlaySound;
import com.vpolishchuk.tanks.client.Tank;
import com.vpolishchuk.tanks.launcher.Launcher;
import com.vpolishchuk.tanks.launcher.ResourceLoader;
import com.vpolishchuk.tanks.server.MultiThreadServer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by Vladimir on 12/02/18.
 **/
public class MultiPlayerState implements GameState {
    public Tank tank = new Tank(Launcher.NICKNAME, 100, 100, 0);
    private LinkedList<Bullet> bullets = new LinkedList<>();
    private LinkedList<Tank> onlineTanks = new LinkedList<>();
    private GameDevClient devClient;

    public MultiPlayerState(InetAddress address, String port) {
        try {
            devClient = new GameDevClient(address, Integer.valueOf(port));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        tank.update();
        try {
            onlineTanks = new LinkedList<>(Arrays.asList(devClient.sendToServerTank(tank)));
            for (Tank othertank : onlineTanks) {
                if (this.tank.equals(othertank)) {
                    this.tank.setHealth(othertank.getHealth());
                }
            }
            bullets = new LinkedList<>(Arrays.asList(devClient.getFromServerBullets()));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics g) {
        for (int i = 0; i < (Launcher.GAME_WINDOW.getWidth() / ResourceLoader.FON.getWidth()) + 1; i++) {
            for (int j = 0; j < (Launcher.GAME_WINDOW.getHeight() / ResourceLoader.FON.getHeight()) + 1; j++) {
                g.drawImage(ResourceLoader.FON, i * ResourceLoader.FON.getWidth(), j * ResourceLoader.FON.getHeight(), null);
            }
        }
        tank.draw(g);
        for (Tank othertank : onlineTanks) {
            if (this.tank.equals(othertank)) continue;
            othertank.draw(g);
        }
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isShiftDown()) {
            tank.moveForce();
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: tank.moveForward(); break;
            case KeyEvent.VK_DOWN: tank.moveBack(); break;
            case KeyEvent.VK_LEFT: tank.turnBodyLeft(); break;
            case KeyEvent.VK_RIGHT: tank.turnBodyRight(); break;
            case KeyEvent.VK_Z: tank.turnHeaderLeft(); break;
            case KeyEvent.VK_C: tank.turnHeaderRight(); break;
            case KeyEvent.VK_SLASH: Launcher.GAME_WINDOW.log.addMessage(String.valueOf(MultiThreadServer.getPort())); break;
            case KeyEvent.VK_SPACE: {
                Bullet bullet = tank.doShot();
                if (bullet == null) break;
                try {
                    devClient.sendToServerBullet(bullet);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                PlaySound.playSound(ResourceLoader.SHOT_AUDIO);
                break;
            }
            case KeyEvent.VK_ESCAPE: Launcher.GAME_WINDOW.setGameState(new PauseState(this, devClient)); break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!e.isShiftDown()) {
            tank.stopForce();
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: case KeyEvent.VK_DOWN: tank.moveStop(); break;
            case KeyEvent.VK_LEFT: case KeyEvent.VK_RIGHT: tank.turnBodyStop(); break;
            case KeyEvent.VK_Z: case KeyEvent.VK_C: tank.turnHeaderStop(); break;
        }
    }

    @Override
    public void mouseClick(MouseEvent e) {

    }
}
