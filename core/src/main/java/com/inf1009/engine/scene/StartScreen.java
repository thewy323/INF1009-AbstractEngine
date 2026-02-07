package com.inf1009.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.engine.GameMaster;

public class StartScreen extends AbstractScreen {

    private ShapeRenderer shape;
    private SpriteBatch batch;
    private BitmapFont font;

    private final float startX = 220, startY = 240, startW = 200, startH = 55;
    private final float exitX  = 220, exitY  = 160, exitW  = 200, exitH  = 55;

    public StartScreen(GameMaster game) {
        super(game);
    }

    @Override
    public void show() {
        if (shape == null) shape = new ShapeRenderer();
        batch = game.getBatch();
        if (font == null) font = new BitmapFont();
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0.08f, 0.08f, 0.08f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(startX, startY, startW, startH);
        shape.rect(exitX, exitY, exitW, exitH);
        shape.end();

        batch.begin();
        font.draw(batch, "ABSTRACT ENGINE DEMO", 200, 360);
        font.draw(batch, "START", startX + 75, startY + 35);
        font.draw(batch, "EXIT",  exitX  + 80, exitY  + 35);
        font.draw(batch, "SPACE = Start", 245, 120);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.getSceneManager().setScreen("sim");
            return;
        }

        if (Gdx.input.justTouched()) {
            int mx = Gdx.input.getX();
            int my = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (inside(mx, my, startX, startY, startW, startH)) {
                game.getSceneManager().setScreen("sim");
            } else if (inside(mx, my, exitX, exitY, exitW, exitH)) {
                Gdx.app.exit();
            }
        }
    }

    private boolean inside(float px, float py, float x, float y, float w, float h) {
        return px >= x && px <= x + w && py >= y && py <= y + h;
    }

    @Override
    public void dispose() {
        if (shape != null) { shape.dispose(); shape = null; }
        if (font != null)  { font.dispose();  font = null; }
    }
}
