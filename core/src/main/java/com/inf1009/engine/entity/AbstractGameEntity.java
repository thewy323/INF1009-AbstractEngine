package com.inf1009.engine.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class AbstractGameEntity {

    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected Rectangle bounds;
    protected boolean destroyed;

    public AbstractGameEntity(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle(x, y, width, height);
        this.destroyed = false;
    }

    public abstract void update(float deltaTime);
    public abstract void render(ShapeRenderer shape);

    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }

    public Rectangle getBounds() { return bounds; }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.bounds.setPosition(x, y);
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        this.bounds.setSize(width, height);
    }

    public void destroy() { this.destroyed = true; }
    public boolean isDestroyed() { return destroyed; }
}
