package com.inf1009.engine.manager;

import java.util.List;

public class CollisionHandling {

    public void resolve(List<CollisionPair> pairs) {
        for (CollisionPair p : pairs) {
            p.getA().onCollision(p.getB());
            p.getB().onCollision(p.getA());
        }
    }
}
