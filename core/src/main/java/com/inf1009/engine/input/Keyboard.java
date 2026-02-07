package com.inf1009.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.inf1009.engine.entity.InputState;

public class Keyboard extends AbstractInputDevice {

    private final int leftKey;
    private final int rightKey;
    private final int jumpKey;

    public Keyboard(int leftKey, int rightKey, int jumpKey) {
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.jumpKey = jumpKey;
    }

    @Override
    public InputState readInput() {
        float moveX = 0f;

        if (Gdx.input.isKeyPressed(leftKey)) moveX -= 1f;
        if (Gdx.input.isKeyPressed(rightKey)) moveX += 1f;

        // Platformer: do NOT use moveY. Jump is separate.
        float moveY = 0f;

        // Jump should be just-pressed so holding W/UP doesn't spam jumps
        boolean jump = Gdx.input.isKeyJustPressed(jumpKey);

        return new InputState(moveX, moveY, jump);
    }

    // P1: A/D move, W jump
    public static Keyboard player1WASD() {
        return new Keyboard(Input.Keys.A, Input.Keys.D, Input.Keys.W);
    }

    // P2: LEFT/RIGHT move, UP jump
    public static Keyboard player2Arrows() {
        return new Keyboard(Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP);
    }
}
