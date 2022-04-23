package com.vpolishchuk.tanks.map;

import java.awt.*;

import static com.vpolishchuk.tanks.launcher.func.Function.rotatePoint;

/**
 * Created by Vladimir on 05/03/18.
 **/
public class HitBox {

    private Polygon hitBox;
    private Point center;

    public HitBox(Polygon hitBox, Point center) {
        this.center = center;
        this.hitBox = hitBox;
    }

    public Polygon getHitBox() {
        return hitBox;
    }

    public void setHitBox(Polygon hitBox) {
        this.hitBox = hitBox;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public Polygon getPolygon(Point position, double rotate) {
        Polygon polygon = new Polygon();
        Point point = null;
        for (int i = 0; i < hitBox.npoints; i++) {
            point = rotatePoint(new Point(hitBox.xpoints[i], hitBox.ypoints[i]), center, rotate);
            polygon.addPoint(position.x + point.x - center.x, position.y + point.y - center.y);
        }
        return polygon;
    }
}
