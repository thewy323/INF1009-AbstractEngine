package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.engine.GameMaster;
import com.inf1009.engine.entity.DynamicEntity;
import com.inf1009.engine.entity.ICollidable;
import com.inf1009.engine.entity.StaticEntity;

import java.util.ArrayList;
import java.util.List;

public class SimulatorScreen implements Screen {

    private final GameMaster game;
    private ShapeRenderer shape;

    private DynamicEntity a;
    private DynamicEntity b;
    private StaticEntity goal;

    public SimulatorScreen(GameMaster game) {
        this.game = game;
    }

    @Override
    public void show() {
        shape = new ShapeRenderer();

        a = new DynamicEntity(60, 60, 30, 30, 220f);
        b = new DynamicEntity(120, 60, 30, 30, 220f);
        goal = new StaticEntity(320, 200, 80, 80);

        game.getEntityManager().addEntity(a);
        game.getEntityManager().addEntity(b);
        game.getEntityManager().addEntity(goal);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getMovementManager().applyInput(a, game.getIOManager().readP1(), dt);
        game.getMovementManager().applyInput(b, game.getIOManager().readP2(), dt);

        game.getEntityManager().update(dt);

        List<ICollidable> collidables = new ArrayList<>();
        collidables.add(a);
        collidables.add(b);
        collidables.add(goal);
        game.getCollisionManager().update(collidables);

        boolean win = a.getBounds().overlaps(goal.getBounds()) && b.getBounds().overlaps(goal.getBounds());
        if (win) {
            game.getSceneManager().addScreen("end", new EndScreen(game));
            game.getSceneManager().setScreen("end");
        }

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(a.getX(), a.getY(), a.getW(), a.getH());
        shape.rect(b.getX(), b.getY(), b.getW(), b.getH());
        shape.rect(goal.getX(), goal.getY(), goal.getW(), goal.getH());
        shape.end();
    }

    @Override public void hide() { }

    @Override
    public void dispose() {
        if (shape != null) shape.dispose();
    }
}
