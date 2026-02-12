package com.inf1009.engine.manager;

import com.inf1009.engine.entity.InputState;
import com.inf1009.engine.input.AbstractInputDevice;
import com.inf1009.engine.input.Keyboard;

import java.util.*;

public class IOManager {

    private final List<AbstractInputDevice> devices = new ArrayList<>();

    // Simple action-to-key binding map
    private final Map<String, Integer> bindings = new HashMap<>();

    public IOManager() {
        devices.add(Keyboard.wasd());
        devices.add(Keyboard.arrows());
    }

    public InputState readDevice(int index) {
        if (index < 0 || index >= devices.size()) {
            return InputState.neutral();
        }
        return devices.get(index).readInput();
    }

    public List<AbstractInputDevice> getDevices() {
        return Collections.unmodifiableList(devices);
    }

    public void addDevice(AbstractInputDevice device) {
        if (device == null) return;
        devices.add(device);
    }

    // UML requirement
    public void update() {
        // Reserved for future polling logic
    }

    // UML requirement
    public InputState getInputState(int index) {
        return readDevice(index);
    }

    // UML requirement
    public void setBinding(String action, int keyCode) {
        bindings.put(action, keyCode);
    }

    public Integer getBinding(String action) {
        return bindings.get(action);
    }
}
