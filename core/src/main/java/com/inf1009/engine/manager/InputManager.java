package com.inf1009.engine.manager;

import com.badlogic.gdx.Gdx;
import com.inf1009.engine.input.InputDevice;
import com.inf1009.engine.input.InputState;
import com.inf1009.engine.interfaces.IInputManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Aggregates input from multiple devices
public class InputManager implements IInputManager {

    private List<InputDevice> inputDevices = new ArrayList<>();
    private Map<String, Integer> keyBindings = new HashMap<>();

    // Registers input device
    public void registerDevice(InputDevice device) {
        if (device != null) inputDevices.add(device);
    }

    // Polls all devices
    @Override
    public void update() {
        for (InputDevice d : inputDevices) {
            d.readInput();
        }
    }

    // Merges device input states
    @Override
    public InputState getInputState() {

        InputState merged = new InputState();

        for (InputDevice d : inputDevices) {
            InputState s = d.getInputState();
            merged.setMoveX(merged.getMoveX() + s.getMoveX());
            merged.setMoveY(merged.getMoveY() + s.getMoveY());
            merged.setJump(merged.isJump() || s.isJump());
        }

        return merged;
    }

    // Binds action to key
    @Override
    public void rebindKey(String action, int keyCode) {
        keyBindings.put(action, keyCode);
    }

    // Retrieves bound key
    @Override
    public int getKeyBinding(String action) {
        return keyBindings.getOrDefault(action, -1);
    }

    // Checks if action key was pressed
    @Override
    public boolean isActionJustPressed(String action) {
        Integer keyCode = keyBindings.get(action);
        if (keyCode == null) return false;
        return Gdx.input.isKeyJustPressed(keyCode);
    }

    // Reads specific device state
    public InputState readDevice(int index) {
        if (index < 0 || index >= inputDevices.size()) return new InputState();
        return inputDevices.get(index).getInputState();
    }

    // Clears registered devices
    public void clearDevices() {
        inputDevices.clear();
    }
}
