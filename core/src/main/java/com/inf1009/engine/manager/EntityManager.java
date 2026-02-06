package com.inf1009.engine.manager;

import com.inf1009.engine.entity.AbstractGameEntity;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    private final List<AbstractGameEntity> entities = new ArrayList<>();

    public void addEntity(AbstractGameEntity entity) {
        entities.add(entity);
    }

    public void removeEntity(AbstractGameEntity entity) {
        entities.remove(entity);
    }

    public void update(float dt) {
        for (AbstractGameEntity e : entities) {
            e.update(dt);
        }
    }

    public List<AbstractGameEntity> getEntities() {
        return entities;
    }

    public void clear() {
        entities.clear();
    }
}
