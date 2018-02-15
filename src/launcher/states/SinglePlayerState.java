package launcher.states;

import client.Bullet;
import client.GameDevClient;
import client.PlaySound;
import client.Tank;
import launcher.Launcher;
import launcher.ResourceLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

/**
 * Created by Vladimir on 10/02/18.
 **/
public class SinglePlayerState implements GameState {
    private Tank tank = new Tank(Launcher.NICKNAME, 100, 100, 0);
    public LinkedList<Bullet> bullets = new LinkedList<>();
    private LinkedList<Bullet> tBullets = new LinkedList<>();
    private LinkedList<Tank> tanks = new LinkedList<>();
    private GameDevClient devClient;

    public SinglePlayerState() {
    }

    @Override
    public void update() {
        tank.update();
        tBullets.clear();

        bullets.forEach((bullet) -> {
            bullet.update();
            if (tank.intersect(new Point(bullet.getX(), bullet.getY()))) {
                tank.doHit();
                bullet.die();
            }
            for (Tank tank : tanks) {
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

    @Override
    public void draw(Graphics g) {
        for (int i = 0; i < (Launcher.GAME_WINDOW.getWidth() / ResourceLoader.FON.getWidth()) + 1; i++) {
            for (int j = 0; j < (Launcher.GAME_WINDOW.getHeight() / ResourceLoader.FON.getHeight()) + 1; j++) {
                g.drawImage(ResourceLoader.FON, i * ResourceLoader.FON.getWidth(), j * ResourceLoader.FON.getHeight(), null);
            }
        }
        tank.draw(g);
        for (Tank tank : tanks) {
            tank.draw(g);
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
            case KeyEvent.VK_SPACE: {
                Bullet bullet = tank.doShot();
                if (bullet == null) break;
                bullets.add(bullet);
                PlaySound.playSound(ResourceLoader.SHOT_AUDIO);
                break;
            }
            case KeyEvent.VK_ESCAPE: Launcher.GAME_WINDOW.setGameState(new PauseState(this)); break;
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
