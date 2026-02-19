package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.inf1009.engine.interfaces.IInputInterface;
import com.inf1009.engine.interfaces.ISceneNavigator;
import com.inf1009.engine.interfaces.ISoundInterface;

public class SettingsScene extends Scene {

    private final ISceneNavigator sceneNavigator;
    private final IInputInterface inputInterface;
    private final ISoundInterface soundInterface;
    private final SpriteBatch batch;
    private final Viewport viewport;

    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    // Rebinding state
    private boolean waitingForKey = false;
    private String actionToRebind = null;
    private String[] rebindableActions = {"left", "right", "up", "down", "jump"};
    private int[] defaultBindings = {Input.Keys.A, Input.Keys.D, Input.Keys.W, Input.Keys.S, Input.Keys.SPACE};
    private int selectedIndex = 0;

    // Menu items: 5 key bindings + 2 volume sliders = 7 total
    private static final int TOTAL_ITEMS = 7;
    private static final int MASTER_VOL_INDEX = 5;
    private static final int MUSIC_VOL_INDEX = 6;

    // Slider dimensions
    private static final float SLIDER_X = 300;
    private static final float SLIDER_W = 150;
    private static final float SLIDER_H = 10;

    public SettingsScene(
        ISceneNavigator sceneNavigator,
        IInputInterface inputInterface,
        ISoundInterface soundInterface,
        SpriteBatch batch,
        Viewport viewport
    ) {
        this.sceneNavigator = sceneNavigator;
        this.inputInterface = inputInterface;
        this.soundInterface = soundInterface;
        this.batch = batch;
        this.viewport = viewport;
    }

    @Override
    public void show() {
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        isLoaded = true;
    }

    @Override
    public void render(float dt) {
        update(dt);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Apply viewport
        viewport.apply();

        // Get virtual screen size from viewport (no GameMaster dependency)
        float screenW = viewport.getWorldWidth();
        float screenH = viewport.getWorldHeight();

        batch.begin();
        font.draw(batch, "SETTINGS", 280, 400);
        font.draw(batch, "Press M to Mute / U to Unmute", 200, 370);
        font.draw(batch, "Press ESC to Return", 230, 340);
        font.draw(batch, "--- KEY BINDINGS ---", 230, 300);

        for (int i = 0; i < rebindableActions.length; i++) {
            String action = rebindableActions[i];
            int keyCode = inputInterface.getKeyBinding(action);
            String keyName = keyCode >= 0 ? Input.Keys.toString(keyCode) : "UNBOUND";
            String defaultKeyName = Input.Keys.toString(defaultBindings[i]);
            String prefix = (i == selectedIndex) ? "> " : "  ";
            font.draw(batch, prefix + action.toUpperCase() + ": " + keyName + " (Default: " + defaultKeyName + ")", 150, 270 - i * 25);
        }

        // Volume slider labels
        font.draw(batch, "--- VOLUME ---", 250, 130);

        String masterPrefix = (selectedIndex == MASTER_VOL_INDEX) ? "> " : "  ";
        font.draw(batch, masterPrefix + "MASTER: " + soundInterface.getMasterVolume() + "%", 150, 105);

        String musicPrefix = (selectedIndex == MUSIC_VOL_INDEX) ? "> " : "  ";
        font.draw(batch, musicPrefix + "MUSIC:  " + soundInterface.getMusicVolume() + "%", 150, 75);

        if (waitingForKey) {
            font.draw(batch, "Press any key to bind to: " + actionToRebind.toUpperCase(), 150, 35);
        } else {
            font.draw(batch, "UP/DOWN select, ENTER rebind, LEFT/RIGHT adjust volume", 100, 35);
        }

        batch.end();

        // Draw volume slider bars
        viewport.apply();
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        drawSlider(SLIDER_X, 97, soundInterface.getMasterVolume(), selectedIndex == MASTER_VOL_INDEX);
        drawSlider(SLIDER_X, 67, soundInterface.getMusicVolume(), selectedIndex == MUSIC_VOL_INDEX);
    }

    private void update(float dt) {
        inputInterface.update();

        if (waitingForKey) {
            for (int i = 0; i < 256; i++) {
                if (inputInterface.isKeyJustPressed(i)) {
                    inputInterface.rebindKey(actionToRebind, i);
                    waitingForKey = false;
                    actionToRebind = null;
                    break;
                }
            }
            return;
        }

        if (inputInterface.isKeyJustPressed(Input.Keys.UP)) {
            selectedIndex = (selectedIndex - 1 + TOTAL_ITEMS) % TOTAL_ITEMS;
        }
        if (inputInterface.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedIndex = (selectedIndex + 1) % TOTAL_ITEMS;
        }

        // ENTER to rebind keys (only for key binding items)
        if (inputInterface.isKeyJustPressed(Input.Keys.ENTER)) {
            if (selectedIndex < rebindableActions.length) {
                waitingForKey = true;
                actionToRebind = rebindableActions[selectedIndex];
            }
        }

        // LEFT/RIGHT to adjust volume sliders
        if (selectedIndex == MASTER_VOL_INDEX) {
            if (inputInterface.isKeyJustPressed(Input.Keys.LEFT)) {
                soundInterface.setMasterVolume(soundInterface.getMasterVolume() - 10);
            }
            if (inputInterface.isKeyJustPressed(Input.Keys.RIGHT)) {
                soundInterface.setMasterVolume(soundInterface.getMasterVolume() + 10);
            }
        }
        if (selectedIndex == MUSIC_VOL_INDEX) {
            if (inputInterface.isKeyJustPressed(Input.Keys.LEFT)) {
                soundInterface.setMusicVolume(soundInterface.getMusicVolume() - 10);
            }
            if (inputInterface.isKeyJustPressed(Input.Keys.RIGHT)) {
                soundInterface.setMusicVolume(soundInterface.getMusicVolume() + 10);
            }
        }

        if (inputInterface.isKeyJustPressed(Input.Keys.M)) {
            soundInterface.mute();
        }
        if (inputInterface.isKeyJustPressed(Input.Keys.U)) {
            soundInterface.unmute();
        }
        if (inputInterface.isKeyJustPressed(Input.Keys.ESCAPE)) {
            sceneNavigator.navigateTo("start");
        }
    }

    // Draws a volume slider bar
    private void drawSlider(float x, float y, int value, boolean isSelected) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Background bar (dark gray)
        shapeRenderer.setColor(0.3f, 0.3f, 0.3f, 1f);
        shapeRenderer.rect(x, y, SLIDER_W, SLIDER_H);

        // Filled portion (highlight if selected)
        float fillWidth = (value / 100f) * SLIDER_W;
        if (isSelected) {
            shapeRenderer.setColor(0f, 0.8f, 1f, 1f);   // Cyan when selected
        } else {
            shapeRenderer.setColor(0.6f, 0.6f, 0.6f, 1f); // Gray when not selected
        }
        shapeRenderer.rect(x, y, fillWidth, SLIDER_H);

        shapeRenderer.end();
    }

    @Override public void hide() {}

    @Override
    public void dispose() {
        if (font != null) font.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();
    }
}
