package launcher;

import launcher.states.GameState;
import launcher.states.GameStateManager;
import launcher.states.MenuState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MainFrame extends JFrame implements Runnable {
    private JPanel mainPanel;
    private Thread mainThread;
    private GameStateManager manager;
    public GameLog log = new GameLog();
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;

    public MainFrame(String title) throws HeadlessException {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        requestFocus();
        try {
            setIconImage(ImageIO.read(getClass().getResourceAsStream("/resource/iconlogo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {manager.keyTyped(e);}
            @Override
            public void keyPressed(KeyEvent e) {manager.keyPressed(e);}
            @Override
            public void keyReleased(KeyEvent e) {manager.keyReleased(e);}
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                manager.mouseClick(e);
            }
        });

        manager = new GameStateManager(new MenuState());

        mainPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                manager.draw(g);
                log.draw(g);
            }
        };
        add(mainPanel);

        mainThread = new Thread(this);
        mainThread.start();
    }

    public GameState setGameState(GameState state) {
        GameState prevGameState = manager.getGameState();
        manager.setGameState(state);
        return prevGameState;
    }

    @Override
    public void run() {
        long start, end;
        while (true) {
            try {
                start = System.nanoTime();

                manager.update();
                log.update();
                mainPanel.repaint();

                end = System.nanoTime();
                if (30 - ((end - start) / 1_000_000) > 0)
                    Thread.sleep(30 - ((end - start) / 1_000_000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
