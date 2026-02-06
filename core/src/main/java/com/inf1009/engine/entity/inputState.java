package com.inf1009.engine.entity;

public class inputState {
    public float moveX;
    public float moveY;
    public boolean jump;

    public inputState(float moveX, float moveY, boolean jump) {
        this.moveX = moveX;
        this.moveY = moveY;
        this.jump = jump;
    }

    public static inputState neutral() {
        return new inputState(0f, 0f, false);
    }
}
