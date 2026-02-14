package com.inf1009.engine.input;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractInputDevice {

    protected InputMap bindings = new InputMap();
    protected Map<String, Runnable> inputActions = new HashMap<>();
    protected InputState deviceInput = new InputState();

    public abstract void defineBindings();
    public abstract void defineActions();
    public abstract void readInput();
    public abstract void readAction();

    public void setBinding(String action, int inputKeyCode) {
        bindings.put(action, inputKeyCode);
    }

    public void setAction(String action, Runnable actionFunction) {
        inputActions.put(action, actionFunction);
    }

    public void runAction(String action) {
        Runnable r = inputActions.get(action);
        if (r != null) r.run();
    }

    public void runIfAction(String action, Runnable actionFunction) {
        if (actionFunction == null) return;
        inputActions.put(action, actionFunction);
        runAction(action);
    }

    public Runnable getFunction(String action) {
        return inputActions.get(action);
    }

    public InputState getInputState() {
        return deviceInput;
    }

    public static class InputMap extends HashMap<String, Integer> {}
}
