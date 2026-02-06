package com.inf1009.engine.manager;

import com.inf1009.engine.scene.Screen;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {

    private final Map<String, Screen> screens = new HashMap<>();
    private Screen current;

    public void addScreen(String name, Screen screen) {
        screens.put(name, screen);
    }

    public void setScreen(String name) {
        Screen next = screens.get(name);
        if (next == null) return;

        if (current != null) current.hide();
        current = next;
        current.show();
    }

    public void render(float dt) {
        if (current != null) current.render(dt);
    }

    public void dispose() {
        for (Screen s : screens.values()) {
            s.dispose();
        }
    }
}
