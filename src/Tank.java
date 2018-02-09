import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by Vladimir on 06/02/18.
 **/
public class Tank {
    private double xPosition;
    private double yPosition;
    private double rotateBody = 0.0;
    private double rotateHeader = 0.0;
    private double health = 1;

    private int move = 0;
    private double dBody = 0;
    private double dHeader = 0;
    private boolean moveForce = false;

    private static final double BODY_ROTATE = 1.0;
    private static final double HEADER_ROTATE = 3.2;
    private static final double MOVE_SPEED = 2.0;
    private static final double MOVE_FORCE_SPEED = 3.0;

    public Tank(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public void doHit() {
        health = health - 0.05;
    }

    public void moveForward() {move = 1;}

    public void moveBack() {move = -1;}

    public void moveStop() {move = 0;}

    public void moveForce() {moveForce = true;}

    public void stopForce() {moveForce = false;}

    public void turnBodyLeft() {dBody = -1;}

    public void turnBodyRight() {dBody = 1;}

    public void turnBodyStop() {dBody = 0;}

    public void turnHeaderLeft() {dHeader = -1;}

    public void turnHeaderRight() {dHeader = 1;}

    public void turnHeaderStop() {dHeader = 0;}

    public Bullet doShot() {
        return new Bullet(
                (int) (xPosition + Math.cos(Math.toRadians(rotateHeader + rotateBody)) * 30 + ImageLoader.TANK_HEADER.getWidth() / 2),
                (int) (yPosition + Math.sin(Math.toRadians(rotateHeader + rotateBody)) * 30 + ImageLoader.TANK_HEADER.getHeight() / 2),
            rotateBody + rotateHeader);
    }

    public void update() {
        if (moveForce) {
            xPosition = xPosition + MOVE_FORCE_SPEED * move * Math.cos(Math.toRadians(rotateBody));
            yPosition = yPosition + MOVE_FORCE_SPEED * move * Math.sin(Math.toRadians(rotateBody));
        } else {
            xPosition = xPosition + MOVE_SPEED * move * Math.cos(Math.toRadians(rotateBody));
            yPosition = yPosition + MOVE_SPEED * move * Math.sin(Math.toRadians(rotateBody));
        }
        rotateBody = rotateBody + dBody * BODY_ROTATE;
        if (dHeader != 0)
            rotateHeader = rotateHeader + dBody * BODY_ROTATE + dHeader * HEADER_ROTATE;
    }

    public void draw(Graphics g) {
        g.drawRect((int)xPosition, (int)(yPosition - 10), ImageLoader.TANK_BODY.getWidth(), 5);
        g.setColor(Color.GREEN);
        g.fillRect((int)xPosition + 1, (int) (yPosition - 10 + 1), (int) ((ImageLoader.TANK_BODY.getWidth() - 1) * health), 4);

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotateBody), ImageLoader.TANK_BODY.getWidth() / 2, ImageLoader.TANK_BODY.getHeight() / 2);

        BufferedImage imageBody = new BufferedImage(
                ImageLoader.TANK_BODY.getWidth(),
                ImageLoader.TANK_BODY.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D d = (Graphics2D) imageBody.getGraphics();
        d.drawImage(ImageLoader.TANK_BODY, transform, null);

        g.drawImage(imageBody, (int)xPosition, (int)yPosition, null);

        transform.rotate(Math.toRadians(rotateHeader), ImageLoader.TANK_HEADER.getWidth() / 2, ImageLoader.TANK_HEADER.getHeight() / 2);

        BufferedImage imageHeader = new BufferedImage(
                ImageLoader.TANK_HEADER.getWidth(),
                ImageLoader.TANK_HEADER.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        d = (Graphics2D) imageHeader.getGraphics();
        d.drawImage(ImageLoader.TANK_HEADER, transform, null);

        g.drawImage(imageHeader, (int)xPosition, (int)yPosition, null);
    }

}
