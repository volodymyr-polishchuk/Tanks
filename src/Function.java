import java.awt.*;
import java.util.Arrays;

/**
 * Created by Vladimir on 08/02/18.
 **/
public class Function {
    public static boolean pointInRect(Point p1, Point p2, Point p3, Point p4, Point o) {
        double a = (p1.x - o.x) * (p2.y - p1.y) - (p2.x - p1.x) * (p1.y - o.y);
        double b = (p2.x - o.x) * (p3.y - p2.y) - (p3.x - p2.x) * (p2.y - o.y);
        double c = (p3.x - o.x) * (p4.y - p3.y) - (p4.x - p3.x) * (p3.y - o.y);
        double d = (p4.x - o.x) * (p1.y - p4.y) - (p1.x - p4.x) * (p4.y - o.y);
        return (a >= 0 && b >= 0 && c >= 0 && d >= 0) || (a <= 0 && b <= 0 && c <= 0 && d <= 0);
    }
}
