package com.inf1009.engine.manager;

import com.inf1009.engine.entity.DynamicEntity;
import com.inf1009.engine.entity.InputState;

public class MovementManager {

    // Simulation bounds (abstract world space)
    private static final float WORLD_WIDTH = 640f;
    private static final float WORLD_HEIGHT = 480f;

    public void applyInput(DynamicEntity entity, InputState input, float dt) {

        float nx = entity.getX() + input.getMoveX() * entity.getSpeed() * dt;
        float ny = entity.getY() + input.getMoveY() * entity.getSpeed() * dt;

        // clamp X within world bounds
        if (nx < 0) nx = 0;
        if (nx + entity.getW() > WORLD_WIDTH) {
            nx = WORLD_WIDTH - entity.getW();
        }

        // clamp Y within world bounds
        if (ny < 0) ny = 0;
        if (ny + entity.getH() > WORLD_HEIGHT) {
            ny = WORLD_HEIGHT - entity.getH();
        }

        entity.setPosition(nx, ny);
    }
}
