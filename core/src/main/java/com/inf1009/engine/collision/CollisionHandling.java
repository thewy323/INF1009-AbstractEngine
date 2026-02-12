package com.inf1009.engine.collision;

import com.inf1009.engine.entity.ICollidable;
import java.util.List;

public class CollisionHandling {

    public void resolve(List<CollisionPair> pairs) {
        for (CollisionPair p : pairs) {
            ICollidable a = p.getA();
            ICollidable b = p.getB();

            // Engine only notifies. 
            a.onCollision(b);
            b.onCollision(a);
        }
    }
}
