package map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Vladimir on 13/02/18.
 **/
class MFrame extends JFrame implements MouseListener, KeyListener {
    private JPanel jPanel;
    private HashMap<Point, BufferedImage> points = new HashMap<>();
    private BufferedImage nowImage = Loader.FON_ASPHALT;

    public MFrame() throws HeadlessException {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        jPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                draw(g);
            }

        };
        jPanel.addMouseListener(this);
        addKeyListener(this);
        setLayout(new GridLayout(1, 1));
        add(jPanel);
        jPanel.requestFocus();

        new Thread(() -> {
            while (true) {
                update();
                jPanel.repaint();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void draw(Graphics g) {
        for (HashMap.Entry<Point, BufferedImage> item : points.entrySet()) {
            g.drawImage(item.getValue(), item.getKey().x, item.getKey().y, 50, 50, null);
        }
    }

    public void update() {
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        switch (k) {
            case KeyEvent.VK_1: nowImage = Loader.FON_ASPHALT; break;
            case KeyEvent.VK_2: nowImage = Loader.FON_GRASS; break;
            case KeyEvent.VK_3: nowImage = Loader.FON_GROUND; break;
            case KeyEvent.VK_4: nowImage = Loader.FON_ICE; break;
            case KeyEvent.VK_5: nowImage = Loader.FON_LIGHT_GRASS; break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        points.put(new Point((x / 50) * 50, (y / 50) * 50), nowImage);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println(x + ":" + y);
        points.put(new Point((x / 50) * 50, (y / 50) * 50), nowImage);
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
