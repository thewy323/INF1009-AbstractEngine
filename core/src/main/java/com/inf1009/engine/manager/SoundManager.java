package com.inf1009.engine.manager;

import com.inf1009.engine.entity.GameEntity;
import com.inf1009.engine.interfaces.ICollidableListener;
import com.inf1009.engine.interfaces.ISoundManager;
import com.inf1009.engine.interfaces.IVolume;
import com.inf1009.engine.sound.Sound;

import java.util.ArrayList;
import java.util.List;

public class SoundManager implements ISoundManager, IVolume, ICollidableListener {

    private List<Sound> soundList = new ArrayList<>();
    private int masterVol = 100;
    private int musicVol = 100;
    private boolean muted = false;

    // Basic registration
    public void addSound(Sound s) {
        if (s != null) soundList.add(s);
    }

    // ISoundManager

    @Override
    public void playSound(String name) {
        if (muted) return;

        for (Sound s : soundList) {
            if (!s.isMusic() && s.getSoundFile().equals(name)) {
                s.playSound(name, false);
            }
        }
    }

    @Override
    public void playMusic(String name) {
        if (muted) return;

        for (Sound s : soundList) {
            if (s.isMusic() && s.getSoundFile().equals(name)) {
                s.playSound(name, true);
            }
        }
    }

    @Override
    public void stopMusic() {
        for (Sound s : soundList) {
            if (s.isMusic()) {
                s.stopSound();
            }
        }
    }

    @Override
    public void pauseMusic() {
        // simple stub
    }

    @Override
    public void resumeMusic() {
        // simple stub
    }

    @Override
    public int getMasterVolume() {
        return masterVol;
    }

    @Override
    public void setMasterVolume(int volume) {
        masterVol = clamp(volume);
        if (!muted) applyVolumes();
    }

    @Override
    public int getMusicVolume() {
        return musicVol;
    }

    @Override
    public void setMusicVolume(int volume) {
        musicVol = clamp(volume);
        if (!muted) applyVolumes();
    }

    @Override
    public void mute() {
        muted = true;
        for (Sound s : soundList) {
            s.setVolume(0);
        }
    }

    @Override
    public void unmute() {
        muted = false;
        applyVolumes();
    }

    @Override
    public void dispose() {
        for (Sound s : soundList) {
            s.stopSound();
        }
        soundList.clear();
    }

    // Collision listener

    @Override
    public void onCollision(GameEntity entity1, GameEntity entity2) {
        if (!muted) {
            for (Sound s : soundList) {
                if (!s.isMusic()) {
                    s.playSound(s.getSoundFile(), false);
                }
            }
        }
    }

    // IVolume

    @Override
    public int getMasterVol() {
        return masterVol;
    }

    @Override
    public void setMasterVol(int vol) {
        setMasterVolume(vol);
    }

    @Override
    public int getMusicVol() {
        return musicVol;
    }

    @Override
    public void setMusicVol(int vol) {
        setMusicVolume(vol);
    }

    // Helper

    private void applyVolumes() {
        for (Sound s : soundList) {
            int volume = s.isMusic() ? musicVol : masterVol;
            s.setVolume(volume);
        }
    }

    private int clamp(int v) {
        if (v < 0) return 0;
        if (v > 100) return 100;
        return v;
    }
}
