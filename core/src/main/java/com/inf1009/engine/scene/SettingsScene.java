package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inf1009.engine.interfaces.IInputInterface;
import com.inf1009.engine.interfaces.ISceneNavigator;
import com.inf1009.engine.interfaces.ISoundInterface;

public class SettingsScene extends Scene {

    private final ISceneNavigator sceneNavigator;
    private final IInputInterface inputManager;
    private final ISoundInterface soundManager;
    private final SpriteBatch batch;

    private BitmapFont font;

    // Rebinding state
    private boolean waitingForKey = false;
    private String actionToRebind = null;
    private String[] rebindableActions = {"left", "right", "up", "down", "jump"};
    private int selectedIndex = 0;

    public SettingsScene(
        ISceneNavigator sceneNavigator,
        IInputInterface inputManager,
        ISoundInterface soundManager,
        SpriteBatch batch
    ) {
        this.sceneNavigator = sceneNavigator;
        this.inputManager = inputManager;
        this.soundManager = soundManager;
        this.batch = batch;
    }

    @Override
    public void show() {
        font = new BitmapFont();
        isLoaded = true;
    }

    @Override
    public void render(float dt) {
        update(dt);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "SETTINGS", 280, 400);
        font.draw(batch, "Press M to Mute / U to Unmute", 200, 370);
        font.draw(batch, "Press ESC to Return", 230, 340);
        font.draw(batch, "--- KEY BINDINGS ---", 230, 300);

        for (int i = 0; i < rebindableActions.length; i++) {
            String action = rebindableActions[i];
            int keyCode = inputManager.getKeyBinding(action);
            String keyName = keyCode >= 0 ? Input.Keys.toString(keyCode) : "UNBOUND";
            String prefix = (i == selectedIndex) ? "> " : "  ";
            font.draw(batch, prefix + action.toUpperCase() + ": " + keyName, 220, 270 - i * 25);
        }

        if (waitingForKey) {
            font.draw(batch, "Press any key to bind to: " + actionToRebind.toUpperCase(), 180, 100);
        } else {
            font.draw(batch, "UP/DOWN to select, ENTER to rebind", 180, 100);
        }

        batch.end();
    }

    private void update(float dt) {
        inputManager.update();

        if (waitingForKey) {
            for (int i = 0; i < 256; i++) {
                if (Gdx.input.isKeyJustPressed(i)) {
                    inputManager.rebindKey(actionToRebind, i);
                    waitingForKey = false;
                    actionToRebind = null;
                    break;
                }
            }return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedIndex = (selectedIndex - 1 + rebindableActions.length) % rebindableActions.length;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedIndex = (selectedIndex + 1) % rebindableActions.length;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            waitingForKey = true;
            actionToRebind = rebindableActions[selectedIndex];
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            soundManager.mute();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            soundManager.unmute();
        }if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            sceneNavigator.navigateTo("start");
        }
    }

    @Override public void hide() {}
    @Override public void resize(int w, int h) {}

    @Override
    public void dispose() {
        if (font != null) font.dispose();
    }
}
