package com.inf1009.engine.output;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class SoundOutputDevice implements Output {

    private boolean muted = false;

    private Music currentMusic;
    private final Map<String, Sound> sounds = new HashMap<>();

    @Override
    public void playSound(String file, float volume) {
        if (muted) return;

        Sound s = sounds.get(file);
        if (s == null) {
            s = Gdx.audio.newSound(Gdx.files.internal(file));
            sounds.put(file, s);
        }
        s.play(volume);
    }

    @Override
    public void playMusic(String file, float volume, boolean loop) {
        if (muted) return;

        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }

        currentMusic = Gdx.audio.newMusic(Gdx.files.internal(file));
        currentMusic.setLooping(loop);
        currentMusic.setVolume(volume);
        currentMusic.play();
    }

    @Override
    public void stopMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
        }
    }

    @Override
    public void mute() {
        muted = true;
        if (currentMusic != null) currentMusic.pause();
    }

    @Override
    public void unmute() {
        muted = false;
        if (currentMusic != null) currentMusic.play();
    }
}
