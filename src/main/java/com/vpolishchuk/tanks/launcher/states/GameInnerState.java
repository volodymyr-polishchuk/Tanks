package com.vpolishchuk.tanks.launcher.states;

import com.vpolishchuk.tanks.launcher.states.GameState;

/**
 * Created by Vladimir on 11/02/18.
 **/
public interface GameInnerState extends GameState {

    GameState getOwner();

}
