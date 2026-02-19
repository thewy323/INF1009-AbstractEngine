package com.inf1009.engine.scene;

// Base abstraction for all scenes in the engine
public abstract class Scene {

    // Tracks whether scene resources are initialized
    protected boolean isLoaded = false;

    // Called when scene becomes active
    public abstract void show();

    // Called every frame
    public abstract void render(float deltaTime);

    // Called when scene is hidden
    public abstract void hide();

    // Releases scene resources
    public abstract void dispose();

    // Returns loading state
    public boolean isLoaded() {
        return isLoaded;
    }
}
