package com.inf1009.engine.output;

// Audio abstraction for engine-level sound control
public interface Output {
    void playSound(String file, float volume);
    void playMusic(String file, float volume, boolean loop);
    void stopMusic();
    void mute();
    void unmute();
}
