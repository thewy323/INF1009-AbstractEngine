package com.inf1009.engine.interfaces;

import com.badlogic.gdx.math.Vector2;

public interface IMovable {

    Vector2 getVelocity();
    void setVelocity(Vector2 v);

    Vector2 getDirection();
    void setDirection(float dirX, float dirY);

    float getSpeed();
    void setSpeed(float speed);

    void applyVelocity(float dt);
}
