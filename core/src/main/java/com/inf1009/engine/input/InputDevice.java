package com.inf1009.engine.input;

import java.util.HashMap;
import java.util.Map;

// Base abstraction for input devices
public abstract class InputDevice {

    // Key bindings for this device
    protected InputMap bindings = new InputMap();

    // Action callbacks mapped by name
    protected Map<String, Runnable> inputActions = new HashMap<>();

    // Current device input state
    protected InputState deviceInput = new InputState();

    // Defines key bindings for device
    public abstract void defineBindings();

    // Defines action callbacks
    public abstract void defineActions();

    // Reads raw device input
    public abstract void readInput();

    // Executes mapped actions
    public abstract void readAction();

    // Binds action name to key code
    public void setBinding(String action, int inputKeyCode) {
        bindings.put(action, inputKeyCode);
    }

    public int getBinding(String action) {
        return bindings.getOrDefault(action, -1);
    }

    // Associates action name with function
    public void setAction(String action, Runnable actionFunction) {
        inputActions.put(action, actionFunction);
    }

    // Executes stored action
    public void runAction(String action) {
        Runnable r = inputActions.get(action);
        if (r != null) r.run();
    }

    // Registers and runs action immediately
    public void runIfAction(String action, Runnable actionFunction) {
        if (actionFunction == null) return;
        inputActions.put(action, actionFunction);
        runAction(action);
    }

    // Returns stored action function
    public Runnable getFunction(String action) {
        return inputActions.get(action);
    }

    // Returns current input state snapshot
    public InputState getInputState() {
        return deviceInput;
    }

    // Simple binding map wrapper
    public static class InputMap extends HashMap<String, Integer> {}
}
