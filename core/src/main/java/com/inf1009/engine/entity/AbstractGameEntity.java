package com.inf1009.engine.entity;

import com.badlogic.gdx.math.Rectangle;

public abstract class AbstractGameEntity {

    private float x;
    private float y;
    private float w;
    private float h;

    private final Rectangle bounds;
    private boolean destroyed;

    protected AbstractGameEntity(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.bounds = new Rectangle(x, y, w, h);
        this.destroyed = false;
    }

    public abstract void update(float dt);

    public final Rectangle getBounds() {
        return bounds;
    }

    // getters (subclasses + managers can read)
    public final float getX() { return x; }
    public final float getY() { return y; }
    public final float getW() { return w; }
    public final float getH() { return h; }

    public final boolean isDestroyed() { return destroyed; }
    public final void destroy() { destroyed = true; }

    // protected helpers for subclasses
    protected final void setPosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
        bounds.setPosition(newX, newY);
    }

    protected final void setSize(float newW, float newH) {
        this.w = newW;
        this.h = newH;
        bounds.setSize(newW, newH);
    }
}
