package launcher.states;

import launcher.Launcher;
import launcher.ResourceLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by Vladimir on 10/02/18.
 **/
public class MultiPlayerMenuState implements GameState {
    private static String[] items = new String[] {"Адреса сервера", "Порт", "З'єднатися", "На головну"};
    private static final int ADDRESS = 0, PORT = 1, CONNECT = 2, RETURN = 3;
    private int nowItem = ADDRESS;
    private String address = "";
    private boolean inputAddress = false;
    private String port = "";
    private boolean inputPort = false;

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        Color color = g.getColor();
        Font font = g.getFont();
        int w = Launcher.GAME_WINDOW.getWidth();
        int h = Launcher.GAME_WINDOW.getHeight();

        g.setColor(new Color(76, 175, 80));
        g.fillRect(0, 0, w, h);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier New", Font.BOLD, 24));
        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            if (i == ADDRESS && (address.length() > 0 || inputAddress)) {
                item = address;
            } else if (i == PORT && (port.length() > 0 || inputPort)) {
                item = port;
            }
            g.drawString(item,
                    (w - g.getFontMetrics().stringWidth(item)) / 2,
                    ((h - g.getFontMetrics().getHeight() * items.length) / 2) + (i * 50));
        }
        g.drawImage(ResourceLoader.MENU_CHECK_ICON,
                (w - 300) / 2,
                ((h - g.getFontMetrics().getHeight() * items.length - 60) / 2) + (nowItem * 50),
                null);

        g.setColor(color);
        g.setFont(font);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (inputAddress) {
            if (c == KeyEvent.VK_BACK_SPACE) {
                address = address.substring(0, address.length() - 1);
                return;
            }
            if (address.length() >= 14) return;
            if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z'
                    || c == '_' || c >= '0' && c <= '9' || c == '.') {
                address += c;
            }
        } else if (inputPort) {
            if (c == KeyEvent.VK_BACK_SPACE) {
                port = port.substring(0, port.length() - 1);
                return;
            }
            if (port.length() >= 5) return;
            if (c >= '0' && c <= '9') {
                port += c;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: nowItem = nowItem > 0 ? nowItem - 1: items.length - 1; inputPort = false; inputAddress = false; break;
            case KeyEvent.VK_DOWN: nowItem = nowItem < items.length - 1 ? nowItem + 1 : 0; inputPort = false; inputAddress = false; break;
            case KeyEvent.VK_ENTER:
                switch (nowItem) {
                    case ADDRESS: inputAddress = !inputAddress; break;
                    case PORT: inputPort = !inputPort; break;
                    case CONNECT: break;
                    case RETURN: Launcher.GAME_WINDOW.setGameState(new MenuState()); break;
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClick(MouseEvent e) {

    }
}
