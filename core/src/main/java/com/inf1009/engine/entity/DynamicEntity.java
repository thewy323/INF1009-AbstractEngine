package com.inf1009.engine.entity;

public class DynamicEntity extends AbstractGameEntity implements ICollidable, Moveable {

    private final float speed;

    public DynamicEntity(float x, float y, float w, float h, float speed) {
        super(x, y, w, h);
        this.speed = speed;
    }

    @Override
    public void movement(inputState input, float dt) {
        float nx = getX() + input.moveX * speed * dt;
        float ny = getY() + input.moveY * speed * dt;
        setPosition(nx, ny);
    }

    @Override
    public void update(float dt) {
        // keep minimal
    }

    @Override
    public void onCollision(ICollidable other) {
        // default no-op
    }
}
