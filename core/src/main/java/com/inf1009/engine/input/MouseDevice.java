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
    private Map<Key, ButtonState> previousKeyStates = new EnumMap<>(Key.class);
    private Vector2 position = new Vector2();
    private Vector2 clickPosition = new Vector2();
    private boolean leftJustClicked = false;
    private boolean rightJustClicked = false;

    // Initializes mouse state tracking
    public MouseDevice() {
        for (Key k : Key.values()) {
            keyStates.put(k, ButtonState.UP);
            previousKeyStates.put(k, ButtonState.UP);
        }
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
        // Store previous states
        for (Key k : Key.values()) {
            previousKeyStates.put(k, keyStates.get(k));
        }

        // Update position (convert Y to game coordinates)
        position.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

        // Update button states
        keyStates.put(Key.LEFT, Gdx.input.isButtonPressed(0) ? ButtonState.DOWN : ButtonState.UP);
        keyStates.put(Key.RIGHT, Gdx.input.isButtonPressed(1) ? ButtonState.DOWN : ButtonState.UP);
        keyStates.put(Key.MIDDLE, Gdx.input.isButtonPressed(2) ? ButtonState.DOWN : ButtonState.UP);

        // Check for just clicked (transition from UP to DOWN)
        leftJustClicked = (previousKeyStates.get(Key.LEFT) == ButtonState.UP &&
                           keyStates.get(Key.LEFT) == ButtonState.DOWN);
        rightJustClicked = (previousKeyStates.get(Key.RIGHT) == ButtonState.UP &&
                            keyStates.get(Key.RIGHT) == ButtonState.DOWN);

        // Store click position when left clicked
        if (leftJustClicked) {
            clickPosition.set(position);
        }
    }


    // Returns current mouse position (in game coordinates)
    public Vector2 getPosition() {
        return position;
    }

    // Returns position where left click occurred
    public Vector2 getClickPosition() {
        return clickPosition;
    }

    // Returns true if left button was just clicked this frame
    public boolean isLeftJustClicked() {
        return leftJustClicked;
    }

    // Returns true if right button was just clicked this frame
    public boolean isRightJustClicked() {
        return rightJustClicked;
    }

    // Returns true if left button is currently held down
    public boolean isLeftDown() {
        return keyStates.get(Key.LEFT) == ButtonState.DOWN;
    }

    // Returns true if right button is currently held down
    public boolean isRightDown() {
        return keyStates.get(Key.RIGHT) == ButtonState.DOWN;
    }
}
