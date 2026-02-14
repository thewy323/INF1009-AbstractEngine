package com.inf1009.engine.manager;

import com.inf1009.engine.entity.AbstractGameEntity;
import com.inf1009.engine.interfaces.IEntityProvider;
import java.util.ArrayList;
import java.util.List;

public class EntityManager implements IEntityProvider {

    private List<AbstractGameEntity> entities = new ArrayList<>();
    private List<AbstractGameEntity> entitiesToAdd = new ArrayList<>();
    private List<AbstractGameEntity> entitiesToRemove = new ArrayList<>();

    public void update(float deltaTime) {
        if (!entitiesToAdd.isEmpty()) {
            entities.addAll(entitiesToAdd);
            entitiesToAdd.clear();
        }
        if (!entitiesToRemove.isEmpty()) {
            entities.removeAll(entitiesToRemove);
            entitiesToRemove.clear();
        }

        for (AbstractGameEntity e : entities) e.update(deltaTime);
        removeDeadEntities();
    }

    public void addEntity(AbstractGameEntity entity) {
        if (entity != null) entitiesToAdd.add(entity);
    }

    public void removeEntity(AbstractGameEntity entity) {
        if (entity != null) entitiesToRemove.add(entity);
    }

    public List<AbstractGameEntity> getEntities() {
        return entities;
    }

    public void removeDeadEntities() {
        entities.removeIf(AbstractGameEntity::isDestroyed);
    }

    public void clear() {
        entities.clear();
        entitiesToAdd.clear();
        entitiesToRemove.clear();
    }
}
