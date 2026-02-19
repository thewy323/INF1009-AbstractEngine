package com.inf1009.engine.input;

import java.util.HashMap;

// Base abstraction for input devices
public abstract class InputDevice {

    // Key bindings for this device
    protected InputMap bindings = new InputMap();

    // Current device input state
    protected InputState deviceInput = new InputState();

    // Defines key bindings for device
    public abstract void defineBindings();

    // Defines action callbacks
    public abstract void defineActions();

    // Reads raw device input
    public abstract void readInput();

    // Binds action name to key code
    public void setBinding(String action, int inputKeyCode) {
        bindings.put(action, inputKeyCode);
    }

    public int getBinding(String action) {
        return bindings.getOrDefault(action, -1);
    }


    // Returns current input state snapshot
    public InputState getInputState() {
        return deviceInput;
    }

    // Simple binding map wrapper
    public static class InputMap extends HashMap<String, Integer> {}
}
