package com.inf1009.engine.interfaces;

import com.inf1009.engine.entity.AbstractGameEntity;
import java.util.List;

public interface IEntityProvider {
    List<AbstractGameEntity> getEntities();
    void addEntity(AbstractGameEntity entity);
    void removeEntity(AbstractGameEntity entity);
}
