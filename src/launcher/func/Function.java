package launcher.func;

import java.awt.*;

/**
 * Created by Vladimir on 05/03/18.
 **/
public class Function {
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
