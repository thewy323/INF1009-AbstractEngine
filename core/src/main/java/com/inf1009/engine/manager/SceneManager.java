package com.inf1009.engine.manager;

import java.util.HashMap;
import java.util.Map;

import com.inf1009.engine.interfaces.IScreen;

public class SceneManager {

    // Registered screens
    private final Map<String, IScreen> screens = new HashMap<>();

    // Currently active screen
    private IScreen current;

    public void addScreen(String name, IScreen screen) {
        if (name == null || screen == null) return;
        screens.put(name, screen);
    }

    // Switches active screen
    public void setScreen(String name) {

        IScreen next = screens.get(name);
        if (next == null) return;

        if (current != null) current.hide();

        current = next;
        current.show();
    }

    // Delegates render to active screen
    public void render(float dt) {
        if (current != null) {
            current.render(dt);
        }
    }

    // Disposes all screens
    public void dispose() {
        for (IScreen s : screens.values()) {
            s.dispose();
        }
        screens.clear();
    }
}
