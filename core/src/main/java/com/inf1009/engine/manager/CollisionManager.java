package com.inf1009.engine.manager;

import com.inf1009.engine.collision.CollisionDetection;
import com.inf1009.engine.collision.CollisionHandling;
import com.inf1009.engine.interfaces.ICollidable;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {

    // Registered collidable objects
    private final List<ICollidable> collidables = new ArrayList<>();

    // Detection and resolution helpers
    private final CollisionDetection detection;
    private final CollisionHandling handling;

    public CollisionManager() {
        this.detection = new CollisionDetection();
        this.handling = new CollisionHandling();
    }

    // Registers object for collision checks
    public void register(ICollidable c) {
        if (c == null) return;
        if (!collidables.contains(c)) {
            collidables.add(c);
        }
    }

    // Unregisters object
    public void unregister(ICollidable c) {
        collidables.remove(c);
    }

    // Clears all registered objects
    public void clear() {
        collidables.clear();
    }

    // Runs detection using internal registry
    public void update() {
        if (collidables.isEmpty()) return;
        detection.detectAll(collidables, handling);
    }

    // Runs detection using external list
    public void update(List<ICollidable> list) {
        if (list == null || list.isEmpty()) return;
        detection.detectAll(list, handling);
    }
}
