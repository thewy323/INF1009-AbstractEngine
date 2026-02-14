package com.inf1009.engine.scene;

//import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class EndScene extends Scene {

    //private Button restartButton;
    //private Button mainMenuButton;

    public EndScene() {}

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
    public void dispose() {}
}
