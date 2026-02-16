package com.inf1009.engine.interfaces;

import com.inf1009.engine.input.InputState;

public interface IInputInterface {

    void update();

    InputState getInputState();

    void rebindKey(String action, int keyCode);

    int getKeyBinding(String action);

    boolean isActionJustPressed(String action);
}
