package com.inf1009.engine.collision;

import com.inf1009.engine.interfaces.ICollidable;

public class CollisionHandling {

    // Notifies both objects that a collision has occurred
    public void resolve(ICollidable a, ICollidable b) {

        // First object reacts to second
        a.onCollision(b);

        // Second object reacts to first
        b.onCollision(a);
    }
}
