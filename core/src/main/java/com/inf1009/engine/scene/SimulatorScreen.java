package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inf1009.engine.GameMaster;
import com.inf1009.engine.entity.DynamicEntity;
import com.inf1009.engine.entity.ICollidable;
import com.inf1009.engine.entity.StaticEntity;

import java.util.ArrayList;
import java.util.List;

public class SimulatorScreen implements Screen {

    private final GameMaster game;

    private SpriteBatch batch;

    // textures
    private Texture bg;
    private Texture playerATex;
    private Texture playerBTex;

    // entities
    private DynamicEntity agentA;
    private DynamicEntity agentB;
    private StaticEntity ground;

    public SimulatorScreen(GameMaster game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = game.getBatch();

        // load textures
        bg = new Texture("img/bg.png");
        playerATex = new Texture("img/playerA.png");
        playerBTex = new Texture("img/playerB.png");

        // reset world
        game.getEntityManager().clear();

        // entities
        agentA = new DynamicEntity(80, 120, 32, 32, 220f);
        agentB = new DynamicEntity(140, 120, 32, 32, 220f);

        ground = new StaticEntity(0, 40, 640, 40);

        game.getEntityManager().addEntity(agentA);
        game.getEntityManager().addEntity(agentB);
        game.getEntityManager().addEntity(ground);
    }

    @Override
    public void render(float dt) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.getSceneManager().setScreen("start");
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // input + movement
        game.getMovementManager().applyInput(agentA, game.getIOManager().readP1(), dt);
        game.getMovementManager().applyInput(agentB, game.getIOManager().readP2(), dt);

        // update
        game.getEntityManager().update(dt);

        // collision
        List<ICollidable> collidables = new ArrayList<>();
        game.getEntityManager().getEntities().forEach(e -> {
            if (e instanceof ICollidable) collidables.add((ICollidable) e);
        });
        game.getCollisionManager().update(collidables);

        // render
        batch.begin();

        // background
        batch.draw(bg, 0, 0, 640, 480);

        // ground
        batch.draw(playerATex, ground.getX(), ground.getY(), ground.getW(), ground.getH());

        // players
        batch.draw(playerATex, agentA.getX(), agentA.getY(), agentA.getW(), agentA.getH());
        batch.draw(playerBTex, agentB.getX(), agentB.getY(), agentB.getW(), agentB.getH());

        batch.end();
    }

    @Override public void hide() {}

    @Override
    public void dispose() {
        bg.dispose();
        playerATex.dispose();
        playerBTex.dispose();
    }

    
}
