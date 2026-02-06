package com.inf1009.engine.manager;

import com.inf1009.engine.entity.ICollidable;

public class CollisionPair {
    private final ICollidable a;
    private final ICollidable b;

    public CollisionPair(ICollidable a, ICollidable b) {
        this.a = a;
        this.b = b;
    }

    public ICollidable getA() { return a; }
    public ICollidable getB() { return b; }
}
