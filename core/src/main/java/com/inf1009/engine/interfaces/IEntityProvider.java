package com.inf1009.engine.interfaces;

import com.inf1009.engine.entity.GameEntity;
import java.util.List;

public interface IEntityProvider {

    List<GameEntity> getEntities();

    void addEntity(GameEntity entity);

    void clear();

    void removeEntity(GameEntity entity);

    // Factory methods to create entities through abstraction
    GameEntity createDynamicEntity(float x, float y, float width, float height);

    GameEntity createStaticEntity(float x, float y, float width, float height);
}
