package com.inf1009.engine.input;

import com.badlogic.gdx.Gdx;
import com.inf1009.engine.entity.inputState;

public class Keyboard extends AbstractInputDevice {

    private final int left;
    private final int right;
    private final int up;
    private final int down;

    public Keyboard(int left, int right, int up, int down) {
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
    }

    @Override
    public inputState readInput() {
        float mx = 0f;
        float my = 0f;

        if (Gdx.input.isKeyPressed(left)) mx -= 1f;
        if (Gdx.input.isKeyPressed(right)) mx += 1f;
        if (Gdx.input.isKeyPressed(up)) my += 1f;
        if (Gdx.input.isKeyPressed(down)) my -= 1f;

        return new inputState(mx, my, false);
    }

    public static Keyboard wasd() {
        return new Keyboard(
                com.badlogic.gdx.Input.Keys.A,
                com.badlogic.gdx.Input.Keys.D,
                com.badlogic.gdx.Input.Keys.W,
                com.badlogic.gdx.Input.Keys.S
        );
    }

    public static Keyboard arrows() {
        return new Keyboard(
                com.badlogic.gdx.Input.Keys.LEFT,
                com.badlogic.gdx.Input.Keys.RIGHT,
                com.badlogic.gdx.Input.Keys.UP,
                com.badlogic.gdx.Input.Keys.DOWN
        );
    }
}
