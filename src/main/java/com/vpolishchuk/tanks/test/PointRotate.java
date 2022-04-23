package com.vpolishchuk.tanks.test;

import java.awt.*;

public class PointRotate {

    public static Point rotatePoint(final Point pt, Point center, double angleDeg) {
        double cosAngle = Math.cos(Math.toRadians(angleDeg));
        double sinAngle = Math.sin(Math.toRadians(angleDeg));
        pt.setLocation(
                center.getX() + ((pt.getX() - center.getX()) * cosAngle - (pt.getY() - center.getY()) * sinAngle),
                center.getY() + ((pt.getX() - center.getX()) * sinAngle + (pt.getY() - center.getY()) * cosAngle)
        );
        return pt;
    }

}
