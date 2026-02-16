package com.inf1009.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import java.util.EnumMap;
import java.util.Map;

// Mouse input implementation
public class MouseDevice extends InputDevice {

    public enum Key { LEFT, RIGHT, MIDDLE }
    public enum ButtonState { UP, DOWN }

    private Map<Key, ButtonState> keyStates = new EnumMap<>(Key.class);
    private Vector2 position = new Vector2();

    // Initializes mouse state tracking
    public MouseDevice() {
        for (Key k : Key.values()) keyStates.put(k, ButtonState.UP);
        defineBindings();
        defineActions();
    }

    @Override
    public void defineBindings() {}

    @Override
    public void defineActions() {}

    // Reads mouse position and button states
    @Override
    public void readInput() {
        position.set(Gdx.input.getX(), Gdx.input.getY());

        keyStates.put(Key.LEFT, Gdx.input.isButtonPressed(0) ? ButtonState.DOWN : ButtonState.UP);
        keyStates.put(Key.RIGHT, Gdx.input.isButtonPressed(1) ? ButtonState.DOWN : ButtonState.UP);
        keyStates.put(Key.MIDDLE, Gdx.input.isButtonPressed(2) ? ButtonState.DOWN : ButtonState.UP);
    }

    @Override
    public void readAction() {}

    // Returns current mouse position
    public Vector2 getPosition() {
        return position;
    }
}
