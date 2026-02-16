package com.inf1009.engine.collision;

import com.inf1009.engine.interfaces.ICollidable;

public class CollisionHandling {

    // Resolves collision between two solid objects
    public void resolve(ICollidable a, ICollidable b) {

        if (!a.isStatic() || !b.isStatic()) return;

        a.onCollision(b);
        b.onCollision(a);
    }
}
