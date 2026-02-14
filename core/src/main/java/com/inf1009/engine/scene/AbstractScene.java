package com.inf1009.engine.scene;

public abstract class AbstractScene {

    protected boolean isLoaded = false;

    public abstract void show();
    public abstract void render(float deltaTime);
    public abstract void hide();
    public abstract void dispose();

    public boolean isLoaded() {
        return isLoaded;
    }
}
