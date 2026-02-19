package com.inf1009.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.util.EnumMap;
import java.util.Map;

public class KeyboardDevice extends InputDevice {

    public enum Key { LEFT, RIGHT, UP, DOWN, JUMP }
    public enum ButtonState { UP, DOWN }

    private Map<Key, ButtonState> keyStates = new EnumMap<>(Key.class);

    public KeyboardDevice(int leftKey, int rightKey, int upKey, int downKey, int jumpKey) {
        for (Key k : Key.values()) keyStates.put(k, ButtonState.UP);

        setBinding("left", leftKey);
        setBinding("right", rightKey);
        setBinding("up", upKey);
        setBinding("down", downKey);
        setBinding("jump", jumpKey);

        defineBindings();
        defineActions();
    }

    public static KeyboardDevice wasd() {
        return new KeyboardDevice(Input.Keys.A, Input.Keys.D, Input.Keys.W, Input.Keys.S, Input.Keys.SPACE);
    }

    public static KeyboardDevice arrows() {
        return new KeyboardDevice(Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.ENTER);
    }

    @Override
    public void defineBindings() {}

    @Override
    public void defineActions() {}

    @Override
    public void readInput() {
        deviceInput.reset();

        int leftKey = getBinding("left");
        int rightKey = getBinding("right");
        int upKey = getBinding("up");
        int downKey = getBinding("down");
        int jumpKey = getBinding("jump");

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
}
