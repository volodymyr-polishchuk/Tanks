import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.Buffer;

/**
 * Created by Vladimir on 07/02/18.
 **/
public class FileLoader {
    public static BufferedImage TANK_BODY;
    public static BufferedImage TANK_HEADER;
    public static BufferedImage FON;
    public static BufferedImage BULLET;
    public static BufferedImage BOOM;
    public static Clip BOOM_AUDIO;
    public static Clip SHOT_AUDIO;


    public FileLoader() throws IOException {
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
