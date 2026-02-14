package com.inf1009.engine.sound;

import com.inf1009.engine.interfaces.ISound;

public abstract class SoundOutputDevice implements ISound {

    // Fields
    protected int volume = 100;
    protected boolean isMusic = false;

    // Volume control
    public int getVolume() { return volume; }

    public void setVolume(int v) {
        if (v < 0) v = 0;
        if (v > 100) v = 100;
        this.volume = v;
    }

    public boolean isMusic() { return isMusic; }

    // Abstract sound behavior
    @Override
    public abstract void playSound(String soundFile, boolean isMusic);

    @Override
    public abstract void stopSound();
}
