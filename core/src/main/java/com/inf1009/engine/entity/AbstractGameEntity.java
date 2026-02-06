package com.inf1009.engine.entity;

import com.badlogic.gdx.math.Rectangle;

public abstract class AbstractGameEntity {

    protected float x;
    protected float y;
    protected float width;
    protected float height;

    protected Rectangle bounds;
    protected boolean isDestroyed;

    public AbstractGameEntity(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle(x, y, width, height);
        this.isDestroyed = false;
    }

    public abstract void update(float deltaTime);
    public abstract void render();

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
