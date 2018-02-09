import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;

/**
 * Created by Vladimir on 07/02/18.
 **/
public class ImageLoader {
    public static BufferedImage TANK_BODY;
    public static BufferedImage TANK_HEADER;
    public static BufferedImage FON;
    public static BufferedImage BULLET;
    public static BufferedImage BOOM;

    public ImageLoader() throws IOException {
        TANK_BODY = ImageIO.read(getClass().getResourceAsStream("/resource/tankBody.png"));
        TANK_HEADER = ImageIO.read(getClass().getResourceAsStream("/resource/tankHeader.png"));
        FON = ImageIO.read(getClass().getResourceAsStream("/resource/fon.png"));
        BULLET = ImageIO.read(getClass().getResourceAsStream("/resource/bullet.png"));
        BOOM = ImageIO.read(getClass().getResourceAsStream("/resource/boom.png"));
    }
}
