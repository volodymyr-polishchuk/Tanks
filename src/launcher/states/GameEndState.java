package launcher.states;

import launcher.Launcher;
import launcher.ResourceLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by Vladimir on 15/02/18.
 **/
public class GameEndState implements GameInnerState {
    private String[] items = new String[] {"Головне меню", "Вихід"};
    private static final int RETURN = 0, EXIT = 1;
    private int nowItem = 0;
    private GameState ownerState;

    public GameEndState(GameState ownerState) {
        this.ownerState = ownerState;
    }

    @Override
    public GameState getOwner() {
        return ownerState;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        ownerState.draw(g);
        Color color = g.getColor();
        Font font = g.getFont();
        int w = Launcher.GAME_WINDOW.getWidth();
        int h = Launcher.GAME_WINDOW.getHeight();

        g.setColor(new Color(122, 122, 122, 120));
        g.fillRect(0, 0, w, h);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier New", Font.PLAIN, 16));
        g.drawString("Hello, " + Launcher.NICKNAME, 20, 20);
        g.setColor(new Color(0x801417));
        g.setFont(new Font("Times New Roman", Font.BOLD, 48));
        g.drawString("YOU DIED",
                (w - g.getFontMetrics().stringWidth("YOU DIED")) / 2,
                ((h - g.getFontMetrics().getHeight() * items.length) / 2) + (-1 * 50));
        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier New", Font.BOLD, 24));
        for (int i = 0; i < items.length; i++) {
            String item = items[i];
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

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                nowItem = nowItem > 0 ? nowItem - 1 : items.length - 1;
                break;
            case KeyEvent.VK_DOWN:
                nowItem = nowItem < items.length - 1 ? nowItem + 1 : 0;
                break;
//            case KeyEvent.VK_ESCAPE:
//                Launcher.GAME_WINDOW.setGameState(ownerState);
//                break;
            case KeyEvent.VK_ENTER:
                switch (nowItem) {
                    case RETURN:
                        Launcher.GAME_WINDOW.setGameState(new MenuState());
                        break;
                    case EXIT:
                        System.exit(0);
                        break;
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
