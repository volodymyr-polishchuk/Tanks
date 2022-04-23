package com.vpolishchuk.tanks.launcher.states;

import com.vpolishchuk.tanks.client.GameDevClient;
import com.vpolishchuk.tanks.launcher.Launcher;
import com.vpolishchuk.tanks.launcher.ResourceLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by Vladimir on 11/02/18.
 **/
public class PauseState implements GameInnerState {
    private String[] items = new String[] {"Продовжити", "Налаштування", "Головне меню"};
    private static final int RETURN = 0, SETTING = 1, EXIT = 2;
    private int nowItem = 0;
    private GameState ownerState;
    private GameDevClient devClient;

    public PauseState(GameState ownerState) {
        this.ownerState = ownerState;
    }

    public PauseState(MultiPlayerState multiPlayerState, GameDevClient devClient) {
        ownerState = multiPlayerState;
        this.devClient = devClient;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        ownerState.draw(g);
        Color color = g.getColor();
        Font font = g.getFont();
        int w = Launcher.GAME_WINDOW.getWidth();
        int h = Launcher.GAME_WINDOW.getHeight();

        g.setColor(new Color(122, 122, 122, 120));
        g.fillRect(0, 0, w, h);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier New", Font.PLAIN, 16));
        g.drawString("Hello, " + Launcher.NICKNAME, 20, 20);
        g.setFont(new Font("Courier New", Font.BOLD, 24));
        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            g.drawString(item,
                    (w - g.getFontMetrics().stringWidth(item)) / 2,
                    ((h - g.getFontMetrics().getHeight() * items.length) / 2) + (i * 50));
        }
        g.drawImage(ResourceLoader.MENU_CHECK_ICON,
                (w - 300) / 2,
                ((h - g.getFontMetrics().getHeight() * items.length - 60) / 2) + (nowItem * 50),
                null);
        g.setColor(color);
        g.setFont(font);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: nowItem = nowItem > 0 ? nowItem - 1: items.length - 1; break;
            case KeyEvent.VK_DOWN: nowItem = nowItem < items.length - 1 ? nowItem + 1 : 0; break;
            case KeyEvent.VK_ESCAPE: Launcher.GAME_WINDOW.setGameState(ownerState); break;
            case KeyEvent.VK_ENTER:
                switch (nowItem) {
                    case RETURN: Launcher.GAME_WINDOW.setGameState(ownerState); break;
                    case SETTING: break;
                    case EXIT:
                        if (devClient != null) devClient.disconnect();
                        Launcher.GAME_WINDOW.setGameState(new MenuState());
                        break;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClick(MouseEvent e) {

    }

    @Override
    public GameState getOwner() {
        return ownerState;
    }
}
