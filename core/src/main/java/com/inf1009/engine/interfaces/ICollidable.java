package com.inf1009.engine.interfaces;

import com.badlogic.gdx.math.Rectangle;

public interface ICollidable {

    // Provides bounding box for collision detection
    Rectangle getBounds();

    // Indicates whether object blocks other objects
    boolean isSolid();

    // Callback invoked when collision occurs
    void onCollision(ICollidable other);
}
