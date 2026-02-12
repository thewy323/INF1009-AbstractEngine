package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.engine.GameMaster;
import com.inf1009.engine.entity.AbstractGameEntity;
import com.inf1009.engine.entity.DynamicEntity;
import com.inf1009.engine.entity.ICollidable;
import com.inf1009.engine.entity.StaticEntity;
import com.inf1009.engine.entity.InputState;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic simulation screen.
 * 
 * PURPOSE:
 * Demonstrates engine capabilities only:
 * - Multiple entities
 * - Input system
 * - Movement manager
 * - Collision manager
 * - Rendering
 * 
 * No scoring, no spawning, no game mechanics.
 */
public class SimulatorScreen implements IScreen {

    private final GameMaster game;
    private ShapeRenderer shape;

    // Two controllable dynamic entities
    private DynamicEntity entityA;
    private DynamicEntity entityB;

    // Simple static ground
    private StaticEntity ground;

    private static final float SCREEN_W = 640f;
    private static final float SCREEN_H = 480f;

    private boolean initialized = false;

    public SimulatorScreen(GameMaster game) {
        this.game = game;
    }

    @Override
    public void show() {

        if (shape == null) {
            shape = new ShapeRenderer();
        }

        if (!initialized) {
            initialized = true;

            game.getEntityManager().clear();

            // Create two generic movable entities
            entityA = new DynamicEntity(100, 150, 30, 30, 220f);
            entityB = new DynamicEntity(220, 150, 30, 30, 220f);

            // Static ground for collision demo
            ground = new StaticEntity(0, 40, SCREEN_W, 40);

            game.getEntityManager().addEntity(entityA);
            game.getEntityManager().addEntity(entityB);
            game.getEntityManager().addEntity(ground);
        }
    }

    @Override
    public void render(float dt) {

        // Simple exit navigation
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.getSceneManager().setScreen("end");
            return;
        }

        clearScreen();

        handleInput(dt);

        // Update all entities via EntityManager
        game.getEntityManager().update(dt);

        // Run collision system
        handleCollisions();

        // Application-level boundary control
        clampToScreen(entityA);
        clampToScreen(entityB);

        renderWorld();
    }

    /**
     * Demonstrates IO + Movement managers.
     * Device 0 and 1 control separate entities.
     */
    private void handleInput(float dt) {

        InputState inputA = game.getIOManager().readDevice(0);
        InputState inputB = game.getIOManager().readDevice(1);

        game.getMovementManager().applyInput(entityA, inputA, dt);
        game.getMovementManager().applyInput(entityB, inputB, dt);
    }

    /**
     * Collects collidable entities and runs collision manager.
     */
    private void handleCollisions() {

        List<ICollidable> collidables = new ArrayList<>();

        for (AbstractGameEntity e : game.getEntityManager().getEntities()) {
            if (e instanceof ICollidable) {
                collidables.add((ICollidable) e);
            }
        }

        game.getCollisionManager().update(collidables);
    }

    /**
     * Application controls world bounds.
     * Engine does NOT hardcode world size.
     */
    private void clampToScreen(DynamicEntity e) {

        float x = e.getX();
        float y = e.getY();

        if (x < 0) e.setPosition(0, y);
        if (x + e.getW() > SCREEN_W)
            e.setPosition(SCREEN_W - e.getW(), y);

        if (y < 0) e.setPosition(x, 0);
        if (y + e.getH() > SCREEN_H)
            e.setPosition(x, SCREEN_H - e.getH());
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.10f, 0.12f, 0.14f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * Simple rectangle rendering for demonstration.
     */
    private void renderWorld() {

        shape.begin(ShapeRenderer.ShapeType.Filled);

        shape.rect(ground.getX(), ground.getY(), ground.getW(), ground.getH());

        shape.rect(entityA.getX(), entityA.getY(), entityA.getW(), entityA.getH());
        shape.rect(entityB.getX(), entityB.getY(), entityB.getW(), entityB.getH());

        shape.end();
    }

    @Override public void hide() {}

    @Override
    public void dispose() {
        if (shape != null) shape.dispose();
    }
}
