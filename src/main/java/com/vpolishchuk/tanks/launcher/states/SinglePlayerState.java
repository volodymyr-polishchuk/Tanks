package com.vpolishchuk.tanks.launcher.states;

import com.vpolishchuk.tanks.client.Bullet;
import com.vpolishchuk.tanks.client.PlaySound;
import com.vpolishchuk.tanks.launcher.Launcher;
import com.vpolishchuk.tanks.launcher.ResourceLoader;
import com.vpolishchuk.tanks.launcher.entity.EnemyEntity;
import com.vpolishchuk.tanks.launcher.entity.PlayerEntity;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

/**
 * Created by Vladimir on 10/02/18.
 **/
public class SinglePlayerState implements GameState {
    public PlayerEntity tank = new PlayerEntity(Launcher.NICKNAME, 100, 100, 0, 0, this);

    public final LinkedList<Bullet> bullets = new LinkedList<>();
    private LinkedList<Bullet> tBullets = new LinkedList<>();
    public final LinkedList<EnemyEntity> tanks = new LinkedList<>();

    public SinglePlayerState() {
//        tanks.add(new EnemyEntity("1", 300, 300, 0, 0, this));
//        tanks.add(new EnemyEntity("2", 500, 300, 180, 0, this));
    }

    @Override
    public void update() {
        tank.update();
        tBullets.clear();
        tanks.forEach(EnemyEntity::update);

        synchronized (bullets) {
            bullets.forEach((bullet) -> {
                bullet.update();
                if (tank.intersect(new Point(bullet.getX(), bullet.getY()))) {
                    tank.doHit();
                    bullet.die();
                }
                for (EnemyEntity tank : tanks) {
                    if (tank.intersect(new Point(bullet.getX(), bullet.getY()))) {
                        tank.doHit();
                        bullet.die();
                    }
                }
                if (bullet.isDie()) tBullets.add(bullet);
            });
            if (tBullets.size() > 0) PlaySound.playSound(ResourceLoader.BOOM_AUDIO);
            bullets.removeAll(tBullets);
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

        for (EnemyEntity tank : tanks) tank.draw(g);
        for (Bullet bullet : bullets) bullet.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        tank.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_N) {
            tanks.add(new EnemyEntity("1", Math.random() * 600, Math.random() * 600, Math.random() * 360, Math.random() * 360, this));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        tank.keyReleased(e);
    }

    @Override
    public void mouseClick(MouseEvent e) {

    }
}
