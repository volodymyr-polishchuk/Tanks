package launcher.states;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by Vladimir on 10/02/18.
 **/
public class GameStateManager implements GameState{
    private GameState nowState;

    public GameStateManager(GameState nowState) {
        this.nowState = nowState;
    }

    public void update() {
        nowState.update();
    }

    @Override
    public void draw(Graphics g) {
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

    @Override
    public void mouseClick(MouseEvent e) {

    }

    public GameState getGameState() {
        return nowState;
    }

    public void setGameState(GameState gameState) {
        this.nowState = gameState;
    }
}
