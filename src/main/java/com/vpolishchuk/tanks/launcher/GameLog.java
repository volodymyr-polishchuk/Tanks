package com.vpolishchuk.tanks.launcher;

import com.vpolishchuk.tanks.launcher.states.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Vladimir on 11/02/18.
 **/
public class GameLog implements GameState {
    private LinkedList<String> list = new LinkedList<>();
    private int showTime = 0;
    private int showCount = 5;

    public void addMessage(String message) {
        list.addFirst(message);
        showCount = 1;
        showTime = 100;
    }

    public void showAllMessage() {
        showCount = list.size() < 10 ? list.size() : 10;
    }

    @Override
    public void update() {
        showTime = showTime > 0 ? showTime - 1 : 0;
    }

    @Override
    public void draw(Graphics g) {
        if (showTime <= 0) return;
        Iterator<String> iterator = list.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            String line = iterator.next();
            Font font = g.getFont();
            g.setFont(new Font(font.getName(), font.getStyle(), 18));
            g.drawString(line, 20, Launcher.GAME_WINDOW.getHeight() - 100 - count * 30);
            g.setFont(font);
            count++;
            if (count >= showCount) return;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClick(MouseEvent e) {

    }
}
