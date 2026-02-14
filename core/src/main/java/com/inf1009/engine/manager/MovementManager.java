package com.inf1009.engine.manager;

import com.badlogic.gdx.math.Vector2;
import com.inf1009.engine.input.InputState;
import com.inf1009.engine.interfaces.IMovable;

public class MovementManager {

    public void updateEntity(IMovable entity, InputState inputState, float dt) {
        if (entity == null || inputState == null) return;
        setDirection(entity, inputState.moveX, inputState.moveY);
        if (inputState.jump) entity.applyAcceleration(new Vector2(0, 250f));
        applyAcceleration(entity, dt);
        applyVelocity(entity, dt);
    }

    public void applyVelocity(IMovable entity, float dt) {
        if (entity == null) return;
        entity.applyVelocity(dt);
    }

    public void applyAcceleration(IMovable entity, float dt) {
        if (entity == null) return;
        Vector2 a = entity.getAcceleration();
        if (a == null) return;
        entity.setVelocity(entity.getVelocity().add(a.x * dt, a.y * dt));
    }

    public void setDirection(IMovable entity, float dx, float dy) {
        if (entity == null) return;
        entity.setDirection(dx, dy);
    }

    public void stopEntity(IMovable entity) {
        if (entity == null) return;
        entity.setVelocity(new Vector2(0, 0));
        entity.setSpeed(0f);
    }
}
