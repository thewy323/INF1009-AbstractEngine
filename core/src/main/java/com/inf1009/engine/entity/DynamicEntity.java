package com.inf1009.engine.entity;

import com.inf1009.engine.interfaces.ICollidable;
import com.inf1009.engine.interfaces.IMoveable;

public class DynamicEntity extends AbstractGameEntity implements IMoveable, ICollidable {

    // Velocity in px per second
    private float velocityX = 0f;
    private float velocityY = 0f;

    // Movement speed scalar
    private float speed;

    // True when resting on surface
    private boolean isGrounded = false;

    // Simple physics parameters
    private float gravity = -900f;
    private float jumpImpulse = 380f;

    public DynamicEntity(float x, float y, float w, float h, float speed) {
        super(x, y, w, h);
        this.speed = speed;
    }

    // Applies physics each frame
    @Override
    public void update(float dt) {
        isGrounded = false;
        applyGravity(gravity, dt);
        move(velocityX, velocityY, dt);
    }

    // Converts input state into velocity
    @Override
    public void movement(InputState input, float dt) {

        velocityX = input.getMoveX() * speed;

        if (input.isJump() && isGrounded) {
            jump(jumpImpulse);
        }
    }

    // Applies vertical acceleration
    public void applyGravity(float gravity, float dt) {
        if (!isGrounded) {
            velocityY += gravity * dt;
        }
    }

    // Moves entity using velocity
    public void move(float dx, float dy, float dt) {
        float nx = getX() + dx * dt;
        float ny = getY() + dy * dt;
        setPosition(nx, ny);
    }

    // Applies upward impulse
    public void jump(float force) {
        velocityY = force;
        isGrounded = false;
    }

    // Snaps entity to surface
    public void landOn(float groundY) {
        setPosition(getX(), groundY);
        velocityY = 0f;
        isGrounded = true;
    }

    // Velocity accessors
    public float getVelocityX() { return velocityX; }
    public float getVelocityY() { return velocityY; }
    public void setVelocityX(float vx) { this.velocityX = vx; }
    public void setVelocityY(float vy) { this.velocityY = vy; }

    public float getSpeed() { return speed; }
    public void setSpeed(float speed) { this.speed = speed; }

    public boolean isGrounded() { return isGrounded; }

    // Default collision reaction
    @Override
    public void onCollision(ICollidable other) {
        setVelocityX(-getVelocityX());
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
