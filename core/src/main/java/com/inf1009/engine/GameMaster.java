package com.inf1009.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inf1009.engine.manager.CollisionManager;
import com.inf1009.engine.manager.EntityManager;
import com.inf1009.engine.manager.IOManager;
import com.inf1009.engine.manager.MovementManager;
import com.inf1009.engine.manager.SceneManager;
import com.inf1009.engine.output.Output;
import com.inf1009.engine.output.SoundOutputDevice;
import com.inf1009.engine.scene.EndScreen;
import com.inf1009.engine.scene.SimulatorScreen;
import com.inf1009.engine.scene.StartScreen;

public class GameMaster extends ApplicationAdapter {

    private SpriteBatch batch;

    private EntityManager ent;
    private SceneManager sm;
    private MovementManager mm;
    private CollisionManager cm;
    private IOManager io;

    private Output output;

    @Override
    public void create() {
        batch = new SpriteBatch();

        ent = new EntityManager();
        sm  = new SceneManager();
        mm  = new MovementManager();
        cm  = new CollisionManager();
        io  = new IOManager();

        output = new SoundOutputDevice();

        sm.addScreen("start", new StartScreen(this));
        sm.addScreen("sim", new SimulatorScreen(this));
        sm.addScreen("end", new EndScreen(this));
        sm.setScreen("start");

    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        sm.render(dt);
    }

    @Override
    public void dispose() {
        sm.dispose();
        batch.dispose();
    }

    public SpriteBatch getBatch() { return batch; }
    public EntityManager getEntityManager() { return ent; }
    public SceneManager getSceneManager() { return sm; }
    public MovementManager getMovementManager() { return mm; }
    public CollisionManager getCollisionManager() { return cm; }
    public IOManager getIOManager() { return io; }
    public Output getOutput() { return output; }
}
