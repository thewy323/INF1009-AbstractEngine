package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inf1009.engine.interfaces.IInputManager;
import com.inf1009.engine.interfaces.ISceneNavigator;
import com.inf1009.engine.interfaces.ISoundManager;

// Scene for runtime configuration such as audio control
public class SettingsScene extends Scene {

    // Engine systems used by settings
    private final ISceneNavigator sceneNavigator;
    private final IInputManager inputManager;
    private final ISoundManager soundManager;
    private final SpriteBatch batch;

    // Font for UI rendering
    private BitmapFont font;

    // Injects required systems
    public SettingsScene(
            ISceneNavigator sceneNavigator,
            IInputManager inputManager,
            ISoundManager soundManager,
            SpriteBatch batch
    ) {
        this.sceneNavigator = sceneNavigator;
        this.inputManager = inputManager;
        this.soundManager = soundManager;
        this.batch = batch;
    }

    @Override
    public void show() {
        font = new BitmapFont();   // Initialize font
        isLoaded = true;
    }

    @Override
    public void render(float dt) {

        update(dt);                // Handle input logic

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "SETTINGS", 300, 320);
        font.draw(batch, "Press M to Mute", 260, 280);
        font.draw(batch, "Press U to Unmute", 250, 250);
        font.draw(batch, "Press ESC to Return", 230, 220);
        batch.end();
    }

    // Handles settings input actions
    private void update(float dt) {

        inputManager.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            soundManager.mute();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            soundManager.unmute();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            sceneNavigator.navigateTo("start");   // Navigate via abstraction
        }
    }

    @Override public void hide() {}
    @Override public void resize(int w, int h) {}

    @Override
    public void dispose() {
        if (font != null) font.dispose();
    }
}
