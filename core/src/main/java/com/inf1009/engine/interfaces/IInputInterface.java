package com.inf1009.engine.interfaces;

import com.badlogic.gdx.math.Vector2;
import com.inf1009.engine.input.InputState;

public interface IInputInterface {

    void update();

    InputState getInputState();

    void rebindKey(String action, int keyCode);

    int getKeyBinding(String action);

    boolean isActionJustPressed(String action);

    // Mouse input methods
    boolean isMouseLeftJustClicked();

    Vector2 getMousePosition();

    Vector2 getMouseClickPosition();

    // UI/Menu input methods
    boolean isKeyJustPressed(int keyCode);

    boolean isJustTouched();

    // Raw input coordinates (screen space)
    int getInputX();

    int getInputY();
}
