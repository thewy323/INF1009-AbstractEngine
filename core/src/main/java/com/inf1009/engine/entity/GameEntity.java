package com.inf1009.engine.entity;

import com.badlogic.gdx.math.Rectangle;

// Base class for all entities in the engine
public abstract class GameEntity {

    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected Rectangle bounds;
    protected boolean isDestroyed;

    // Initializes entity transform and bounds
    public GameEntity(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle(x, y, width, height);
        this.isDestroyed = false;
    }

    // Per-frame update
    public abstract void update(float deltaTime);


    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public Rectangle getBounds() { return bounds; }

    // Updates entity position
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.bounds.setPosition(x, y);
    }

    // Marks entity for removal
    public void destroy() { this.isDestroyed = true; }

    public boolean isDestroyed() { return isDestroyed; }
}
