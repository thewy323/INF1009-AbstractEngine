package com.inf1009.engine.manager;

import com.inf1009.engine.entity.ICollidable;
import java.util.List;

public class CollisionManager {

    private final CollisionDetection detection;
    private final CollisionHandling handling;

    public CollisionManager() {
        this.detection = new CollisionDetection();
        this.handling = new CollisionHandling();
    }

    public void update(List<ICollidable> collidables) {
        List<CollisionPair> pairs = detection.detectAll(collidables);
        handling.resolve(pairs);
    }
}
