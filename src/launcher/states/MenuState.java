package launcher.states;

import launcher.Launcher;
import launcher.ResourceLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by Vladimir on 10/02/18.
 **/
public class MenuState implements GameState {
    private String[] menuItems = new String[] {"Одиночна гра", "Мережева гра", "Налаштування", "Вихід"};
    private static final int SINGLE_PLAYER = 0, MULTI_PLAYER = 1, SETTING = 2, EXIT = 3;
    private int nowItem = 0;

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
        for (int i = 0; i < menuItems.length; i++) {
            String item = menuItems[i];
            g.drawString(menuItems[i],
                    (w - g.getFontMetrics().stringWidth(menuItems[i])) / 2,
                    ((h - g.getFontMetrics().getHeight() * menuItems.length) / 2) + (i * 50));
        }
        g.drawImage(ResourceLoader.MENU_CHECK_ICON,
                (w - 300) / 2,
                ((h - g.getFontMetrics().getHeight() * menuItems.length - 60) / 2) + (nowItem * 50),
                null);

        g.drawImage(ResourceLoader.MENU_LOGO,
                (w - ResourceLoader.MENU_LOGO.getWidth()) / 2,
                50,
                null);

        g.setColor(color);
        g.setFont(font);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: nowItem = nowItem > 0 ? nowItem - 1: menuItems.length - 1; break;
            case KeyEvent.VK_DOWN: nowItem = nowItem < menuItems.length - 1 ? nowItem + 1 : 0; break;
            case KeyEvent.VK_ENTER:
                switch (nowItem) {
                    case SINGLE_PLAYER: Launcher.GAME_WINDOW.setGameState(new SinglePlayerState()); break;
                    case MULTI_PLAYER: Launcher.GAME_WINDOW.setGameState(new MultiPlayerMenuState()); break;
                    case SETTING: Launcher.GAME_WINDOW.setGameState(new SettingState()); break;
                    case EXIT: System.exit(0); break;
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
