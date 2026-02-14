package com.inf1009.engine.manager;

import com.badlogic.gdx.math.Vector2;
import com.inf1009.engine.input.InputState;
import com.inf1009.engine.interfaces.IMovable;

public class MovementManager {

    // Update entity using input
    public void update(IMovable entity, InputState inputState, float dt) {

        if (entity == null || inputState == null) return;

        setDirection(
                entity,
                inputState.getMoveX(),
                inputState.getMoveY()
        );

        updateVelocity(entity, dt);
        applyVelocity(entity, dt);
    }

    // Apply final velocity
    public void applyVelocity(IMovable entity, float dt) {
        if (entity == null) return;
        entity.applyVelocity(dt);
    }

    // Update velocity based on acceleration
    public void updateVelocity(IMovable entity, float dt) {
        if (entity == null) return;

        Vector2 acceleration = entity.getAcceleration();
        Vector2 velocity = entity.getVelocity();

        velocity.x += acceleration.x * dt;
        velocity.y += acceleration.y * dt;

        entity.setVelocity(velocity);
    }

    // Set movement direction
    public void setDirection(IMovable entity, float dx, float dy) {
        if (entity == null) return;
        entity.setDirection(dx, dy);
    }

    // Stop entity movement
    public void stop(IMovable entity) {
        if (entity == null) return;
        entity.setVelocity(new Vector2(0, 0));
        entity.setSpeed(0f);
    }
}
