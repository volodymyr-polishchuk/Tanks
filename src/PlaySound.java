import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Created by Vladimir on 09/02/18.
 **/
public class PlaySound {
    public static synchronized void playSound(final String url) {
        new Thread(() -> {
            try {
                System.out.println(0);
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                        ClassLoader.class.getResourceAsStream(url));
                clip.open(inputStream);
                clip.start();
                System.out.println(1);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }
}
