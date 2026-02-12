package com.inf1009.engine.collision;

import com.inf1009.engine.entity.ICollidable;

public class CollisionHandling {

    // Engine only notifies both objects
    public void resolve(ICollidable a, ICollidable b) {
        a.onCollision(b);
        b.onCollision(a);
    }
}
