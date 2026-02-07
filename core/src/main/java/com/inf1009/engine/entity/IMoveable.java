package com.inf1009.engine.entity;

public interface IMoveable {

    // Apply input-based movement for this frame
    void movement(InputState input, float dt);
}
