package com.inf1009.engine.interfaces;

public interface IScreen {

    // Called when screen becomes active
    void show();

    // Per-frame update and rendering
    void render(float dt);

    // Called when screen is hidden
    void hide();

    // Cleanup resources
    void dispose();
}
