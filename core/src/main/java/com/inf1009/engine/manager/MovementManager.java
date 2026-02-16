package com.inf1009.engine.manager;

import com.badlogic.gdx.math.Vector2;
import com.inf1009.engine.interfaces.IMovable;
import com.inf1009.engine.interfaces.IMovementManager;

// Handles movement logic for IMovable entities
public class MovementManager implements IMovementManager {

    @Override
    public void update(IMovable entity, float deltaTime) {
        if (entity == null) return;
        applyVelocity(entity, deltaTime);
    }

    // Applies directional input
    @Override
    public void applyInput(IMovable entity, float dirX, float dirY, float speed) {
        if (entity == null) return;
        entity.setDirection(dirX, dirY);
        entity.setSpeed(speed);
    }

    // Applies gravity force
    @Override
    public void applyGravity(IMovable entity, float gravity) {
        if (entity == null) return;
        Vector2 velocity = entity.getVelocity();
        velocity.y -= gravity;
        entity.setVelocity(velocity);
    }

    // Applies velocity over time
    @Override
    public void applyVelocity(IMovable entity, float deltaTime) {
        if (entity == null) return;
        entity.applyVelocity(deltaTime);
    }

    @Override
    public void setDirection(IMovable entity, float dirX, float dirY) {
        if (entity == null) return;
        entity.setDirection(dirX, dirY);
    }

    @Override
    public void setSpeed(IMovable entity, float speed) {
        if (entity == null) return;
        entity.setSpeed(speed);
    }

    // Stops entity movement
    @Override
    public void stop(IMovable entity) {
        if (entity == null) return;
        entity.setVelocity(new Vector2(0, 0));
        entity.setSpeed(0f);
    }
}
