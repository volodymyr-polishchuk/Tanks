package map;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by Vladimir on 05/03/18.
 **/
public class Texture2D {

    private Image texture;
    private Point center;

    public Texture2D(Image texture, Point center) {
        this.texture = texture;
        this.center = center;
    }

    public Image getTexture() {
        return texture;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public void draw(Graphics g, Point position, double rotate) {
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotate), center.getX(), center.getY());
        BufferedImage image = new BufferedImage(texture.getWidth(null), texture.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D d = (Graphics2D) image.getGraphics();
        d.drawImage(texture, transform, null);
        g.drawImage(image, position.x - center.x, position.y - center.y, null);
    }

    public BufferedImage getDrawableImage(double rotate) {
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotate), center.getX(), center.getY());
        BufferedImage image = new BufferedImage(texture.getWidth(null), texture.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D d = (Graphics2D) image.getGraphics();
        d.drawImage(texture, transform, null);
        return image;
    }

}
