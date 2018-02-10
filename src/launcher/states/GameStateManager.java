package launcher.states;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Vladimir on 10/02/18.
 **/
public class GameStateManager {
    private GameState nowState;

    public GameStateManager(GameState nowState) {
        this.nowState = nowState;
    }

    public void update() {
        nowState.update();
    }

    public void redraw(Graphics g) {
        nowState.draw(g);
    }

    public void keyTyped(KeyEvent e) {
        nowState.keyTyped(e);
    }

    public void keyPressed(KeyEvent e) {
        nowState.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        nowState.keyReleased(e);
    }

    public GameState getGameState() {
        return nowState;
    }

    public void setGameState(GameState gameState) {
        this.nowState = gameState;
    }
}
