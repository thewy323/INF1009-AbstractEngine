package com.inf1009.engine.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.engine.interfaces.ICollidable;

public class StaticEntity extends AbstractGameEntity implements ICollidable {

    private boolean isSolid;

    public StaticEntity(float x, float y, float width, float height, boolean isSolid) {
        super(x, y, width, height);
        this.isSolid = isSolid;
    }

    @Override
    public void update(float deltaTime) {}

    @Override
    public void render(ShapeRenderer shape) {
        shape.rect(x, y, width, height);
    }

    @Override
    public boolean isSolid() { return isSolid; }

    public void setSolid(boolean solid) { this.isSolid = solid; }

    @Override
    public void onCollision(ICollidable other) {}
}
