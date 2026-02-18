package com.inf1009.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inf1009.engine.manager.*;
import com.inf1009.engine.scene.*;
import com.inf1009.engine.sound.Sound;
import com.inf1009.engine.input.KeyboardDevice;

public class GameMaster extends ApplicationAdapter {

    private SpriteBatch batch;
    private EntityManager entityManager;
    private SceneManager sceneManager;
    private MovementManager movementManager;
    private CollisionManager collisionManager;
    private InputManager inputManager;
    private SoundManager soundManager;

    @Override
    public void create() {
        batch = new SpriteBatch();

        entityManager = new EntityManager();
        sceneManager = new SceneManager();
        movementManager = new MovementManager();
        inputManager = new InputManager();
        soundManager = new SoundManager();
        collisionManager = new CollisionManager(entityManager);

        Sound bgm = new Sound("audio/bgm.wav", true, 100);
        Sound hit = new Sound("audio/hit.wav", false, 100);

        soundManager.addSound(bgm);
        soundManager.addSound(hit);
        soundManager.playMusic("audio/bgm.wav");

        inputManager.registerDevice(KeyboardDevice.wasd());

        sceneManager.addScene("start", new StartScene(sceneManager, batch));
        sceneManager.addScene("settings", new SettingsScene(sceneManager, inputManager, soundManager, batch));
        sceneManager.addScene("sim", new SimulatorScene(entityManager, movementManager, inputManager, soundManager, batch, sceneManager));
        sceneManager.addScene("end", new EndScene(sceneManager, batch));
        sceneManager.setScene("start");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float dt = Gdx.graphics.getDeltaTime();

        entityManager.update(dt);
        collisionManager.update();
        sceneManager.update(dt);
    }

    @Override
    public void resize(int width, int height) {
        // Handle window resize
    }

    @Override
    public void pause() {
        // Handle pause (e.g., when app loses focus)
    }

    @Override
    public void resume() {
        // Handle resume (e.g., when app regains focus)
    }

    @Override
    public void dispose() {
        sceneManager.dispose();
        if (batch != null)
            batch.dispose();
    }

    public EntityManager getEntityManager() { return entityManager; }
    public SceneManager getSceneManager() { return sceneManager; }
    public MovementManager getMovementManager() { return movementManager; }
    public CollisionManager getCollisionManager() { return collisionManager; }
    public InputManager getInputManager() { return inputManager; }
    public SoundManager getSoundManager() { return soundManager; }
    public SpriteBatch getBatch() { return batch; }
}
