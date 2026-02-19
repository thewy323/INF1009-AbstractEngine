package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.inf1009.engine.interfaces.IInputInterface;
import com.inf1009.engine.interfaces.ISceneNavigator;

// Initial menu scene
public class StartScene extends Scene {

    // Navigation abstraction
    private final ISceneNavigator sceneNavigator;
    private final IInputInterface inputInterface;
    private final SpriteBatch batch;
    private final Viewport viewport;

    private ShapeRenderer shape;
    private BitmapFont font;

    // Button dimensions (fixed size, dynamic position)
    private static final float BUTTON_W = 200;
    private static final float BUTTON_H = 60;
    private static final float BUTTON_SPACING = 20;

    // Injects navigation system
    public StartScene(
            ISceneNavigator sceneNavigator,
            IInputInterface inputInterface,
            SpriteBatch batch,
            Viewport viewport
    ) {
        this.sceneNavigator = sceneNavigator;
        this.inputInterface = inputInterface;
        this.batch = batch;
        this.viewport = viewport;
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

        // Use virtual screen size from viewport
        float screenW = viewport.getWorldWidth();
        float screenH = viewport.getWorldHeight();
        float buttonX = (screenW - BUTTON_W) / 2f;  // Center horizontally
        float startY = screenH / 2f + BUTTON_H + BUTTON_SPACING;
        float settingsY = screenH / 2f;
        float exitY = screenH / 2f - BUTTON_H - BUTTON_SPACING;

        // Apply viewport and set projection matrix
        viewport.apply();
        shape.setProjectionMatrix(viewport.getCamera().combined);

        // Draw buttons
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(buttonX, startY, BUTTON_W, BUTTON_H);
        shape.rect(buttonX, settingsY, BUTTON_W, BUTTON_H);
        shape.rect(buttonX, exitY, BUTTON_W, BUTTON_H);
        shape.end();

        // Draw text
        batch.begin();
        font.setColor(0.2f, 0.2f, 0.2f, 1f);  // Dark gray text
        font.draw(batch, "START", buttonX + 70, startY + 38);
        font.draw(batch, "SETTINGS", buttonX + 55, settingsY + 38);
        font.draw(batch, "EXIT", buttonX + 80, exitY + 38);
        batch.end();

        // Button logic
        if (isClicked(buttonX, startY, BUTTON_W, BUTTON_H)) {
            sceneNavigator.navigateTo("sim");
        }
        else if (isClicked(buttonX, settingsY, BUTTON_W, BUTTON_H)) {
            sceneNavigator.navigateTo("settings");
        }
        else if (isClicked(buttonX, exitY, BUTTON_W, BUTTON_H)) {
            sceneNavigator.exitGame();
        }
    }

    // Detects click inside button area using viewport coordinates
    private boolean isClicked(float x, float y, float w, float h) {

        if (!inputInterface.isJustTouched()) return false;

        // Convert screen coordinates to world coordinates using viewport
        Vector2 screenPos = new Vector2(inputInterface.getInputX(), inputInterface.getInputY());
        Vector2 worldCoords = viewport.unproject(screenPos);

        return worldCoords.x >= x && worldCoords.x <= x + w &&
            worldCoords.y >= y && worldCoords.y <= y + h;
    }

    @Override public void hide() {}

    @Override
    public void dispose() {
        if (shape != null) shape.dispose();
        if (font != null) font.dispose();
    }
}
