package com.inf1009.engine.manager;

import com.inf1009.engine.entity.InputState;
import com.inf1009.engine.input.AbstractInputDevice;
import com.inf1009.engine.input.Keyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IOManager {

    // Registered input devices
    private final List<AbstractInputDevice> devices = new ArrayList<>();

    public IOManager() {

        // Default keyboard presets
        devices.add(Keyboard.wasd());
        devices.add(Keyboard.arrows());
    }

    // Reads input from a specific device
    public InputState readDevice(int index) {
        if (index < 0 || index >= devices.size()) {
            return InputState.neutral();
        }
        return devices.get(index).readInput();
    }

    // Read-only device list
    public List<AbstractInputDevice> getDevices() {
        return Collections.unmodifiableList(devices);
    }

    public void addDevice(AbstractInputDevice device) {
        if (device == null) return;
        devices.add(device);
    }
}
