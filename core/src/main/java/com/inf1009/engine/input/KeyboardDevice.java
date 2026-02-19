package com.inf1009.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
public class KeyboardDevice extends InputDevice {

    public KeyboardDevice(int leftKey, int rightKey, int upKey, int downKey, int jumpKey) {

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
    }
}
