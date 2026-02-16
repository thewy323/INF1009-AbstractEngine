package com.inf1009.engine.input;

// Stores directional and action input values
public class InputState {

    private float moveX;
    private float moveY;
    private boolean jump;

    // Initializes default state
    public InputState() {
        reset();
    }

    // Resets all input values
    public void reset() {
        moveX = 0f;
        moveY = 0f;
        jump = false;
    }

    public float getMoveX() { return moveX; }
    public float getMoveY() { return moveY; }
    public boolean isJump() { return jump; }

    public void setMoveX(float moveX) { this.moveX = moveX; }
    public void setMoveY(float moveY) { this.moveY = moveY; }
    public void setJump(boolean jump) { this.jump = jump; }
}
