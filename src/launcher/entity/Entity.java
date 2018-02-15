package launcher.entity;

import client.Tank;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Created by Vladimir on 10/02/18.
 **/
public interface Entity extends MouseListener, KeyListener{

    String getName();

    Point getPosition();

    double getRotate();

    String getData();

    Entity getInstance(String data);

    boolean intersect(Point point);

    boolean intersect(Polygon polygon);

    void update();

    void draw(Graphics g);

}
