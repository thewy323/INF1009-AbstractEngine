package com.inf1009.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.util.EnumMap;
import java.util.Map;

// Concrete keyboard input implementation
public class KeyboardDevice extends InputDevice {

    // Logical key identifiers
    public enum Key { LEFT, RIGHT, UP, DOWN, JUMP }

    // Key state tracking
    public enum ButtonState { UP, DOWN }

    private Map<Key, ButtonState> keyStates = new EnumMap<>(Key.class);

    // Key codes
    private final int leftKey;
    private final int rightKey;
    private final int upKey;
    private final int downKey;
    private final int jumpKey;

    // Creates keyboard device with custom keys
    public KeyboardDevice(int leftKey, int rightKey, int upKey, int downKey, int jumpKey) {
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;
        this.downKey = downKey;
        this.jumpKey = jumpKey;

        for (Key k : Key.values()) keyStates.put(k, ButtonState.UP);

        defineBindings();
        defineActions();
    }

    // WASD preset
    public static KeyboardDevice wasd() {
        return new KeyboardDevice(
                Input.Keys.A,
                Input.Keys.D,
                Input.Keys.W,
                Input.Keys.S,
                Input.Keys.SPACE
        );
    }

    // Arrow keys preset
    public static KeyboardDevice arrows() {
        return new KeyboardDevice(
                Input.Keys.LEFT,
                Input.Keys.RIGHT,
                Input.Keys.UP,
                Input.Keys.DOWN,
                Input.Keys.ENTER
        );
    }

    // Defines logical key bindings
    @Override
    public void defineBindings() {
        setBinding("left", leftKey);
        setBinding("right", rightKey);
        setBinding("up", upKey);
        setBinding("down", downKey);
        setBinding("jump", jumpKey);
    }

    @Override
    public void defineActions() {}

    // Reads current keyboard state
    @Override
    public void readInput() {

        deviceInput.reset();

        if (Gdx.input.isKeyPressed(leftKey))
            deviceInput.setMoveX(deviceInput.getMoveX() - 1f);

        if (Gdx.input.isKeyPressed(rightKey))
            deviceInput.setMoveX(deviceInput.getMoveX() + 1f);

        if (Gdx.input.isKeyPressed(downKey))
            deviceInput.setMoveY(deviceInput.getMoveY() - 1f);

        if (Gdx.input.isKeyPressed(upKey))
            deviceInput.setMoveY(deviceInput.getMoveY() + 1f);

        deviceInput.setJump(Gdx.input.isKeyJustPressed(jumpKey));

        keyStates.put(Key.LEFT, Gdx.input.isKeyPressed(leftKey) ? ButtonState.DOWN : ButtonState.UP);
        keyStates.put(Key.RIGHT, Gdx.input.isKeyPressed(rightKey) ? ButtonState.DOWN : ButtonState.UP);
        keyStates.put(Key.UP, Gdx.input.isKeyPressed(upKey) ? ButtonState.DOWN : ButtonState.UP);
        keyStates.put(Key.DOWN, Gdx.input.isKeyPressed(downKey) ? ButtonState.DOWN : ButtonState.UP);
        keyStates.put(Key.JUMP, Gdx.input.isKeyPressed(jumpKey) ? ButtonState.DOWN : ButtonState.UP);
    }

    @Override
    public void readAction() {}
}
