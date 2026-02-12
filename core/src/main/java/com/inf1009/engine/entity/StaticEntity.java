package com.inf1009.engine.entity;

import com.inf1009.engine.interfaces.ICollidable;

public class StaticEntity extends AbstractGameEntity implements ICollidable {

    // Defines whether entity blocks others
    private boolean solid = true;

    public StaticEntity(float x, float y, float w, float h) {
        super(x, y, w, h);
    }

    // Static entities have no per-frame behaviour
    @Override
    public void update(float dt) {
    }

    @Override
    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    // Default no reaction
    @Override
    public void onCollision(ICollidable other) {
    }
}
