package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.engine.GameMaster;

public class StartScreen implements Screen {

    private final GameMaster game;
    private ShapeRenderer shape;

    public StartScreen(GameMaster game) {
        this.game = game;
    }

    @Override
    public void show() {
        shape = new ShapeRenderer();
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0.08f, 0.08f, 0.08f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(200, 220, 240, 60);
        shape.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.getSceneManager().addScreen("sim", new SimulatorScreen(game));
            game.getSceneManager().setScreen("sim");
        }
    }

    @Override public void hide() { }

    @Override
    public void dispose() {
        if (shape != null) shape.dispose();
    }
}
