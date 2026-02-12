package com.inf1009.engine.manager;

import com.inf1009.engine.entity.InputState;
import com.inf1009.engine.input.AbstractInputDevice;
import com.inf1009.engine.input.Keyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Coordinates input devices (engine-level)
public class IOManager {

    private final List<AbstractInputDevice> devices = new ArrayList<>();

    public IOManager() {
        // default devices (simulation can still ignore these)
        devices.add(Keyboard.wasd());
        devices.add(Keyboard.arrows());
    }

    // Read input from a device index
    public InputState readDevice(int index) {
        if (index < 0 || index >= devices.size()) return InputState.neutral();
        return devices.get(index).readInput();
    }

    public List<AbstractInputDevice> getDevices() {
        return Collections.unmodifiableList(devices);
    }

    public void addDevice(AbstractInputDevice device) {
        if (device == null) return;
        devices.add(device);
    }
}
