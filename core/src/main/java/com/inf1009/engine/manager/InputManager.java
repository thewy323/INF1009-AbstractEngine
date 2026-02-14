package com.inf1009.engine.manager;

import com.inf1009.engine.input.AbstractInputDevice;
import com.inf1009.engine.input.InputState;
import java.util.ArrayList;
import java.util.List;

public class InputManager {

    private List<AbstractInputDevice> inputDevices = new ArrayList<>();
    private InputState playerInput = new InputState();

    public InputManager() {}

    public InputManager(List<AbstractInputDevice> inputDevices) {
        if (inputDevices != null) this.inputDevices.addAll(inputDevices);
    }

    public void registerDevice(AbstractInputDevice device) {
        if (device != null) inputDevices.add(device);
    }

    public InputState getInputState() {
        return playerInput;
    }

    public void update() {
        processInput();
    }

    public void processInput() {
        playerInput.reset();
        for (AbstractInputDevice d : inputDevices) {
            d.readInput();
            InputState s = d.getInputState();
            playerInput.moveX += s.moveX;
            playerInput.moveY += s.moveY;
            playerInput.jump = playerInput.jump || s.jump;
        }
        if (playerInput.moveX > 1) playerInput.moveX = 1;
        if (playerInput.moveX < -1) playerInput.moveX = -1;
        if (playerInput.moveY > 1) playerInput.moveY = 1;
        if (playerInput.moveY < -1) playerInput.moveY = -1;
    }
}
