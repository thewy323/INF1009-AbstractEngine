package com.inf1009.engine.entity;

public class StaticEntity extends AbstractGameEntity implements ICollidable {

    public StaticEntity(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void update(float deltaTime) {
        // Static entities do not update position
    }

    @Override
    public void render() {
        // Rendering handled externally
    }

    @Override
    public void onCollision(ICollidable other) {
        // Default collision response
    }
}
