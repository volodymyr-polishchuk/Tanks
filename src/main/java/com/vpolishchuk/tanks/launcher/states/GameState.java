package com.vpolishchuk.tanks.launcher.states;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by Vladimir on 10/02/18.
 **/
public interface GameState {

    void update();

    void draw(Graphics g);

    void keyTyped(KeyEvent e);

    void keyPressed(KeyEvent e);

    void keyReleased(KeyEvent e);

    void mouseClick(MouseEvent e);
}
