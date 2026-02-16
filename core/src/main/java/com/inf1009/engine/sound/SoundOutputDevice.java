package com.inf1009.engine.sound;

// Base abstraction for audio output devices
public abstract class SoundOutputDevice {

    // Master volume level (0–100)
    protected int volume = 100;

    // Indicates whether current sound is music
    protected boolean isMusic = false;

    // Returns current volume
    public int getVolume() {
        return volume;
    }

    // Sets clamped volume level
    public void setVolume(int v) {
        if (v < 0) v = 0;
        if (v > 100) v = 100;
        this.volume = v;
    }

    // Returns whether current audio is music
    public boolean isMusic() {
        return isMusic;
    }

    // Plays a sound or music file
    public abstract void playSound(String soundFile, boolean isMusic);

    // Stops currently playing sound
    public abstract void stopSound();
}
