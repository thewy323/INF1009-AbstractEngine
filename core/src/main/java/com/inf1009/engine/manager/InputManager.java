package com.inf1009.engine.manager;

import com.inf1009.engine.input.InputDevice;
import com.inf1009.engine.input.InputState;
import java.util.ArrayList;
import java.util.List;

public class InputManager {

    // Fields
    private List<InputDevice> inputDevices = new ArrayList<>();
    private InputState playerInput = new InputState();

    // Constructors
    public InputManager() {}

    public InputManager(List<InputDevice> inputDevices) {
        if (inputDevices != null) this.inputDevices.addAll(inputDevices);
    }

    // Register device
    public void registerDevice(InputDevice device) {
        if (device != null) inputDevices.add(device);
    }

    // Get input state
    public InputState getInputState() {
        return playerInput;
    }

    // Update
    public void update() {
        processInput();
    }

    // Merge device inputs
    public void processInput() {

        playerInput.reset();

        for (InputDevice d : inputDevices) {

            d.readInput();
            InputState s = d.getInputState();

            playerInput.setMoveX(
                    playerInput.getMoveX() + s.getMoveX()
            );

            playerInput.setMoveY(
                    playerInput.getMoveY() + s.getMoveY()
            );

            playerInput.setJump(
                    playerInput.isJump() || s.isJump()
            );
        }

        if (playerInput.getMoveX() > 1)
            playerInput.setMoveX(1);

        if (playerInput.getMoveX() < -1)
            playerInput.setMoveX(-1);

        if (playerInput.getMoveY() > 1)
            playerInput.setMoveY(1);

        if (playerInput.getMoveY() < -1)
            playerInput.setMoveY(-1);
    }
}
