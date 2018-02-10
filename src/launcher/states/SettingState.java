package launcher.states;

import launcher.Launcher;
import launcher.ResourceLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by Vladimir on 10/02/18.
 **/
public class SettingState implements GameState {
    private String[] settingItems = new String[]{"Тема танку", "Ім'я користувача", "На головне меню"};
    private static final int THEME = 0, PLAYER_NAME = 1, RETURN = 2;
    private int nowItem = THEME;
    private boolean inputName = false;
    private String name = "";
    private int theme = 0;

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
        for (int i = 0; i < settingItems.length; i++) {
            String item = settingItems[i];
            if (i == THEME) {
                switch (theme) {
                    case 0: g.setColor(new Color(0xB58154));break;
                    case 1: g.setColor(new Color(0x4A7105));break;
                    case 2: g.setColor(new Color(0x000E9C));break;
                    case 3: g.setColor(new Color(0x96004B));break;

                }
            } else g.setColor(Color.BLACK);
            if (i == PLAYER_NAME && (inputName || name.length() > 0)) {
                item = name;
            }
            g.drawString(item,
                    (w - g.getFontMetrics().stringWidth(item)) / 2,
                    ((h - g.getFontMetrics().getHeight() * settingItems.length) / 2) + (i * 50));
        }
        g.drawImage(ResourceLoader.MENU_CHECK_ICON,
                (w - 300) / 2,
                ((h - g.getFontMetrics().getHeight() * settingItems.length - 60) / 2) + (nowItem * 50),
                null);

        g.setColor(color);
        g.setFont(font);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (inputName) {
            if (c == KeyEvent.VK_BACK_SPACE) {
                name = name.substring(0, name.length() - 1);
                return;
            }
            if (name.length() >= 14) return;
            if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z'
                    || c == '_' || c >= '0' && c <= '9') {
                name += c;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: nowItem = nowItem > 0 ? nowItem - 1: settingItems.length - 1; inputName = false; break;
            case KeyEvent.VK_DOWN: nowItem = nowItem < settingItems.length - 1 ? nowItem + 1 : 0; inputName = false; break;
            case KeyEvent.VK_ENTER:
                switch (nowItem) {
                    case THEME: theme = theme < 3 ? theme + 1: 0;break;
                    case PLAYER_NAME: inputName = !inputName; name = "";break;
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
