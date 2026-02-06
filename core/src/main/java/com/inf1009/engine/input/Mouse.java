package com.inf1009.engine.input;

import com.inf1009.engine.entity.inputState;

public class Mouse extends AbstractInputDevice {
    @Override
    public inputState readInput() {
        return inputState.neutral();
    }
}
