package com.inf1009.engine.collision;

import com.inf1009.engine.interfaces.ICollidable;

public class CollisionHandling {

    // Resolves collision between two solid objects
    public void resolve(ICollidable a, ICollidable b) {

    if (a == null || b == null) return;

    // Dynamic vs Static
    if (!a.isStatic() && b.isStatic()) {
        a.onCollision(b);
    }

    else if (a.isStatic() && !b.isStatic()) {
        b.onCollision(a);
    }

    // Dynamic vs Dynamic
    else if (!a.isStatic() && !b.isStatic()) {
        a.onCollision(b);
        b.onCollision(a);
    }

    // Static vs Static → do nothing
}

}
