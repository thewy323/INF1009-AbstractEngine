package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.engine.GameMaster;
import com.inf1009.engine.entity.AbstractGameEntity;
import com.inf1009.engine.interfaces.ICollidable;
import java.util.ArrayList;
import java.util.List;

public class SimulatorScene extends AbstractScene {

    private boolean simulationRunning = true;
    private float simulationTime = 0f;
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
    }

    @Override
    public void render(float deltaTime) {
        handleInput();
        update(deltaTime);

        shape.begin(ShapeRenderer.ShapeType.Line);
        List<AbstractGameEntity> entities = game.getEntityManager().getEntities();
        for (AbstractGameEntity e : entities) e.render(shape);
        shape.end();
    }

    public void update(float deltaTime) {
        if (!simulationRunning) return;

        simulationTime += deltaTime;
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

    public void spawnEntity(AbstractGameEntity entity) {
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
        for (AbstractGameEntity e : game.getEntityManager().getEntities()) {
            if (e instanceof ICollidable) list.add((ICollidable) e);
        }
        return list;
    }
}
