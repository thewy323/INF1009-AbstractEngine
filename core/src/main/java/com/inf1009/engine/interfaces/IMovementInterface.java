package com.inf1009.engine.interfaces;

public interface IMovementInterface {

    void update(IMovable entity, float deltaTime);

    void applyInput(IMovable entity, float dirX, float dirY, float speed);

    void applyGravity(IMovable entity, float gravity);

    void applyVelocity(IMovable entity, float deltaTime);

    void setDirection(IMovable entity, float dirX, float dirY);

    void setSpeed(IMovable entity, float speed);

    void stop(IMovable entity);
}
