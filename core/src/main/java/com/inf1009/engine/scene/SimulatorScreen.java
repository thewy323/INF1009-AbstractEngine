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
    private Texture itemTex;

    // entities
    private DynamicEntity agentA;
    private DynamicEntity agentB;
    private StaticEntity ground;
    private DynamicEntity fallingItem;

    private float fallSpeed = 180f;

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
        itemTex = new Texture("img/falling.png");
        // reset world
        game.getEntityManager().clear();

        // players
        agentA = new DynamicEntity(80, 120, 32, 32, 220f);
        agentB = new DynamicEntity(140, 120, 32, 32, 220f);

        // ground
        ground = new StaticEntity(0, 40, 640, 40);

        game.getEntityManager().addEntity(agentA);
        game.getEntityManager().addEntity(agentB);
        game.getEntityManager().addEntity(ground);

        spawnFallingItem();
    }

    @Override
    public void render(float dt) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.getSceneManager().setScreen("start");
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // movement
        game.getMovementManager().applyInput(agentA, game.getIOManager().readP1(), dt);
        game.getMovementManager().applyInput(agentB, game.getIOManager().readP2(), dt);

        // update entities
        game.getEntityManager().update(dt);

        // falling item movement
        if (fallingItem != null) {
            float ny = fallingItem.getY() - fallSpeed * dt;
            fallingItem.setPosition(fallingItem.getX(), ny);

            // respawn if missed
            if (ny < -40) {
                game.getEntityManager().removeEntity(fallingItem);
                spawnFallingItem();
            }
        }

        // collision system
        List<ICollidable> collidables = new ArrayList<>();
        game.getEntityManager().getEntities().forEach(e -> {
            if (e instanceof ICollidable) {
                collidables.add((ICollidable) e);
            }
        });
        game.getCollisionManager().update(collidables);

        // catch logic (generic demo)
        if (fallingItem != null) {
            if (agentA.getBounds().overlaps(fallingItem.getBounds())
                    || agentB.getBounds().overlaps(fallingItem.getBounds())) {

                game.getEntityManager().removeEntity(fallingItem);
                spawnFallingItem();
            }
        }

        // render
        batch.begin();

        batch.draw(bg, 0, 0, 640, 480);

        batch.draw(playerATex, agentA.getX(), agentA.getY(), agentA.getW(), agentA.getH());
        batch.draw(playerBTex, agentB.getX(), agentB.getY(), agentB.getW(), agentB.getH());

        if (fallingItem != null) {
            batch.draw(itemTex,
                    fallingItem.getX(),
                    fallingItem.getY(),
                    fallingItem.getW(),
                    fallingItem.getH());
        }

        batch.end();
    }

    @Override public void hide() {}

    @Override
    public void dispose() {
        bg.dispose();
        playerATex.dispose();
        playerBTex.dispose();
        itemTex.dispose();
    }

    private void spawnFallingItem() {
        float x = (float) (Math.random() * (640 - 32));
        fallingItem = new DynamicEntity(x, 480, 32, 32, 0f);
        game.getEntityManager().addEntity(fallingItem);
    }
}
