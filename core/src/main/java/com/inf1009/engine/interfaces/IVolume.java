package com.inf1009.engine.interfaces;

// Provides basic volume control abstraction
public interface IVolume {

    int getMasterVol();
    void setMasterVol(int vol);

    int getMusicVol();
    void setMusicVol(int vol);
}
