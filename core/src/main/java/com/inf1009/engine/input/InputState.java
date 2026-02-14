package com.inf1009.engine.input;

public class InputState {
    public float moveX;
    public float moveY;
    public boolean jump;

    public InputState() {
        reset();
    }

    public void reset() {
        moveX = 0f;
        moveY = 0f;
        jump = false;
    }
}
