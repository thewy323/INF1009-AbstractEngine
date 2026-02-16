package com.inf1009.engine.interfaces;

public interface ISoundInterface {

    void playSound(String name);

    void playMusic(String name);

    void stopMusic();

    void pauseMusic();

    void resumeMusic();

    int getMasterVolume();
    void setMasterVolume(int volume);

    int getMusicVolume();
    void setMusicVolume(int volume);

    void mute();
    void unmute();

    void dispose();
}
