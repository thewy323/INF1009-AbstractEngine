package com.inf1009.engine.manager;

import com.inf1009.engine.collision.CollisionDetection;
import com.inf1009.engine.collision.CollisionHandling;
import com.inf1009.engine.interfaces.ICollidable;
import java.util.ArrayList;
import java.util.List;

public class CollisionManager {

    private List<ICollidable> collidables = new ArrayList<>();
    private CollisionDetection detection = new CollisionDetection();
    private CollisionHandling handling = new CollisionHandling();

    public void register(ICollidable c) {
        if (c != null && !collidables.contains(c)) collidables.add(c);
    }

    public void unregister(ICollidable c) {
        collidables.remove(c);
    }

    public void checkCollisions() {
        for (CollisionDetection.Pair p : detection.detectAll(collidables)) {
            handling.resolve(p.a, p.b);
        }
    }

    public void clearCollisions() {
        collidables.clear();
    }

    public void update() {
        checkCollisions();
    }
}
