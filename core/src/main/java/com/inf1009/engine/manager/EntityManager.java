package com.inf1009.engine.manager;

import com.inf1009.engine.entity.AbstractGameEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityManager {

    // Stores all active entities
    private final List<AbstractGameEntity> entities = new ArrayList<>();

    public void addEntity(AbstractGameEntity e) {
        if (e == null) return;
        entities.add(e);
    }

    public void removeEntity(AbstractGameEntity e) {
        entities.remove(e);
    }

    public void clear() {
        entities.clear();
    }

    // Read-only access for other systems
    public List<AbstractGameEntity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    // Updates all entities and removes destroyed ones
    public void update(float dt) {
        for (AbstractGameEntity e : entities) {
            e.update(dt);
        }

        entities.removeIf(AbstractGameEntity::isDestroyed);
    }
}
