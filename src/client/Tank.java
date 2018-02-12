package client;

import launcher.Launcher;
import launcher.ResourceLoader;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by Vladimir on 06/02/18.
 **/
public class Tank {
    private String name;
    private double xPosition;
    private double yPosition;
    private double rotateBody = 0.0;
    private double rotateHeader = 0.0;
    private double health = 1;
    private int cool_down_count = (int) COOL_DOWN;

    private int move = 0;
    private double nx, ny;
    private double dBody = 0;
    private double dHeader = 0;
    private boolean moveForce = false;
    private int drawCoolDown = 1;
    private BufferedImage textureBody;
    private BufferedImage textureHeader;
    private final BufferedImage textureBodyCrash;
    private final BufferedImage textureHeaderCrash;

    private static final double COOL_DOWN = 50; // Кадрів
    private static final double BODY_ROTATE = 1.0; // Градусів за кадр
    private static final double HEADER_ROTATE = 3.2; // Градусів за кадр
    private static final double MOVE_SPEED = 2.0; // Довжина пройденого шляху за кадр
    private static final double MOVE_FORCE_SPEED = 3.0; // Довжина пройденого шляху за кадр

    private Tank() {
        textureBody = ResourceLoader.TANK_BODY;
        textureHeader = ResourceLoader.TANK_HEADER;
        textureBodyCrash = ResourceLoader.TANK_BODY_CRASH;
        textureHeaderCrash = ResourceLoader.TANK_HEADER_CRASH;
    }

    public Tank(String name, double xPosition, double yPosition, double rotateBody, BufferedImage textureBody, BufferedImage textureHeader) {
        this();
        this.name = name;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.rotateBody = rotateBody;
        this.textureBody = textureBody;
        this.textureHeader = textureHeader;
    }

    public Tank(int xPosition, int yPosition, double angle) {
        this();
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.rotateBody = angle;
        this.name = String.valueOf((int)(Math.random() * 1_000_000));
    }

    public Tank(String name, double xPosition, double yPosition, double rotateBody) {
        this();
        this.name = name;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.rotateBody = rotateBody;
    }

    //  name / xPosition ; yPosition ; rotateBody ; rotateHeader ; health
    public String getData() {
        String line = "";
        line += name + "/";
        line += String.valueOf(xPosition) + ";";
        line += String.valueOf(yPosition) + ";";
        line += String.valueOf(rotateBody) + ";";
        line += String.valueOf(rotateHeader) + ";";
        line += String.valueOf(cool_down_count) + ";";
        line += String.valueOf(health);
        return line;
    }

    //  name / xPosition ; yPosition ; rotateBody ; rotateHeader ; health
    public static Tank getInstantsByData(String data) {
        String[] lines = data.split("/");
        String name = lines[0];
        lines = lines[1].split(";");
        if (lines.length != 6) throw new IllegalArgumentException("input data is wrong -> " + data);
        Tank tank = new Tank();
        tank.name = name;
        tank.xPosition = Double.valueOf(lines[0]);
        tank.yPosition = Double.valueOf(lines[1]);
        tank.rotateBody = Double.valueOf(lines[2]);
        tank.rotateHeader = Double.valueOf(lines[3]);
        tank.cool_down_count = Integer.valueOf(lines[4]);
        tank.health = Double.valueOf(lines[5]);
        return tank;
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


    public Polygon getPolygon() {
        return getPolygon(textureBody.getWidth(), textureBody.getHeight(), 46, 30);
    }

    /**
     * @param rw - реальна ширина текстури навколо картинки
     * @param rh - реальна висота текстури навколо картинки
     * @param hw - ширина хітбокса
     * @param hh - висота хітбокса
     * @return Polygon - який позначає хітбокс
     */
    public Polygon getPolygon(int rw, int rh, int hw, int hh) {
        double cx = xPosition + rw / 2;
        double cy = yPosition + rh / 2;
        double tx = cx + (hw / 2) * Math.cos(Math.toRadians(rotateBody));
        double ty = cy + (hw / 2) * Math.sin(Math.toRadians(rotateBody));
        double x1 = tx + (hh / 2) * Math.cos(Math.toRadians(rotateBody + 90));
        double y1 = ty + (hh / 2) * Math.sin(Math.toRadians(rotateBody + 90));
        double x2 = x1 + (hw) * Math.cos(Math.toRadians(rotateBody + 180));
        double y2 = y1 + (hw) * Math.sin(Math.toRadians(rotateBody + 180));
        double x3 = x2 + (hh) * Math.cos(Math.toRadians(rotateBody + 270));
        double y3 = y2 + (hh) * Math.sin(Math.toRadians(rotateBody + 270));
        double x4 = x3 + (hw) * Math.cos(Math.toRadians(rotateBody + 360));
        double y4 = y3 + (hw) * Math.sin(Math.toRadians(rotateBody + 360));
        return new Polygon(new int[]{(int) x1, (int) x2, (int) x3, (int) x4},
                new int[]{(int) y1, (int) y2, (int) y3, (int) y4}, 4);
    }

    public boolean intersect(Point point) {
        return getPolygon(textureBody.getWidth(), textureBody.getHeight(), 46, 30).contains(point);
    }

    public Bullet doShot() {
        if (!(cool_down_count >= COOL_DOWN)) return null;
        cool_down_count = 0;
        return new Bullet(
                (int) (xPosition + Math.cos(Math.toRadians(rotateHeader + rotateBody)) * 30 + textureHeader.getWidth() / 2),
                (int) (yPosition + Math.sin(Math.toRadians(rotateHeader + rotateBody)) * 30 + textureHeader.getHeight() / 2),
            rotateBody + rotateHeader);
    }

    public void update() {
        if (health <= 0) {
            textureBody = textureBodyCrash;
            textureHeader = textureHeaderCrash;
            return;
        }
        if (moveForce) {
            nx = xPosition + MOVE_FORCE_SPEED * move * Math.cos(Math.toRadians(rotateBody));
            ny = yPosition + MOVE_FORCE_SPEED * move * Math.sin(Math.toRadians(rotateBody));
            if (tryMove(xPosition, yPosition, nx, ny)) {
                xPosition = nx;
                yPosition = ny;
            }
            health -= health > 0.1 ? 0.001 : 0;
        } else {
            nx = xPosition + move * Math.cos(Math.toRadians(rotateBody));
            ny = yPosition + move * Math.sin(Math.toRadians(rotateBody));
            if (tryMove(xPosition, yPosition, nx, ny)) {
                xPosition = nx;
                yPosition = ny;
            }
        }
        if (move == -1) rotateBody = rotateBody - dBody * BODY_ROTATE;
        else rotateBody = rotateBody + dBody * BODY_ROTATE;
        if (dHeader != 0)
            rotateHeader = rotateHeader + dBody * BODY_ROTATE + dHeader * HEADER_ROTATE;
        cool_down_count += cool_down_count >= COOL_DOWN ? 0 : 1;
    }

    public boolean tryMove(double x, double y, double nx, double ny) {
        return true;
    }

    public void draw(Graphics g) {
        g.drawString(name, (int)xPosition, (int)(yPosition - 12));
        g.drawRect((int)xPosition, (int)(yPosition - 10), textureBody.getWidth(), 5);
        g.setColor(new Color(0x0AE900));
        g.fillRect((int)xPosition + 1, (int) (yPosition - 10 + 1), (int) ((textureBody.getWidth() - 1) * health), 4);
        g.setColor(new Color(0xF5222D));
        g.fillRect(
                (int) (xPosition),
                (int) (yPosition - 4),
                (int) (textureBody.getWidth() * ((double) cool_down_count / COOL_DOWN) * drawCoolDown),
                (int) (2)
        );
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

}
