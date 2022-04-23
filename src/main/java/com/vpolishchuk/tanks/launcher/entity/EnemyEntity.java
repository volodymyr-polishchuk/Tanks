package com.vpolishchuk.tanks.launcher.entity;

import com.vpolishchuk.tanks.client.Bullet;
import com.vpolishchuk.tanks.launcher.ResourceLoader;
import com.vpolishchuk.tanks.launcher.states.SinglePlayerState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by Vladimir on 15/02/18.
 **/
public class EnemyEntity implements Entity {
    private String name;
    private double xPosition;
    private double yPosition;
    private double rotateBody = 0.0;
    private double rotateHeader = 0.0;
    //
    private double vx = 0;
    private double vy = 0;
    //
    private double health = 1;
    private double cool_down_count = (int) COOL_DOWN;

    private int move = 0, dBody = 0, dHeader = 0, drawCoolDown = 1;
    private boolean moveForce = false;
    private BufferedImage textureBody;
    private BufferedImage textureHeader;

    private static final double COOL_DOWN = 50; // Кадрів
    private static final double BODY_ROTATE = 1.0; // Градусів за кадр
    private static final double HEADER_ROTATE = 2.2; // Градусів за кадр
    private static final double MOVE_SPEED = 2.0; // Довжина пройденого шляху за кадр
    private static final double MOVE_FORCE = 1.0; // Довжина пройденого шляху за кадр

    private SinglePlayerState state;

    private EnemyEntity() {
        textureBody = ResourceLoader.TANK_BODY_ENEMY;
        textureHeader = ResourceLoader.TANK_HEADER_ENEMY;
    }

    public EnemyEntity(String name, double xPosition, double yPosition, double rotateBody, double rotateHeader, SinglePlayerState state) {
        this();
        this.name = name;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.rotateBody = rotateBody;
        this.rotateHeader = rotateHeader;
        this.state = state;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Point getPosition() {
        return new Point((int)xPosition, (int)yPosition);
    }

    @Override
    public double getRotate() {
        return rotateBody;
    }

    @Override
    public String getData() {
        final char split = '/';
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(split);
        sb.append(xPosition).append(split);
        sb.append(yPosition).append(split);
        sb.append(rotateBody).append(split);
        sb.append(rotateHeader).append(split);
        sb.append(cool_down_count).append(split);
        sb.append(health);
        return sb.toString();
    }

    @Override
    public Entity getInstance(String data) {
        final char split = '/';
        String[] args = data.split(String.valueOf(split));
        if (args.length != 7) throw new IllegalComponentStateException("Data must have 7 arg, but get {" + data + "} data");
        EnemyEntity entity = new EnemyEntity();
        entity.name = args[0];
        entity.xPosition = Double.parseDouble(args[1]);
        entity.yPosition = Double.parseDouble(args[2]);
        entity.rotateBody = Double.parseDouble(args[3]);
        entity.rotateHeader = Double.parseDouble(args[4]);
        entity.cool_down_count = Double.parseDouble(args[5]);
        entity.health = Double.parseDouble(args[6]);
        return entity;
    }

    public Polygon getPolygon() {
        return getPolygon(textureBody.getWidth(), textureBody.getHeight(), 46, 30);
    }

    public Polygon getPolygon(double x, double y) {
        return getPolygon(x, y, textureBody.getWidth(), textureBody.getHeight(), 46, 30);
    }

    public Polygon getPolygon(int rw, int rh, int hw, int hh) {
        return getPolygon(xPosition, yPosition, rw, rh, hw, hh);
    }
    /**
     * @param rw - реальна ширина текстури навколо картинки
     * @param rh - реальна висота текстури навколо картинки
     * @param hw - ширина хітбокса
     * @param hh - висота хітбокса
     * @return Polygon - який позначає хітбокс
     */
    public Polygon getPolygon(double x, double y, int rw, int rh, int hw, int hh) {
        double rb = Math.toRadians(rotateBody);
//        (x1, y1)
        double x1 = ((x + rw / 2) + (hw / 2) * Math.cos(rb)) + (hh / 2) * Math.cos(rb + (Math.PI / 2d));
        double y1 = ((y + rh / 2) + (hw / 2) * Math.sin(rb)) + (hh / 2) * Math.sin(rb + (Math.PI / 2d));
//        (x2, y2)
        double x2 = x1 + (hw) * Math.cos(rb + Math.PI);
        double y2 = y1 + (hw) * Math.sin(rb + Math.PI);
//        (x3, y3)
        double x3 = x2 + (hh) * Math.cos(rb + ((Math.PI * 3d) / 2d));
        double y3 = y2 + (hh) * Math.sin(rb + ((Math.PI * 3d) / 2d));
//        (x4, y4)
        double x4 = x3 + (hw) * Math.cos(rb + (Math.PI * 2d));
        double y4 = y3 + (hw) * Math.sin(rb + (Math.PI * 2d));
//
        return new Polygon(new int[]{(int) x1, (int) x2, (int) x3, (int) x4},
                new int[]{(int) y1, (int) y2, (int) y3, (int) y4}, 4);
    }

    @Override
    public boolean intersect(Point point) {
        return getPolygon(textureBody.getWidth(), textureBody.getHeight(), 46, 30).contains(point);
    }

    @Override
    public boolean intersect(Polygon polygon) {
        for (int i = 0; i < polygon.npoints; i++) {
            if (intersect(new Point(polygon.xpoints[i], polygon.ypoints[i]))) return true;
        }
        return false;
    }

    @Override
    public void update() {
        if (health <= 0) {
            textureBody = ResourceLoader.TANK_BODY_CRASH;
            textureHeader = ResourceLoader.TANK_HEADER_CRASH;
            return;
        }
        doMove();
        doRotate();
        cool_down_count += cool_down_count >= COOL_DOWN ? 0 : 1;
//        if (move != 0 && moveForce) health -= 0.001;
        II();
    }

    private void II() {
        Point point = state.tank.getPosition();
        double dist = Math.sqrt(Math.pow(point.getX() - xPosition, 2) + Math.pow(point.getY() - yPosition, 2));
        if (dist > 20 && dist < 300) {
            double bx = xPosition + Math.cos(Math.toRadians(rotateBody));
            double by = yPosition + Math.sin(Math.toRadians(rotateBody));
            //Рахуємо куди потрібно повернути тіло
            double s = (bx-xPosition)*(point.getY()-yPosition)-(by-yPosition)*(point.getX()-xPosition);
            if (s > 2) turnBodyRight(); else if (s < -2) turnBodyLeft();else turnBodyStop();
            //Рахуємо куди треба їхати
            double x1 = Math.cos(Math.toRadians(rotateBody));
            double y1 = Math.sin(Math.toRadians(rotateBody));
            double x2 = state.tank.getPosition().getX() - xPosition;
            double y2 = state.tank.getPosition().getY() - yPosition;
            double result = Math.toDegrees(Math.acos((x1 * x2 + y1 * y2) / (Math.sqrt(x1*x1 + y1*y1) * Math.sqrt(x2*x2 + y2*y2))));
            if (Math.abs(result) < 20 && dist > 150) moveForward(); else moveStop();
            //Рахуємо коли потрібно стріляти
            x1 = Math.cos(Math.toRadians(rotateBody + rotateHeader));
            y1 = Math.sin(Math.toRadians(rotateBody + rotateHeader));
            x2 = state.tank.getPosition().getX() - xPosition;
            y2 = state.tank.getPosition().getY() - yPosition;
            result = Math.toDegrees(Math.acos((x1 * x2 + y1 * y2) / (Math.sqrt(x1*x1 + y1*y1) * Math.sqrt(x2*x2 + y2*y2))));
            if (Math.abs(result) < 5 && cool_down_count == COOL_DOWN) {
                state.bullets.add(doShot());
            }
            //Рахуємо куди потрібно повертати башню
            bx = xPosition + Math.cos(Math.toRadians(rotateBody + rotateHeader));
            by = yPosition + Math.sin(Math.toRadians(rotateBody + rotateHeader));
            s = (bx-xPosition)*(point.getY()-yPosition)-(by-yPosition)*(point.getX()-xPosition);
            if (s > 4) turnHeaderRight(); else if (s < -4) turnHeaderLeft(); else turnHeaderStop();
        } else {
            moveStop();
            turnBodyStop();
            turnHeaderStop();
        }
    }

    private void doMove() {
        double xNew = xPosition + MOVE_SPEED * move * Math.cos(Math.toRadians(rotateBody))
                + (moveForce ? MOVE_FORCE * move * Math.cos(Math.toRadians(rotateBody)) : 0);
        double yNew = yPosition + MOVE_SPEED * move * Math.sin(Math.toRadians(rotateBody))
                + (moveForce ? MOVE_FORCE * move * Math.sin(Math.toRadians(rotateBody)) : 0);
        if (tryMove(xPosition, yPosition, xNew, yNew)) {
            xPosition = xNew;
            yPosition = yNew;
        }
    }

    private void doRotate() {
        if (move == -1) rotateBody = rotateBody - dBody * BODY_ROTATE;
            else rotateBody = rotateBody + dBody * BODY_ROTATE;
        if (dHeader != 0)
            rotateHeader = rotateHeader + dBody * BODY_ROTATE + dHeader * HEADER_ROTATE;
    }

    public boolean tryMove(double xPosition, double yPosition, double xNew, double yNew) {
        if (health <= 0) return false;
        if (state.tank.intersect(getPolygon(xNew, yNew))) return false;
        return true;
    }

    @Override
    public void draw(Graphics g) {
//        g.setColor(Color.BLACK);
//        g.drawString(name, (int)xPosition, (int)(yPosition - 12));

        g.drawRect((int)xPosition, (int)(yPosition - 10), textureBody.getWidth(), 5);
        g.setColor(new Color(0x0AE900));
        g.fillRect((int)xPosition + 1, (int) (yPosition - 10 + 1), (int) ((textureBody.getWidth() - 1) * health), 4);
//
//        g.setColor(new Color(0xF5222D));
//        g.fillRect((int) (xPosition + 1), (int) (yPosition - 4),
//                (int) (textureBody.getWidth() * (cool_down_count / COOL_DOWN)), (2));
//        g.drawPolygon(getPolygon());
//        g.drawOval((int)xPosition - 300 + textureBody.getWidth() / 2, (int)yPosition - 300 + textureBody.getHeight() / 2, 600, 600);
        g.setColor(Color.BLACK);
//      rotate image body
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotateBody), textureBody.getWidth() / 2, textureBody.getHeight() / 2);
        BufferedImage imageBody = new BufferedImage(textureBody.getWidth(), textureBody.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D d = (Graphics2D) imageBody.getGraphics();
        d.drawImage(textureBody, transform, null);
//      draw image body
        g.drawImage(imageBody, (int)xPosition, (int)yPosition, null);
//      rotate image header
        transform.rotate(Math.toRadians(rotateHeader), textureHeader.getWidth() / 2, textureHeader.getHeight() / 2);
        BufferedImage imageHeader = new BufferedImage(textureHeader.getWidth(), textureHeader.getHeight(), BufferedImage.TYPE_INT_ARGB);
        d = (Graphics2D) imageHeader.getGraphics();
        d.drawImage(textureHeader, transform, null);
//      draw image header
        g.drawImage(imageHeader, (int)xPosition, (int)yPosition, null);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    public void doHit() {health = health - 0.30;}

    public void doHit(double hit) {health = health - hit;}

    private void moveForward() {move = 1;}

    private void moveBack() {move = -1;}

    private void moveStop() {move = 0;}

    private void moveForce() {moveForce = true;}

    private void stopForce() {moveForce = false;}

    private void turnBodyLeft() {dBody = -1;}

    private void turnBodyRight() {dBody = 1;}

    private void turnBodyStop() {dBody = 0;}

    private void turnHeaderLeft() {dHeader = -1;}

    private void turnHeaderRight() {dHeader = 1;}

    private void turnHeaderStop() {dHeader = 0;}

    private Bullet doShot() {
        if (!(cool_down_count >= COOL_DOWN)) return null;
        cool_down_count = 0;
        return new Bullet(
                (int) (xPosition + Math.cos(Math.toRadians(rotateHeader + rotateBody)) * 30 + textureHeader.getWidth() / 2),
                (int) (yPosition + Math.sin(Math.toRadians(rotateHeader + rotateBody)) * 30 + textureHeader.getHeight() / 2),
                rotateBody + rotateHeader);
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
