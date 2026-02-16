package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inf1009.engine.interfaces.ISceneNavigator;

// Scene displayed after simulation ends
public class EndScene extends Scene {

    // Navigation abstraction
    private final ISceneNavigator sceneNavigator;
    private final SpriteBatch batch;

    // Rendering utilities
    private ShapeRenderer shape;
    private BitmapFont font;

    // Button bounds
    private float restartX = 220, restartY = 240, restartW = 200, restartH = 60;
    private float menuX    = 220, menuY    = 160, menuW    = 200, menuH    = 60;

    // Injects navigation system
    public EndScene(
            ISceneNavigator sceneNavigator,
            SpriteBatch batch
    ) {
        this.sceneNavigator = sceneNavigator;
        this.batch = batch;
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

        // Draw buttons
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(restartX, restartY, restartW, restartH);
        shape.rect(menuX, menuY, menuW, menuH);
        shape.end();

        // Draw labels
        batch.begin();
        font.draw(batch, "SIMULATION ENDED", 230, 340);
        font.draw(batch, "RESTART", restartX + 60, restartY + 38);
        font.draw(batch, "MAIN MENU", menuX + 40, menuY + 38);
        batch.end();

        // Handle clicks
        if (isClicked(restartX, restartY, restartW, restartH)) {
            sceneNavigator.navigateTo("sim");     // Restart simulation
        }
        else if (isClicked(menuX, menuY, menuW, menuH)) {
            sceneNavigator.navigateTo("start");   // Return to menu
        }
    }

    // Detects click inside button area
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
