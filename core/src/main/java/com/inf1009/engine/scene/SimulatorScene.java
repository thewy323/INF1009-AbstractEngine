package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.engine.GameMaster;
import com.inf1009.engine.entity.AbstractGameEntity;
import com.inf1009.engine.entity.DynamicEntity;
import com.inf1009.engine.interfaces.ICollidable;
import com.inf1009.engine.input.InputState;
import com.inf1009.engine.entity.StaticEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Engine Technology Demonstration
 * 
 * DEMONSTRATES ALL 5 MANAGERS:
 * 1. IOManager - Dual input devices (WASD+Space vs Arrows+Enter)
 * 2. MovementManager - Physics-based movement
 * 3. EntityManager - Dynamic spawning/despawning
 * 4. CollisionManager - Collision detection and response
 * 5. SceneManager - Screen transitions (ESC)
 * 
 * ABSTRACT BEHAVIORS (No game logic):
 * - Two controllable entities
 * - Falling hazards (circle drops from top)
 * - Entity-entity collision (bump into each other)
 * - Audio feedback on collision
 * - Dynamic spawning system
 */
public class SimulatorScreen implements IScreen {

    private final GameMaster game;
    private ShapeRenderer shape;

    // Controllable entities
    private DynamicEntity agent1;  // WASD + Space
    private DynamicEntity agent2;  // Arrows + Enter

    // Environment
    private StaticEntity ground;
    private StaticEntity wallLeft;
    private StaticEntity wallRight;

    // Dynamic hazard
    private DynamicEntity fallingHazard;
    private float hazardTimer = 0f;
    private static final float SPAWN_INTERVAL = 2.5f;

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
            setupDemo();
        }
    }

    private void setupDemo() {
        game.getEntityManager().clear();

        // Two agents (demonstrates dual input)
        agent1 = new DynamicEntity(100, 120, 30, 30, 250f);
        agent2 = new DynamicEntity(200, 120, 30, 30, 250f);

        // Boundaries
        ground = new StaticEntity(0, 20, SCREEN_W, 30);
        wallLeft = new StaticEntity(0, 0, 15, SCREEN_H);
        wallRight = new StaticEntity(SCREEN_W - 15, 0, 15, SCREEN_H);

        game.getEntityManager().addEntity(agent1);
        game.getEntityManager().addEntity(agent2);
        game.getEntityManager().addEntity(ground);
        game.getEntityManager().addEntity(wallLeft);
        game.getEntityManager().addEntity(wallRight);

        fallingHazard = null;
        hazardTimer = 0f;
    }

    @Override
    public void render(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.getSceneManager().setScreen("end");
            return;
        }

        clearScreen();
        
        // TEST ALL MANAGERS:
        handleDualInput(dt);      // IOManager + MovementManager
        updateEntities(dt);       // EntityManager
        handleHazardSpawning(dt); // EntityManager (lifecycle)
        detectCollisions();       // CollisionManager
        
        renderScene();
    }

    /**
     * DEMONSTRATES: IOManager (2 devices) + MovementManager
     */
    private void handleDualInput(float dt) {
        InputState input1 = game.getIOManager().readDevice(0);
        game.getMovementManager().applyInput(agent1, input1, dt);

        InputState input2 = game.getIOManager().readDevice(1);
        game.getMovementManager().applyInput(agent2, input2, dt);
    }

    /**
     * DEMONSTRATES: EntityManager (update all)
     */
    private void updateEntities(float dt) {
        game.getEntityManager().update(dt);

        float groundTop = ground.getY() + ground.getH();
        
        if (agent1.getY() <= groundTop) {
            agent1.landOn(groundTop);
        }
        if (agent2.getY() <= groundTop) {
            agent2.landOn(groundTop);
        }
        if (fallingHazard != null && fallingHazard.getY() <= groundTop) {
            fallingHazard.landOn(groundTop);
        }
    }

    /**
     * DEMONSTRATES: EntityManager (dynamic add/remove)
     */
    private void handleHazardSpawning(float dt) {
        if (fallingHazard == null) {
            hazardTimer += dt;
            
            if (hazardTimer >= SPAWN_INTERVAL) {
                spawnHazard();
                hazardTimer = 0f;
            }
        } else {
            if (fallingHazard.getY() < -50) {
                game.getEntityManager().removeEntity(fallingHazard);
                fallingHazard = null;
            }
        }
    }

    private void spawnHazard() {
        float randomX = 50 + (float) (Math.random() * (SCREEN_W - 100));
        fallingHazard = new DynamicEntity(randomX, SCREEN_H - 50, 25, 25, 0f);
        fallingHazard.setVelocityY(-150f);
        game.getEntityManager().addEntity(fallingHazard);
    }

    /**
     * DEMONSTRATES: CollisionManager
     */
    private void detectCollisions() {
        List<ICollidable> collidables = new ArrayList<>();
        
        for (AbstractGameEntity e : game.getEntityManager().getEntities()) {
            if (e instanceof ICollidable) {
                collidables.add((ICollidable) e);
            }
        }

        // Handle specific collisions
        handleCollisionBehaviors();
        
        // Engine's generic collision detection
        game.getCollisionManager().update(collidables);
    }

    /**
     * Abstract collision responses (no game logic)
     */
    private void handleCollisionBehaviors() {
        // Agent 1 hits hazard
        if (fallingHazard != null && 
            agent1.getBounds().overlaps(fallingHazard.getBounds())) {
            
            if (game.getOutput() != null) {
                game.getOutput().playSound("audio/collision.wav", 0.3f);
            }
            
            game.getEntityManager().removeEntity(fallingHazard);
            fallingHazard = null;
            System.out.println("Agent 1 collision - hazard removed");
        }

        // Agent 2 hits hazard
        if (fallingHazard != null && 
            agent2.getBounds().overlaps(fallingHazard.getBounds())) {
            
            if (game.getOutput() != null) {
                game.getOutput().playSound("audio/collision.wav", 0.3f);
            }
            
            game.getEntityManager().removeEntity(fallingHazard);
            fallingHazard = null;
            System.out.println("Agent 2 collision - hazard removed");
        }

        // Agents collide with each other
        if (agent1.getBounds().overlaps(agent2.getBounds())) {
            if (game.getOutput() != null) {
                game.getOutput().playSound("audio/bump.wav", 0.2f);
            }
            
            // Simple push apart
            float push = 2f;
            float center1 = agent1.getX() + agent1.getW() / 2;
            float center2 = agent2.getX() + agent2.getW() / 2;
            
            if (center1 < center2) {
                agent1.setPosition(agent1.getX() - push, agent1.getY());
                agent2.setPosition(agent2.getX() + push, agent2.getY());
            } else {
                agent1.setPosition(agent1.getX() + push, agent1.getY());
                agent2.setPosition(agent2.getX() - push, agent2.getY());
            }
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.08f, 0.08f, 0.12f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void renderScene() {
        shape.begin(ShapeRenderer.ShapeType.Filled);

        // Ground
        shape.setColor(0.4f, 0.4f, 0.4f, 1f);
        shape.rect(ground.getX(), ground.getY(), ground.getW(), ground.getH());

        // Walls
        shape.setColor(0.3f, 0.3f, 0.3f, 1f);
        shape.rect(wallLeft.getX(), wallLeft.getY(), wallLeft.getW(), wallLeft.getH());
        shape.rect(wallRight.getX(), wallRight.getY(), wallRight.getW(), wallRight.getH());

        // Agent 1 (green)
        shape.setColor(Color.GREEN);
        shape.rect(agent1.getX(), agent1.getY(), agent1.getW(), agent1.getH());

        // Agent 2 (cyan)
        shape.setColor(Color.CYAN);
        shape.rect(agent2.getX(), agent2.getY(), agent2.getW(), agent2.getH());

        // Falling hazard (red circle)
        if (fallingHazard != null) {
            shape.setColor(Color.RED);
            float cx = fallingHazard.getX() + fallingHazard.getW() / 2;
            float cy = fallingHazard.getY() + fallingHazard.getH() / 2;
            shape.circle(cx, cy, fallingHazard.getW() / 2, 20);
        }

        shape.end();
    }

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        if (shape != null) {
            shape.dispose();
        }
    }
}