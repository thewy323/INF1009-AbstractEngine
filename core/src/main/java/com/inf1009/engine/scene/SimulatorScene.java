package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.inf1009.engine.entity.DynamicEntity;
import com.inf1009.engine.entity.GameEntity;
import com.inf1009.engine.entity.StaticEntity;
import com.inf1009.engine.interfaces.IEntityProvider;
import com.inf1009.engine.interfaces.IMovementInterface;
import com.inf1009.engine.interfaces.ISceneNavigator;
import com.inf1009.engine.interfaces.IInputInterface;
import com.inf1009.engine.interfaces.ISoundInterface;

import java.util.List;

public class SimulatorScene extends Scene {

    // Engine dependencies
    private final IEntityProvider entityProvider;
    private final IMovementInterface movementManager;
    private final IInputInterface inputManager;
    private final ISoundInterface soundManager;
    private final ISceneNavigator sceneNavigator;
    private final SpriteBatch batch;


    // Simulation state
    private boolean simulationRunning = true;
    private float simulationTime = 0f;

    // Demo entities
    private DynamicEntity controllableEntity;
    private DynamicEntity autonomousEntity;
    private StaticEntity primarySurface;     // Ground
    private StaticEntity secondarySurface;   // Platform

    // Rendering helpers
    private ShapeRenderer shapeRenderer;
    private BitmapFont overlayFont;

    // Constructor injects engine systems
    public SimulatorScene(
        IEntityProvider entityProvider,
        IMovementInterface movementManager,
        IInputInterface inputManager,
        ISoundInterface soundManager,
        SpriteBatch batch,
        ISceneNavigator sceneNavigator
) {
    this.entityProvider = entityProvider;
    this.movementManager = movementManager;
    this.inputManager = inputManager;
    this.soundManager = soundManager;
    this.batch = batch;
    this.sceneNavigator = sceneNavigator;
}


    @Override
    public void show() {

        shapeRenderer = new ShapeRenderer();     // Initialize renderer
        overlayFont = new BitmapFont();          // Initialize font
        entityProvider.clear();                  // Reset world state

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        primarySurface = new StaticEntity(0, 0, w, 40);
        secondarySurface = new StaticEntity(250, 150, 150, 20);

        controllableEntity = new DynamicEntity(200, 200, 45, 45);
        autonomousEntity = new DynamicEntity(w / 2f, h - 80f, 80, 80);

        entityProvider.addEntity(primarySurface);
        entityProvider.addEntity(secondarySurface);
        entityProvider.addEntity(controllableEntity);
        entityProvider.addEntity(autonomousEntity);

        simulationTime = 0f;
        isLoaded = true;
    }

    @Override
    public void render(float dt) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            sceneNavigator.navigateTo("end");    // Scene transition
        }

        clearScreen();

        if (simulationRunning) {
            update(dt);                     // Main simulation update
        }

        renderWorld();                      // Draw entities
        renderOverlay();                    // Draw UI
    }

    // Core simulation logic
    private void update(float dt) {

        simulationTime += dt;
        inputManager.update();

        float moveX = inputManager.getInputState().getMoveX();
        boolean jump = inputManager.getInputState().isJump();

        movementManager.applyInput(controllableEntity, moveX, 0f, 300f);

        if (jump && Math.abs(controllableEntity.getVelocity().y) < 0.1f) {
            Vector2 v = controllableEntity.getVelocity();
            v.y = 500f;
            controllableEntity.setVelocity(v);
        }

        movementManager.applyGravity(controllableEntity, 1200f * dt);
        movementManager.applyGravity(autonomousEntity, 900f * dt);

        movementManager.applyVelocity(controllableEntity, dt);
        movementManager.applyVelocity(autonomousEntity, dt);

        resolveLanding(controllableEntity);   // Handle surface landing
        clampToWorld(controllableEntity);     // Keep inside screen

        if (autonomousEntity.getBounds().overlaps(controllableEntity.getBounds())) {
            soundManager.playSound("audio/hit.wav");
            respawnAutonomousEntity();
            return;
        }

        if (autonomousEntity.getBounds().overlaps(secondarySurface.getBounds())) {
            respawnAutonomousEntity();
            return;
        }

        if (autonomousEntity.getY() <= primarySurface.getY() + primarySurface.getHeight()) {
            respawnAutonomousEntity();
        }
    }

    // Resolves vertical landing on static surfaces
    private void resolveLanding(DynamicEntity entity) {

        List<GameEntity> entities = entityProvider.getEntities();

        for (GameEntity e : entities) {

            if (e instanceof StaticEntity) {

                StaticEntity surface = (StaticEntity) e;

                boolean falling = entity.getVelocity().y <= 0;

                boolean horizontalOverlap =
                        entity.getX() + entity.getWidth() > surface.getX() &&
                        entity.getX() < surface.getX() + surface.getWidth();

                boolean touchingTop =
                        entity.getY() >= surface.getY() + surface.getHeight() - 10 &&
                        entity.getY() <= surface.getY() + surface.getHeight() + 10;

                if (falling && horizontalOverlap && touchingTop) {

                    entity.setPosition(
                            entity.getX(),
                            surface.getY() + surface.getHeight()
                    );

                    Vector2 v = entity.getVelocity();
                    v.y = 0;
                    entity.setVelocity(v);
                }
            }
        }
    }

    // Constrains entity within screen bounds
    private void clampToWorld(DynamicEntity entity) {

        float worldWidth = Gdx.graphics.getWidth();

        if (entity.getX() < 0)
            entity.setPosition(0, entity.getY());

        if (entity.getX() + entity.getWidth() > worldWidth)
            entity.setPosition(worldWidth - entity.getWidth(), entity.getY());

        if (entity.getY() < 0) {
            entity.setPosition(entity.getX(), 0);
            Vector2 v = entity.getVelocity();
            v.y = 0;
            entity.setVelocity(v);
        }
    }

    // Respawns the autonomous entity at a random top position
    private void respawnAutonomousEntity() {

        entityProvider.removeEntity(autonomousEntity);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        autonomousEntity = new DynamicEntity(
                100 + (float)Math.random() * (w - 200),
                h - 80f,
                80, 80
        );

        entityProvider.addEntity(autonomousEntity);
    }

    // Renders all entities
    private void renderWorld() {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        List<GameEntity> entities = entityProvider.getEntities();

        for (GameEntity e : entities) {

            if (e == controllableEntity)
                shapeRenderer.setColor(0f, 0.8f, 1f, 1f);
            else if (e == secondarySurface)
                shapeRenderer.setColor(0.3f, 0.8f, 0.3f, 1f);
            else
                shapeRenderer.setColor(1f, 0f, 0f, 1f);

            shapeRenderer.rect(e.getX(), e.getY(),
                    e.getWidth(), e.getHeight());
        }

        shapeRenderer.end();
    }

    // Renders overlay UI
    private void renderOverlay() {

        batch.begin();

        overlayFont.draw(batch,
                "Press ESC to End Simulation",
                20,
                Gdx.graphics.getHeight() - 20);

        String timeText = "Time: " + String.format("%.2f", simulationTime);

        overlayFont.draw(batch,
                timeText,
                Gdx.graphics.getWidth() - 150,
                Gdx.graphics.getHeight() - 20);

        batch.end();
    }

    // Clears the frame buffer
    private void clearScreen() {
        Gdx.gl.glClearColor(0.1f, 0.12f, 0.14f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override public void hide() {}
    @Override public void resize(int width, int height) {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        overlayFont.dispose();
    }

    // External spawn helper
    public void spawnEntity(GameEntity entity) {
        entityProvider.addEntity(entity);
    }
}
