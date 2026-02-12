package com.inf1009.engine.manager;

import com.inf1009.engine.entity.InputState;
import com.inf1009.engine.interfaces.IMoveable;

public class MovementManager {

    // Apply input to entity
    public void applyInput(IMoveable entity, InputState input, float dt) {
        if (entity == null || input == null) return;
        entity.movement(input, dt);
    }

    // Generic update wrapper (
    public void update(IMoveable entity, InputState input, float dt) {
        applyInput(entity, input, dt);
    }

    // Stop movement 
    public void stop(IMoveable entity) {
        if (entity == null) return;
        entity.movement(InputState.neutral(), 0f);
    }

    // Placeholder for collision resolution logic
    public void resolveCollision(IMoveable entity, float dt) {
        // Engine-level stub
        // Actual collision behaviour handled inside entities
    }
}
