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

// Scene displayed after simulation ends
public class EndScene extends Scene {

    // Navigation abstraction
    private final ISceneNavigator sceneNavigator;
    private final IInputInterface inputInterface;
    private final SpriteBatch batch;
    private final Viewport viewport;

    // Rendering utilities
    private ShapeRenderer shape;
    private BitmapFont font;

    // Button dimensions (fixed size, dynamic position)
    private static final float BUTTON_W = 200;
    private static final float BUTTON_H = 60;
    private static final float BUTTON_SPACING = 20;

    // Injects navigation system
    public EndScene(
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

        if (shape == null) shape = new ShapeRenderer();
        if (font == null) font = new BitmapFont();

        isLoaded = true;
    }

    @Override
    public void render(float dt) {

        // Clear screen
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.05f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Use virtual screen size from viewport
        float screenW = viewport.getWorldWidth();
        float screenH = viewport.getWorldHeight();
        float buttonX = (screenW - BUTTON_W) / 2f;  // Center horizontally
        float restartY = screenH / 2f + BUTTON_SPACING / 2f;
        float menuY = screenH / 2f - BUTTON_H - BUTTON_SPACING / 2f;

        // Apply viewport and set projection matrix
        viewport.apply();
        shape.setProjectionMatrix(viewport.getCamera().combined);

        // Draw buttons
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(buttonX, restartY, BUTTON_W, BUTTON_H);
        shape.rect(buttonX, menuY, BUTTON_W, BUTTON_H);
        shape.end();

        // Draw labels
        batch.begin();
        font.setColor(1f, 1f, 1f, 1f);  // White text for title
        font.draw(batch, "SIMULATION ENDED", (screenW - 120) / 2f, screenH / 2f + BUTTON_H + BUTTON_SPACING + 40);
        font.setColor(0.2f, 0.2f, 0.2f, 1f);  // Dark gray text for buttons
        font.draw(batch, "RESTART", buttonX + 60, restartY + 38);
        font.draw(batch, "MAIN MENU", buttonX + 50, menuY + 38);
        batch.end();

        // Handle clicks
        if (isClicked(buttonX, restartY)) {
            sceneNavigator.navigateTo("sim");     // Restart simulation
        }
        else if (isClicked(buttonX, menuY)) {
            sceneNavigator.navigateTo("start");   // Return to menu
        }
    }

    // Detects click inside button area using viewport coordinates
    private boolean isClicked(float x, float y) {

        if (!inputInterface.isJustTouched()) return false;

        // Convert screen coordinates to world coordinates using viewport
        Vector2 screenPos = new Vector2(inputInterface.getInputX(), inputInterface.getInputY());
        Vector2 worldCoords = viewport.unproject(screenPos);

        return worldCoords.x >= x && worldCoords.x <= x + EndScene.BUTTON_W &&
               worldCoords.y >= y && worldCoords.y <= y + EndScene.BUTTON_H;
    }

    @Override public void hide() {}

    @Override
    public void dispose() {
        if (shape != null) shape.dispose();
        if (font != null) font.dispose();
    }
}
