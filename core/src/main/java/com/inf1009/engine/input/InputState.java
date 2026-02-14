package com.inf1009.engine.input;

public class InputState {

    // Fields
    private float moveX;
    private float moveY;
    private boolean jump;

    // Constructor
    public InputState() {
        reset();
    }

    // Reset state
    public void reset() {
        moveX = 0f;
        moveY = 0f;
        jump = false;
    }

    // Getters
    public float getMoveX() { return moveX; }
    public float getMoveY() { return moveY; }
    public boolean isJump() { return jump; }

    // Setters
    public void setMoveX(float moveX) { this.moveX = moveX; }
    public void setMoveY(float moveY) { this.moveY = moveY; }
    public void setJump(boolean jump) { this.jump = jump; }
}
