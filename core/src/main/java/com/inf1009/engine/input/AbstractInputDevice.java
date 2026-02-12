package com.inf1009.engine.input;

import com.inf1009.engine.entity.InputState;

public abstract class AbstractInputDevice {

    // Returns input snapshot for the current frame
    public abstract InputState readInput();
}
