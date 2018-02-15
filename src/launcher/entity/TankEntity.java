package launcher.entity;

import client.Tank;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by Vladimir on 14/02/18.
 **/
public class TankEntity implements Entity {

    private String name;
    private double xPosition;
    private double yPosition;
    private double rotateBody = 0.0;
    private double rotateHeader = 0.0;
    private double health = 1;
    private int cool_down_count = (int) COOL_DOWN;

    private int move = 0;
    private int nx, ny;
    private int dBody = 0;
    private int dHeader = 0;
    private boolean moveForce = false;
    private int drawCoolDown = 1;
    private BufferedImage textureBody;
    private BufferedImage textureHeader;
//    private final BufferedImage textureBodyCrash;
//    private final BufferedImage textureHeaderCrash;

    private static final double COOL_DOWN = 50; // Кадрів
    private static final double BODY_ROTATE = 1.0; // Градусів за кадр
    private static final double HEADER_ROTATE = 3.2; // Градусів за кадр
    private static final double MOVE_SPEED = 2.0; // Довжина пройденого шляху за кадр
    private static final double MOVE_FORCE_SPEED = 3.0; // Довжина пройденого шляху за кадр


    @Override
    public String getName() {
        return null;
    }

    @Override
    public Point getPosition() {
        return null;
    }

    @Override
    public String getData() {
        return null;
    }

    @Override
    public Entity getInstance(String data) {
        return null;
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotateBody), textureBody.getWidth() / 2, textureBody.getHeight() / 2);

        BufferedImage imageBody = new BufferedImage(
                textureBody.getWidth(),
                textureBody.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D d = (Graphics2D) imageBody.getGraphics();
        d.drawImage(textureBody, transform, null);

        g.drawImage(imageBody, (int)xPosition, (int)yPosition, null);

        transform.rotate(Math.toRadians(rotateHeader), textureHeader.getWidth() / 2, textureHeader.getHeight() / 2);

        BufferedImage imageHeader = new BufferedImage(
                textureHeader.getWidth(),
                textureHeader.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        d = (Graphics2D) imageHeader.getGraphics();
        d.drawImage(textureHeader, transform, null);

        g.drawImage(imageHeader, (int)xPosition, (int)yPosition, null);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
