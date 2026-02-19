package com.inf1009.engine.manager;

import com.inf1009.engine.collision.CollisionDetection;
import com.inf1009.engine.collision.CollisionHandling;
import com.inf1009.engine.entity.GameEntity;
import com.inf1009.engine.interfaces.ICollidable;
import com.inf1009.engine.interfaces.ICollidableListener;
import com.inf1009.engine.interfaces.IEntityProvider;

import java.util.ArrayList;
import java.util.List;

// Detects and resolves collisions between entities
public class CollisionManager {

    private IEntityProvider entityProvider;
    private CollisionDetection detection = new CollisionDetection();
    private CollisionHandling handling = new CollisionHandling();
    private List<ICollidableListener> listeners = new ArrayList<>();

    // Injects entity provider
    public CollisionManager(IEntityProvider provider) {
        this.entityProvider = provider;
    }

    // Registers collision listener
    public void addCollisionListener(ICollidableListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeCollisionListener(ICollidableListener listener) {
        listeners.remove(listener);
    }

    // Performs collision detection and notifies listeners
    public void checkCollisions() {

        List<ICollidable> collidables = new ArrayList<>();

        for (GameEntity e : entityProvider.getEntities()) {
            if (e instanceof ICollidable) {
                collidables.add((ICollidable) e);
            }
        }

        for (CollisionDetection.Pair p : detection.detectAll(collidables)) {

            handling.resolve(p.a, p.b);

            for (ICollidableListener listener : listeners) {
                listener.onCollision(p.a, p.b);
            }
        }
    }

    // Called each frame
    public void update() {
        checkCollisions();
    }
}
