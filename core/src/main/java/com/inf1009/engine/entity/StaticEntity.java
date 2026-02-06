package com.inf1009.engine.entity;

public class StaticEntity extends AbstractGameEntity implements ICollidable {

    public StaticEntity(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    @Override
    public void update(float dt) {
        // Static entity does not change state by default
    }

    @Override
    public void onCollision(ICollidable other) {
        // Default: no-op
    }
}

