package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.inf1009.engine.entity.GameEntity;
import com.inf1009.engine.input.InputState;
import com.inf1009.engine.interfaces.IEntityProvider;
import com.inf1009.engine.interfaces.IMovable;
import com.inf1009.engine.interfaces.IMovementInterface;
import com.inf1009.engine.interfaces.ISceneNavigator;
import com.inf1009.engine.interfaces.IInputInterface;
import com.inf1009.engine.interfaces.ISoundInterface;

import java.util.List;

public class SimulatorScene extends Scene {

    // Engine dependencies
    private final IEntityProvider entityProvider;
    private final IMovementInterface movementInterface;
    private final IInputInterface inputInterface;
    private final ISoundInterface soundInterface;
    private final ISceneNavigator sceneNavigator;
    private final SpriteBatch batch;
    private final Viewport viewport;


    // Simulation state
    private float simulationTime = 0f;
    private int score = 0;

    // Demo entities (stored as GameEntity, cast to IMovable when needed)
    private GameEntity controllableEntity;
    private GameEntity autonomousEntity;
    private GameEntity mouseEntity;
    private final Vector2 mouseTarget = new Vector2();
    private boolean hasMouseTarget = false;
    private GameEntity primarySurface;     // Ground
    private GameEntity secondarySurface;   // Platform

    // Rendering helpers
    private ShapeRenderer shapeRenderer;
    private BitmapFont overlayFont;

    // Constructor injects engine systems
    public SimulatorScene(
        IEntityProvider entityProvider,
        IMovementInterface movementInterface,
        IInputInterface inputInterface,
        ISoundInterface soundInterface,
        SpriteBatch batch,
        ISceneNavigator sceneNavigator,
        Viewport viewport
) {
    this.entityProvider = entityProvider;
    this.movementInterface = movementInterface;
    this.inputInterface = inputInterface;
    this.soundInterface = soundInterface;
    this.batch = batch;
    this.sceneNavigator = sceneNavigator;
    this.viewport = viewport;
}


    @Override
    public void show() {

        shapeRenderer = new ShapeRenderer();     // Initialize renderer
        overlayFont = new BitmapFont();          // Initialize font
        entityProvider.clear();                  // Reset world state

        float w = viewport.getWorldWidth();
        float h = viewport.getWorldHeight();

        // Create entities through entityProvider factory methods
        primarySurface = entityProvider.createStaticEntity(0, 0, w, 40);
        secondarySurface = entityProvider.createStaticEntity(250, 150, 150, 20);

        controllableEntity = entityProvider.createDynamicEntity(200, 200, 45, 45);
        autonomousEntity = entityProvider.createDynamicEntity(w / 2f, h - 80f, 80, 80);
        mouseEntity = entityProvider.createDynamicEntity(400, 200, 35, 35);

        entityProvider.addEntity(primarySurface);
        entityProvider.addEntity(secondarySurface);
        entityProvider.addEntity(controllableEntity);
        entityProvider.addEntity(autonomousEntity);
        entityProvider.addEntity(mouseEntity);

        simulationTime = 0f;
        score = 0;
        isLoaded = true;
    }

    @Override
    public void render(float dt) {

        if (inputInterface.isKeyJustPressed(Input.Keys.ESCAPE)) {
            sceneNavigator.navigateTo("end");    // Scene transition
        }

        clearScreen();

        // Always update; the previous flag was always true
        update(dt);                     // Main simulation update

        renderWorld();                      // Draw entities
        renderOverlay();                    // Draw UI
    }

    // Core simulation logic
    private void update(float dt) {

        simulationTime += dt;
        inputInterface.update();

        // Handle mouse click for mouseEntity using InputManager
        if (inputInterface.isMouseLeftJustClicked()) {
            // Get raw screen click position and convert to world coordinates using viewport
            Vector2 screenPos = new Vector2(inputInterface.getInputX(), inputInterface.getInputY());
            Vector2 worldPos = viewport.unproject(screenPos);
            mouseTarget.set(worldPos.x - mouseEntity.getWidth() / 2f, worldPos.y - mouseEntity.getHeight() / 2f);
            hasMouseTarget = true;
        }

        // Move mouseEntity toward target
        if (hasMouseTarget) {
            float dx = mouseTarget.x - mouseEntity.getX();
            float dy = mouseTarget.y - mouseEntity.getY();
            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            if (distance > 2f) {
                float speed = 200f;
                float moveX = (dx / distance) * speed * dt;
                float moveY = (dy / distance) * speed * dt;

                // Don't overshoot
                if (Math.abs(moveX) > Math.abs(dx)) moveX = dx;
                if (Math.abs(moveY) > Math.abs(dy)) moveY = dy;

                mouseEntity.setPosition(mouseEntity.getX() + moveX, mouseEntity.getY() + moveY);
            } else {
                hasMouseTarget = false;
            }
        }

        // Cast to IMovable for movement operations
        IMovable controllable = (IMovable) controllableEntity;
        IMovable autonomous = (IMovable) autonomousEntity;

        // --- Controllable entity (WASD + Space) ---
        InputState state = inputInterface.getInputState();
        float moveX = state.getMoveX();
        boolean jump = state.isJump();
        movementInterface.applyInput(controllable, moveX, 0f, 600f);

        if (jump && Math.abs(controllable.getVelocity().y) < 0.1f) {
            Vector2 v = controllable.getVelocity();
            v.y = 600f;
            controllable.setVelocity(v);
        }

        movementInterface.applyGravity(controllable, 1200f * dt);
        movementInterface.applyGravity(autonomous, 900f * dt);

        movementInterface.applyVelocity(controllable, dt);
        movementInterface.applyVelocity(autonomous, dt);

        resolveLanding(controllableEntity);   // Handle surface landing
        clampToWorld(controllableEntity);     // Keep inside screen

        if (autonomousEntity.getBounds().overlaps(controllableEntity.getBounds())) {
            soundInterface.playSound("audio/hit.wav");
            score++;
            respawnAutonomousEntity();
            return;
        }

        // Mouse entity collision with autonomous block
        if (autonomousEntity.getBounds().overlaps(mouseEntity.getBounds())) {
            soundInterface.playSound("audio/hit.wav");
            score++;
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
    private void resolveLanding(GameEntity entity) {

        if (!(entity instanceof IMovable)) return;
        IMovable movable = (IMovable) entity;

        List<GameEntity> entities = entityProvider.getEntities();

        for (GameEntity e : entities) {

            // Check if this is a static surface (not movable)
            if (!(e instanceof IMovable)) {

                boolean falling = movable.getVelocity().y <= 0;

                boolean horizontalOverlap =
                        entity.getX() + entity.getWidth() > e.getX() &&
                        entity.getX() < e.getX() + e.getWidth();

                boolean touchingTop =
                        entity.getY() >= e.getY() + e.getHeight() - 10 &&
                        entity.getY() <= e.getY() + e.getHeight() + 10;

                if (falling && horizontalOverlap && touchingTop) {

                    entity.setPosition(
                            entity.getX(),
                            e.getY() + e.getHeight()
                    );

                    Vector2 v = movable.getVelocity();
                    v.y = 0;
                    movable.setVelocity(v);
                }
            }
        }
    }

    // Constrains entity within screen bounds
    private void clampToWorld(GameEntity entity) {

        if (!(entity instanceof IMovable)) return;
        IMovable movable = (IMovable) entity;

        float worldWidth = viewport.getWorldWidth();

        if (entity.getX() < 0)
            entity.setPosition(0, entity.getY());

        if (entity.getX() + entity.getWidth() > worldWidth)
            entity.setPosition(worldWidth - entity.getWidth(), entity.getY());

        if (entity.getY() < 0) {
            entity.setPosition(entity.getX(), 0);
            Vector2 v = movable.getVelocity();
            v.y = 0;
            movable.setVelocity(v);
        }
    }

    // Respawns the autonomous entity at a random top position
    private void respawnAutonomousEntity() {

        entityProvider.removeEntity(autonomousEntity);

        float w = viewport.getWorldWidth();
        float h = viewport.getWorldHeight();

        autonomousEntity = entityProvider.createDynamicEntity(
                100 + (float)Math.random() * (w - 200),
                h - 80f,
                80, 80
        );

        entityProvider.addEntity(autonomousEntity);
    }

    // Renders all entities
    private void renderWorld() {

        // Apply viewport and set projection matrix
        viewport.apply();
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        List<GameEntity> entities = entityProvider.getEntities();

        for (GameEntity e : entities) {

            if (e == controllableEntity)
                shapeRenderer.setColor(0f, 0.8f, 1f, 1f);
            else if (e == mouseEntity)
                shapeRenderer.setColor(1f, 0.6f, 0f, 1f);   // Orange for mouse entity
            else if (e == secondarySurface)
                shapeRenderer.setColor(0.3f, 0.8f, 0.3f, 1f);
            else
                shapeRenderer.setColor(1f, 0f, 0f, 1f);

            shapeRenderer.rect(e.getX(), e.getY(),
                    e.getWidth(), e.getHeight());
        }

        // Draw target indicator for mouse entity
        if (hasMouseTarget) {
            shapeRenderer.setColor(1f, 1f, 0f, 0.8f);
            shapeRenderer.circle(mouseTarget.x + mouseEntity.getWidth() / 2f,
                                 mouseTarget.y + mouseEntity.getHeight() / 2f, 8f);
        }

        shapeRenderer.end();
    }

    // Renders overlay UI
    private void renderOverlay() {

        // Use virtual screen size from viewport
        float screenW = viewport.getWorldWidth();
        float screenH = viewport.getWorldHeight();

        batch.begin();

        overlayFont.draw(batch,
                "Press ESC to End Simulation",
                20,
                screenH - 20);

        overlayFont.draw(batch,
                "Left-click to move orange shape",
                20,
                screenH - 40);

        String timeText = "Time: " + String.format("%.2f", simulationTime);

        overlayFont.draw(batch,
                timeText,
                screenW - 150,
                screenH - 20);

        overlayFont.draw(batch,
                "Score: " + score,
                screenW - 150,
                screenH - 40);

        batch.end();
    }

    // Clears the frame buffer
    private void clearScreen() {
        Gdx.gl.glClearColor(0.1f, 0.12f, 0.14f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override public void hide() {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        overlayFont.dispose();
    }
}
