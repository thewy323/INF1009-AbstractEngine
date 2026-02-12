package com.inf1009.engine.manager;

import java.util.HashMap;
import java.util.Map;

import com.inf1009.engine.interfaces.IScreen;

public class SceneManager {

    private final Map<String, IScreen> screens = new HashMap<>();
    private IScreen current;

    public void addScreen(String name, IScreen screen) {
        if (name == null || screen == null) return;
        screens.put(name, screen);
    }

    public void setScreen(String name) {

        IScreen next = screens.get(name);
        if (next == null) return;

        if (current != null) current.hide();

        current = next;
        current.show();
    }

    public IScreen getCurrentScreen() {
        return current;
    }

    public void render(float dt) {
        if (current != null) {
            current.render(dt);
        }
    }


    public void update(float dt) {
        render(dt);
    }


    public void transition(String name) {
        setScreen(name);
    }

    public void dispose() {
        for (IScreen s : screens.values()) {
            s.dispose();
        }
        screens.clear();
    }
}
