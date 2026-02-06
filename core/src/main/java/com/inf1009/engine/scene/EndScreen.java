package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.engine.GameMaster;

public class EndScreen implements Screen {

    @SuppressWarnings("unused")
    private final GameMaster game;
    private ShapeRenderer shape;

    public EndScreen(GameMaster game) {
        this.game = game;
    }

    @Override
    public void show() {
        shape = new ShapeRenderer();
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0.1f, 0.02f, 0.02f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(170, 200, 300, 90);
        shape.end();
    }

    @Override public void hide() { }

    @Override
    public void dispose() {
        if (shape != null) shape.dispose();
    }
}
