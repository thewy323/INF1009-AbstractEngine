package com.inf1009.engine.scene;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class StartScene extends AbstractScene {

    private Texture titleTexture;
    private Button startButton;
    private Button settingsButton;

    public StartScene() {}

    @Override
    public void show() {
        isLoaded = true;
    }

    @Override
    public void render(float deltaTime) {
        handleInput();
    }

    public void handleInput() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        if (titleTexture != null) titleTexture.dispose();
    }
}
