package com.inf1009.engine.interfaces;

import com.inf1009.engine.entity.AbstractGameEntity;

public interface ICollidableListener {
    void onCollisionEnter(AbstractGameEntity entity1, AbstractGameEntity entity2);
    void onCollisionStay(AbstractGameEntity entity1, AbstractGameEntity entity2);
    void onCollisionExit(AbstractGameEntity entity1, AbstractGameEntity entity2);
}
