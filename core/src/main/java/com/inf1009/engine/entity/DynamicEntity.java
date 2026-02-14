package com.inf1009.engine.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.inf1009.engine.interfaces.ICollidable;
import com.inf1009.engine.interfaces.IMovable;

public class DynamicEntity extends AbstractGameEntity implements IMovable, ICollidable {

    private Vector2 velocity = new Vector2();
    private Vector2 acceleration = new Vector2();
    private Vector2 direction = new Vector2(1, 0);
    private float speed = 0f;

    public DynamicEntity(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public Vector2 getVelocity() { return velocity; }

    @Override
    public void setVelocity(Vector2 v) {
        if (v == null) return;
        this.velocity.set(v);
    }

    @Override
    public Vector2 getAcceleration() { return acceleration; }

    @Override
    public void applyAcceleration(Vector2 a) {
        if (a == null) return;
        this.acceleration.add(a);
    }

    public Vector2 getDirection() { return direction; }

    @Override
    public void setDirection(float dx, float dy) {
        this.direction.set(dx, dy);
        if (this.direction.len2() > 0) this.direction.nor();
    }

    public float getSpeed() { return speed; }

    @Override
    public void setSpeed(float speed) { this.speed = speed; }

    @Override
    public void applyVelocity(float dt) {
        velocity.x = direction.x * speed + velocity.x + (acceleration.x * dt);
        velocity.y = direction.y * speed + velocity.y + (acceleration.y * dt);
    }

    @Override
    public void update(float deltaTime) {
        applyVelocity(deltaTime);
        x += velocity.x * deltaTime;
        y += velocity.y * deltaTime;
        bounds.setPosition(x, y);
        acceleration.set(0, 0);
    }

    @Override
    public void render(ShapeRenderer shape) {
        shape.rect(x, y, width, height);
    }

    @Override
    public boolean isSolid() { return true; }

    @Override
    public void onCollision(ICollidable other) {
        onCollisionEnter(other);
    }

    public void onCollisionEnter(ICollidable other) {}
}
