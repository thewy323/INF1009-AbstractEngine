package com.inf1009.engine.manager;

import com.inf1009.engine.entity.DynamicEntity;
import com.inf1009.engine.entity.inputState;

public class MovementManager {
    public void applyInput(DynamicEntity entity, inputState input, float dt) {
        entity.movement(input, dt);
    }
}
