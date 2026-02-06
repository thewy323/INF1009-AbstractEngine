package com.inf1009.engine.entity;

public class DynamicEntity extends AbstractGameEntity
        implements ICollidable, Moveable {

    protected float velocityX;
    protected float velocityY;

    public DynamicEntity(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void movement() {
        x += velocityX;
        y += velocityY;
        bounds.setPosition(x, y);
    }

    @Override
    public void update(float deltaTime) {
        movement();
    }

    @Override
    public void render() {
        // Rendering handled externally
    }

    @Override
    public void onCollision(ICollidable other) {
        // Default dynamic collision response
    }
}
