package com.inf1009.engine.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.inf1009.engine.input.InputDevice;
import com.inf1009.engine.input.InputState;
import com.inf1009.engine.input.MouseDevice;
import com.inf1009.engine.interfaces.IInputInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Aggregates input from multiple devices
public class InputManager implements IInputInterface {

    // Registered input devices
    private final List<InputDevice> inputDevices;

    // Mouse device reference for direct access
    private MouseDevice mouseDevice;

    // Stores merged player input
    private final InputState playerInput;

    // Key bindings for action mapping
    private final Map<String, Integer> keyBindings = new HashMap<>();

    // Default constructor
    public InputManager() {
        this.inputDevices = new ArrayList<>();
        this.playerInput = new InputState();
    }


    // Registers input device
    public void registerDevice(InputDevice device) {
        if (device != null) {
            inputDevices.add(device);
            // Track mouse device separately for direct access
            if (device instanceof MouseDevice) {
                this.mouseDevice = (MouseDevice) device;
            }
        }
    }

    // Polls all devices and processes input
    @Override
    public void update() {
        for (InputDevice d : inputDevices) {
            d.readInput();
        }
        processInput();
    }

    // Merges device input states into playerInput
    public void processInput() {

        playerInput.reset();

        for (InputDevice d : inputDevices) {
            InputState s = d.getInputState();
            playerInput.setMoveX(playerInput.getMoveX() + s.getMoveX());
            playerInput.setMoveY(playerInput.getMoveY() + s.getMoveY());
            playerInput.setJump(playerInput.isJump() || s.isJump());
        }
    }

    // Returns merged player input
    @Override
    public InputState getInputState() {
        return playerInput;
    }

    // Binds action to key on all devices
    @Override
    public void rebindKey(String action, int keyCode) {
        keyBindings.put(action, keyCode);
        for (InputDevice device : inputDevices) {
            device.setBinding(action, keyCode);
        }
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

    // Returns true if left mouse button was just clicked
    @Override
    public boolean isMouseLeftJustClicked() {
        return mouseDevice != null && mouseDevice.isLeftJustClicked();
    }

    // Returns current mouse position
    @Override
    public Vector2 getMousePosition() {
        if (mouseDevice != null) {
            return mouseDevice.getPosition();
        }
        return new Vector2();
    }

    // Returns position where mouse was clicked
    @Override
    public Vector2 getMouseClickPosition() {
        if (mouseDevice != null) {
            return mouseDevice.getClickPosition();
        }
        return new Vector2();
    }

    // Checks if a specific key was just pressed this frame
    @Override
    public boolean isKeyJustPressed(int keyCode) {
        return Gdx.input.isKeyJustPressed(keyCode);
    }

    // Checks if screen was just touched/clicked
    @Override
    public boolean isJustTouched() {
        return Gdx.input.justTouched();
    }

    // Returns raw X coordinate of input (screen space)
    @Override
    public int getInputX() {
        return Gdx.input.getX();
    }

    // Returns raw Y coordinate of input (screen space)
    @Override
    public int getInputY() {
        return Gdx.input.getY();
    }
}
