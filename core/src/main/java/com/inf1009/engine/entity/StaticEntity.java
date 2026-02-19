package com.inf1009.engine.entity;

import com.inf1009.engine.interfaces.ICollidable;

// Non-moving entity that participates in collision
public class StaticEntity extends GameEntity implements ICollidable {

    // Initializes static entity
    public StaticEntity(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    // Static entities do not update
    @Override
    public void update(float deltaTime) {
    }


    // Identifies this entity as static
    @Override
    public boolean isStatic() {
        return true;
    }

    // Static entity collision response (no internal logic here)
    @Override
    public void onCollision(ICollidable other) {
    }
}
