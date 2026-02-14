package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.engine.GameMaster;
import com.inf1009.engine.entity.DynamicEntity;
import com.inf1009.engine.entity.GameEntity;
import com.inf1009.engine.entity.StaticEntity;
import com.inf1009.engine.input.KeyboardDevice;
import com.inf1009.engine.interfaces.ICollidable;
import java.util.ArrayList;
import java.util.List;

public class SimulatorScene extends Scene {

    private boolean simulationRunning = true;
   // private float simulationTime = 0f;
    private boolean showCollisionBounds = false;

    private final GameMaster game;
    private ShapeRenderer shape;

    private boolean gravityEnabled = true;

    public SimulatorScene(GameMaster game) {
        this.game = game;
    }

    @Override
    public void show() {
        shape = new ShapeRenderer();
        isLoaded = true;

        // Setup keyboard devices
        game.getInputManager().registerDevice(KeyboardDevice.wasd());
        game.getInputManager().registerDevice(KeyboardDevice.arrows());

        // Spawn two agents
        DynamicEntity agent1 = new DynamicEntity(100, 200, 40, 40);
        agent1.setSpeed(200f);

        DynamicEntity agent2 = new DynamicEntity(500, 200, 40, 40);
        agent2.setSpeed(200f);

        // Floor
        StaticEntity floor = new StaticEntity(0, 0, 800, 20);
        floor.setSolid(true);

        // Walls
        StaticEntity leftWall = new StaticEntity(0, 0, 20, 600);
        StaticEntity rightWall = new StaticEntity(780, 0, 20, 600);

        // Register entities
        game.getEntityManager().addEntity(agent1);
        game.getEntityManager().addEntity(agent2);
        game.getEntityManager().addEntity(floor);
        game.getEntityManager().addEntity(leftWall);
        game.getEntityManager().addEntity(rightWall);
    }


    @Override
    public void render(float deltaTime) {
        handleInput();
        update(deltaTime);

        shape.begin(ShapeRenderer.ShapeType.Line);
        List<GameEntity> entities = game.getEntityManager().getEntities();
        for (GameEntity e : entities) e.render(shape);
        shape.end();
    }

    public void update(float deltaTime) {
        if (!simulationRunning) return;

       // simulationTime += deltaTime;
        game.getInputManager().update();
        game.getEntityManager().update(deltaTime);

        game.getCollisionManager().clearCollisions();
        for (ICollidable c : collectCollidables()) game.getCollisionManager().register(c);
        game.getCollisionManager().update();

        logSimulation();
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) toggleSimulation();
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) showCollisionBounds = !showCollisionBounds;
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) toggleGravity();
    }

    public void logSimulation() {}

    public void toggleSimulation() {
        simulationRunning = !simulationRunning;
    }

    public void toggleGravity() {
        gravityEnabled = !gravityEnabled;
    }

    public void spawnEntity(GameEntity entity) {
        game.getEntityManager().addEntity(entity);
    }

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        if (shape != null) shape.dispose();
    }

    private List<ICollidable> collectCollidables() {
        List<ICollidable> list = new ArrayList<>();
        for (GameEntity e : game.getEntityManager().getEntities()) {
            if (e instanceof ICollidable) list.add((ICollidable) e);
        }
        return list;
    }
}
