import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by Vladimir on 07/02/18.
 **/
public class Bullet {
    private double x;
    private double y;
    private double angle = 0.0;
    private int dieCount = 0;
    private static final double MOVE_SPEED = 5;
    private static final int DIE_TIME = 100;

    public Bullet(int x, int y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public boolean isDie() {
        return dieCount > DIE_TIME;
    }

    public double getMoveSpeed() {
        return MOVE_SPEED;
    }

    public void update() {
        x = x + MOVE_SPEED * Math.cos(Math.toRadians(angle));
        y = y + MOVE_SPEED * Math.sin(Math.toRadians(angle));
    }

    public void dieSound() {
        PlaySound.playSound("/resource/audio/one.wav");
    }

    public void draw(Graphics g) {
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), ImageLoader.BULLET.getWidth() / 2, ImageLoader.BULLET.getHeight() / 2);
        BufferedImage image = new BufferedImage(ImageLoader.BULLET.getWidth(), ImageLoader.BULLET.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        graphics2D.drawImage(ImageLoader.BULLET, transform, null);
        g.drawImage(image, (int)x, (int)y, null);
        dieCount++;
        if (isDie()) {
            g.drawImage(ImageLoader.BOOM, ((int) (x - ImageLoader.BOOM.getWidth() / 2)), ((int) (y - ImageLoader.BOOM.getHeight() / 2)), null);
        }
    }
}
