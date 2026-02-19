package com.inf1009.engine.manager;

import com.badlogic.gdx.Gdx;
import com.inf1009.engine.interfaces.ISceneNavigator;
import com.inf1009.engine.scene.Scene;
import java.util.HashMap;
import java.util.Map;

// Controls scene registration and transitions
public class SceneManager implements ISceneNavigator{

    private Scene currentScene;
    private final Map<String, Scene> scenes = new HashMap<>();

    // Registers a scene by name
    public void addScene(String name, Scene scene) {
        if (name == null || scene == null) return;
        scenes.put(name, scene);
    }

    // Switches active scene
    public void setScene(String name) {
        if (currentScene != null) currentScene.hide();
        currentScene = scenes.get(name);
        if (currentScene != null) currentScene.show();
    }


    // Updates current scene each frame
    public void update(float deltaTime) {
        if (currentScene != null) currentScene.render(deltaTime);
    }

    // Disposes all registered scenes
    public void dispose() {
        for (Scene s : scenes.values()) s.dispose();
        scenes.clear();
        currentScene = null;
    }

    //scene nav
    @Override
    public void navigateTo(String sceneName) {
        setScene(sceneName);
    }

    // Exits the application
    @Override
    public void exitGame() {
        Gdx.app.exit();
    }
}
