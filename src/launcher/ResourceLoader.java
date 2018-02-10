package launcher;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * Created by Vladimir on 10/02/18.
 **/
public class ResourceLoader {
    public static BufferedImage MENU_CHECK_ICON;
    public static BufferedImage MENU_LOGO;
    public static BufferedImage TANK_BODY;
    public static BufferedImage TANK_HEADER;
    public static BufferedImage FON;
    public static BufferedImage BULLET;
    public static BufferedImage BOOM;
    public static Clip BOOM_AUDIO;
    public static Clip SHOT_AUDIO;

    public ResourceLoader() throws IOException {
        MENU_CHECK_ICON = ImageIO.read(ClassLoader.class.getResourceAsStream("/resource/icons/menu_check_icon.png"));
        MENU_LOGO = ImageIO.read(ClassLoader.class.getResourceAsStream("/resource/icons/menu_logo.png"));
        TANK_BODY = ImageIO.read(getClass().getResourceAsStream("/resource/tankBody.png"));
        TANK_HEADER = ImageIO.read(getClass().getResourceAsStream("/resource/tankHeader.png"));
        FON = ImageIO.read(getClass().getResourceAsStream("/resource/fon.png"));
        BULLET = ImageIO.read(getClass().getResourceAsStream("/resource/bullet.png"));
        BOOM = ImageIO.read(getClass().getResourceAsStream("/resource/boom.png"));
        try {
            BOOM_AUDIO = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    new BufferedInputStream(ClassLoader.class.getResourceAsStream("/resource/audio/two.wav"))
            );
            BOOM_AUDIO.open(inputStream);

            SHOT_AUDIO = AudioSystem.getClip();
            inputStream = AudioSystem.getAudioInputStream(
                    new BufferedInputStream(ClassLoader.class.getResourceAsStream("/resource/audio/one.wav"))
            );
            SHOT_AUDIO.open(inputStream);

        } catch (LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
}
