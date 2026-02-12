package com.inf1009.engine.entity;

public final class InputState {

    // Normalized directional input
    private final float moveX;
    private final float moveY;

    // Action trigger
    private final boolean jump;

    public InputState(float moveX, float moveY, boolean jump) {
        this.moveX = moveX;
        this.moveY = moveY;
        this.jump = jump;
    }

    public float getMoveX() { return moveX; }
    public float getMoveY() { return moveY; }
    public boolean isJump() { return jump; }

    // No-input state
    public static InputState neutral() {
        return new InputState(0f, 0f, false);
    }
}
