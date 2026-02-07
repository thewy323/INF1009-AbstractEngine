package com.inf1009.engine.manager;

import com.inf1009.engine.entity.InputState;
import com.inf1009.engine.input.AbstractInputDevice;
import com.inf1009.engine.input.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IOManager {

    // UML: devices: List<AbstractInputDevice>
    private final List<AbstractInputDevice> devices;

    private final AbstractInputDevice p1;
    private final AbstractInputDevice p2;

    // UML: playerInput: InputState
    private InputState playerInput;

    // UML: bindings: InputMap (implemented as Map<String, Integer>)
    private final Map<String, Integer> bindings;

    public IOManager() {
        devices = new ArrayList<>();
        p1 = Keyboard.player1WASD();
        p2 = Keyboard.player2Arrows();
        devices.add(p1);
        devices.add(p2);

        playerInput = p1.readInput(); // default to p1
        bindings = new HashMap<>();
    }

    // Existing API (keep so nothing breaks)
    public InputState readP1() { return p1.readInput(); }
    public InputState readP2() { return p2.readInput(); }
    public List<AbstractInputDevice> getDevices() { return devices; }

    // update(): void
    public void update() {
        processInput();
    }

    //getInputState(): InputState
    public InputState getInputState() {
        return playerInput;
    }

    //processInput(): void
    // Minimal + safe: just mirror p1 input into playerInput
    public void processInput() {
        playerInput = p1.readInput();
    }

    //binding method without new classes
    public void setBinding(String action, int inputKeyCode) {
        bindings.put(action, inputKeyCode);
    }
}
