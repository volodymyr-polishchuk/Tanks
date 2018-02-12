package client;

import launcher.ResourceLoader;

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
    private static final int DIE_TIME = 200;
    private BufferedImage bullet_image = ResourceLoader.BULLET;
    private BufferedImage boom_image = ResourceLoader.BOOM;

    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }

    public Bullet(int x, int y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public boolean isDie() {
        return dieCount >= DIE_TIME;
    }

    public void die() {dieCount = DIE_TIME;}

    public double getMoveSpeed() {
        return MOVE_SPEED;
    }

    public void update() {
        x = x + MOVE_SPEED * Math.cos(Math.toRadians(angle));
        y = y + MOVE_SPEED * Math.sin(Math.toRadians(angle));
    }

    public void dieSound() {
        PlaySound.playSound(ResourceLoader.BOOM_AUDIO);
    }

    public void draw(Graphics g) {
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), bullet_image.getWidth() / 2, bullet_image.getHeight() / 2);
        BufferedImage image = new BufferedImage(bullet_image.getWidth(), bullet_image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        graphics2D.drawImage(bullet_image, transform, null);
        g.drawImage(image, (int)x, (int)y, null);
        dieCount++;
        if (isDie()) {
            g.drawImage(boom_image, ((int) (x - boom_image.getWidth() / 2)), ((int) (y - boom_image.getHeight() / 2)), null);
        }
    }
}
