package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inf1009.engine.interfaces.ISceneNavigator;

// Initial menu scene
public class StartScene extends Scene {

    // Navigation abstraction
    private final ISceneNavigator sceneNavigator;
    private final SpriteBatch batch;

    private ShapeRenderer shape;
    private BitmapFont font;

    // Buttons
    private float startX = 220, startY = 280, startW = 200, startH = 60;
    private float settingsX = 220, settingsY = 200, settingsW = 200, settingsH = 60;
    private float exitX  = 220, exitY  = 120, exitW  = 200, exitH  = 60;

    // Injects navigation system
    public StartScene(
            ISceneNavigator sceneNavigator,
            SpriteBatch batch
    ) {
        this.sceneNavigator = sceneNavigator;
        this.batch = batch;
    }

    @Override
    public void show() {
        shape = new ShapeRenderer();
        font = new BitmapFont();
        isLoaded = true;
    }

    @Override
    public void render(float dt) {

        Gdx.gl.glClearColor(0.08f, 0.08f, 0.08f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw buttons
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(startX, startY, startW, startH);
        shape.rect(settingsX, settingsY, settingsW, settingsH);
        shape.rect(exitX, exitY, exitW, exitH);
        shape.end();

        // Draw text
        batch.begin();
        font.draw(batch, "START", startX + 70, startY + 38);
        font.draw(batch, "SETTINGS", settingsX + 55, settingsY + 38);
        font.draw(batch, "EXIT", exitX + 80, exitY + 38);
        batch.end();

        // Button logic
        if (isClicked(startX, startY, startW, startH)) {
            sceneNavigator.navigateTo("sim");
        }
        else if (isClicked(settingsX, settingsY, settingsW, settingsH)) {
            sceneNavigator.navigateTo("settings");
        }
        else if (isClicked(exitX, exitY, exitW, exitH)) {
            Gdx.app.exit();
        }
    }

    private boolean isClicked(float x, float y, float w, float h) {

        if (!Gdx.input.justTouched()) return false;

        float mx = Gdx.input.getX();
        float my = Gdx.graphics.getHeight() - Gdx.input.getY();

        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    @Override public void hide() {}

    @Override
    public void dispose() {
        if (shape != null) shape.dispose();
        if (font != null) font.dispose();
    }

    @Override
    public void resize(int width, int height) {}
}
