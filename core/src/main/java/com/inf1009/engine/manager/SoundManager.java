package com.inf1009.engine.manager;

import com.inf1009.engine.interfaces.ICollidable;
import com.inf1009.engine.interfaces.ICollidableListener;
import com.inf1009.engine.interfaces.ISoundInterface;
import com.inf1009.engine.sound.Sound;

import java.util.ArrayList;
import java.util.List;

// Manages audio playback, volume control, and collision-triggered SFX
public class SoundManager implements ICollidableListener, ISoundInterface {

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
    }

    // Plays looping background music
    @Override
    public void playMusic(String name) {

        currentMusicFile = name;
        if (muted) return;

        for (Sound s : soundList) {
            if (s.isMusic()) {
                s.setSoundFile(name);
                s.setVolume((masterVol * musicVol) / 100);
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
                s.setVolume((masterVol * musicVol) / 100);
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
            s.setVolume(0);
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


    // Triggered by collision manager
    @Override
    public void onCollision(ICollidable e1, ICollidable e2) {

        if (!e1.isStatic() && !e2.isStatic()) {
            playSound("audio/hit.wav");
        }
    }

    // Applies stored volume values (master acts as global multiplier)
    private void applyVolumes() {
        for (Sound s : soundList) {
            if (s.isMusic()) {
                // Effective music volume = master * music / 100
                int effective = (masterVol * musicVol) / 100;
                s.setVolume(effective);
            } else {
                s.setVolume(masterVol);
            }
        }
    }

    // Clamps volume between 0–100
    private int clamp(int v) {
        if (v < 0) return 0;
        return Math.min(v, 100);
    }
}
