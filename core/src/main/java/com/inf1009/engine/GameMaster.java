package com.inf1009.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inf1009.engine.manager.CollisionManager;
import com.inf1009.engine.manager.EntityManager;
import com.inf1009.engine.manager.IOManager;
import com.inf1009.engine.manager.MovementManager;
import com.inf1009.engine.manager.SceneManager;
import com.inf1009.engine.scene.SimulatorScreen;
import com.inf1009.engine.scene.StartScreen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


public class GameMaster extends ApplicationAdapter {

    private SpriteBatch batch;

    private EntityManager ent;
    private SceneManager sm;
    private MovementManager mm;
    private CollisionManager cm;
    private IOManager io;
    private Music bgm;
    private Sound coinSound;

    

    @Override
    public void create() {
        batch = new SpriteBatch();

        ent = new EntityManager();
        sm  = new SceneManager();
        mm  = new MovementManager();
        cm  = new CollisionManager();
        io  = new IOManager();

        //Screen code
        sm.addScreen("start", new StartScreen(this));
        Gdx.graphics.setTitle("AbstractEngine - Press SPACE to start");
        sm.addScreen("sim", new SimulatorScreen(this));
        sm.setScreen("start");


        //Audio related code
        bgm = Gdx.audio.newMusic(Gdx.files.internal("audio/bgm.wav"));
        bgm.setLooping(true);
        bgm.setVolume(0.4f);
        bgm.play();
        coinSound = Gdx.audio.newSound(Gdx.files.internal("audio/coin.wav"));

        
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
        if (bgm != null) bgm.dispose();
        if (coinSound != null) coinSound.dispose();

    }

    public SpriteBatch getBatch() { return batch; }
    public EntityManager getEntityManager() { return ent; }
    public SceneManager getSceneManager() { return sm; }
    public MovementManager getMovementManager() { return mm; }
    public CollisionManager getCollisionManager() { return cm; }
    public IOManager getIOManager() { return io; }
    public Sound getCoinSound() { return coinSound; }

}
