package com.inf1009.engine.manager;

import com.inf1009.engine.entity.DynamicEntity;
import com.inf1009.engine.entity.GameEntity;
import com.inf1009.engine.interfaces.ICollidableListener;
import com.inf1009.engine.interfaces.ISoundManager;
import com.inf1009.engine.interfaces.IVolume;
import com.inf1009.engine.sound.Sound;

import java.util.ArrayList;
import java.util.List;

// Manages audio playback, volume control, and collision-triggered SFX
public class SoundManager implements IVolume, ICollidableListener, ISoundManager {

    // Registered sound assets
    private final List<Sound> soundList = new ArrayList<>();

    // Volume state
    private int masterVol = 100;
    private int musicVol = 100;
    private boolean muted = false;

    // Tracks current background music file
    private String currentMusicFile = null;

    // Registers a sound asset
    public void addSound(Sound s) {
        if (s != null) soundList.add(s);
    }

    // Plays a sound effect by file name
    @Override
    public void playSound(String name) {
        if (muted) return;

        for (Sound s : soundList) {
            if (!s.isMusic() && name != null && name.equals(s.getSoundFile())) {
                s.setVolume(masterVol);
                s.playSound(name, false);
                return;
            }
        }

        // Fallback: play first available SFX
        for (Sound s : soundList) {
            if (!s.isMusic()) {
                s.setVolume(masterVol);
                s.playSound(name, false);
                return;
            }
        }
    }

    // Plays looping background music
    @Override
    public void playMusic(String name) {
        currentMusicFile = name;
        if (muted) return;

        for (Sound s : soundList) {
            if (s.isMusic()) {
                s.setSoundFile(name);
                s.setVolume(musicVol);
                s.playSound(name, true);
                return;
            }
        }
    }

    // Stops all music instances
    @Override
    public void stopMusic() {
        for (Sound s : soundList) {
            if (s.isMusic()) {
                s.stopSound();
            }
        }
    }

    // Pauses active music
    @Override
    public void pauseMusic() {
        for (Sound s : soundList) {
            if (s.isMusic()) {
                s.pauseMusic();
            }
        }
    }

    // Resumes music or restarts if disposed
    @Override
    public void resumeMusic() {
        if (muted) return;

        for (Sound s : soundList) {
            if (s.isMusic()) {
                s.setVolume(musicVol);
                s.resumeMusic();
                return;
            }
        }

        if (currentMusicFile != null) {
            playMusic(currentMusicFile);
        }
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

    // Mutes all audio output
    @Override
    public void mute() {
        muted = true;

        for (Sound s : soundList) {
            if (!s.isMusic()) s.setVolume(0);
        }

        for (Sound s : soundList) {
            if (s.isMusic()) {
                s.setVolume(0);
                s.pauseMusic();
            }
        }
    }

    // Restores volume and resumes music
    @Override
    public void unmute() {
        muted = false;
        applyVolumes();
        resumeMusic();
    }

    // Disposes all registered sounds
    @Override
    public void dispose() {
        for (Sound s : soundList) {
            s.stopSound();
        }
        soundList.clear();
    }

    // IVolume compatibility
    @Override
    public int getMasterVol() { return masterVol; }

    @Override
    public void setMasterVol(int vol) { setMasterVolume(vol); }

    @Override
    public int getMusicVol() { return musicVol; }

    @Override
    public void setMusicVol(int vol) { setMusicVolume(vol); }

    // Triggered by collision manager
    @Override
    public void onCollision(GameEntity e1, GameEntity e2) {

        if ((e1.getClass() == DynamicEntity.class &&
            e2.getClass() == DynamicEntity.class)) {

            playSound("audio/hit.wav");
        }
    }

    // Applies stored volume values
    private void applyVolumes() {
        for (Sound s : soundList) {
            if (s.isMusic()) s.setVolume(musicVol);
            else s.setVolume(masterVol);
        }
    }

    // Clamps volume between 0–100
    private int clamp(int v) {
        if (v < 0) return 0;
        if (v > 100) return 100;
        return v;
    }
}
