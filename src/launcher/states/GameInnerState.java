package launcher.states;

/**
 * Created by Vladimir on 11/02/18.
 **/
public interface GameInnerState extends GameState {

    GameState getOwner();

}
