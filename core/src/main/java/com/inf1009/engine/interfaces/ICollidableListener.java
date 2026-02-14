package com.inf1009.engine.interfaces;

import com.inf1009.engine.entity.GameEntity;

public interface ICollidableListener {
    void onCollisionEnter(GameEntity entity1, GameEntity entity2);
    void onCollisionStay(GameEntity entity1, GameEntity entity2);
    void onCollisionExit(GameEntity entity1, GameEntity entity2);
}
