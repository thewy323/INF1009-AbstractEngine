package com.inf1009.engine.manager;

import com.inf1009.engine.scene.AbstractScene;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {

    private AbstractScene currentScene;
    private Map<String, AbstractScene> scenes = new HashMap<>();

    public void addScreen(String name, AbstractScene scene) {
        if (name == null || scene == null) return;
        scenes.put(name, scene);
    }

    public void setScreen(String name) {
        if (currentScene != null) currentScene.hide();
        currentScene = scenes.get(name);
        if (currentScene != null) currentScene.show();
    }

    public AbstractScene getCurrentScene() {
        return currentScene;
    }

    public void transitionTo(String name, Transition effect) {
        AbstractScene next = scenes.get(name);
        if (next == null) return;
        if (effect != null) effect.apply(currentScene, next);
        setScreen(name);
    }

    public void update(float deltaTime) {
        if (currentScene != null) currentScene.render(deltaTime);
    }

    public void dispose() {
        for (AbstractScene s : scenes.values()) s.dispose();
        scenes.clear();
        currentScene = null;
    }

    public interface Transition {
        void apply(AbstractScene from, AbstractScene to);
    }
}
