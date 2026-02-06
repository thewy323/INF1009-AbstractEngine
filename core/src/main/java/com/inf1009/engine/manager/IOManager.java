package com.inf1009.engine.manager;

import com.inf1009.engine.entity.inputState;
import com.inf1009.engine.input.Keyboard;

public class IOManager {

    private final Keyboard p1Keyboard;
    private final Keyboard p2Keyboard;

    public IOManager() {
        p1Keyboard = Keyboard.wasd();
        p2Keyboard = Keyboard.arrows();
        // Mouse exists as a skeleton if your UML includes it
    }

    public inputState readP1() {
        return p1Keyboard.readInput();
    }

    public inputState readP2() {
        return p2Keyboard.readInput();
    }
}
