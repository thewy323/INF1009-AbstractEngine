package com.inf1009.engine.manager;

import com.inf1009.engine.entity.AbstractGameEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Manages lifecycle of all game entities
public class EntityManager {

    // Internal entity list (owned by manager)
    private final List<AbstractGameEntity> entities = new ArrayList<>();

    // Queues to avoid modifying entities while iterating
    private final List<AbstractGameEntity> entitiesToAdd = new ArrayList<>();
    private final List<AbstractGameEntity> entitiesToRemove = new ArrayList<>();

    // Queue a new entity to be added
    public void addEntity(AbstractGameEntity entity) {
        if (entity == null) return;
        entitiesToAdd.add(entity);
    }

    // Queue a specific entity to be removed
    public void removeEntity(AbstractGameEntity entity) {
        if (entity == null) return;
        entitiesToRemove.add(entity);
    }

    // Remove all entities (scene reset)
    public void clear() {
        entities.clear();
        entitiesToAdd.clear();
        entitiesToRemove.clear();
    }

    // Read-only access for rendering / collision
    public List<AbstractGameEntity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    // Update all entities and apply queued add/remove safely
    public void update(float dt) {

        // 1) Apply pending additions before update (optional but common)
        if (!entitiesToAdd.isEmpty()) {
            entities.addAll(entitiesToAdd);
            entitiesToAdd.clear();
        }

        // 2) Update entities
        for (AbstractGameEntity e : entities) {
            e.update(dt);
        }

        // 3) Queue destroyed entities for removal
        for (AbstractGameEntity e : entities) {
            if (e.isDestroyed()) {
                entitiesToRemove.add(e);
            }
        }

        // 4) Apply removals
        if (!entitiesToRemove.isEmpty()) {
            entities.removeAll(entitiesToRemove);
            entitiesToRemove.clear();
        }
    }
}
