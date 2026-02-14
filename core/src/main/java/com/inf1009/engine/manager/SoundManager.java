package com.inf1009.engine.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    private final Map<String, Sound> sfx = new HashMap<>();
    private Music bgm;

    private float masterVol = 1f;
    private float bgmVol = 0.6f;
    private float sfxVol = 1f;

    private boolean mute = false;

    public void loadSfx(String key, String filePath) {
        if (key == null || filePath == null) return;
        if (sfx.containsKey(key)) return;
        sfx.put(key, Gdx.audio.newSound(Gdx.files.internal(filePath)));
    }

    public void playSfx(String key) {
        if (mute) return;
        Sound sound = sfx.get(key);
        if (sound == null) return;
        sound.play(masterVol * sfxVol);
    }

    public void loadBgm(String filePath) {
        if (filePath == null) return;

        if (bgm != null) {
            bgm.stop();
            bgm.dispose();
        }

        bgm = Gdx.audio.newMusic(Gdx.files.internal(filePath));
        bgm.setLooping(true);
        bgm.setVolume(masterVol * bgmVol);
    }

    public void playBgm(boolean loop) {
        if (mute || bgm == null) return;
        bgm.setLooping(loop);
        bgm.setVolume(masterVol * bgmVol);
        bgm.play();
    }

    public void stopBgm() {
        if (bgm != null) bgm.stop();
    }

    public void setMasterVol(float v) {
        masterVol = clamp01(v);
        if (bgm != null) bgm.setVolume(masterVol * bgmVol);
    }

    public void setMusicVol(float v) {
        bgmVol = clamp01(v);
        if (bgm != null) bgm.setVolume(masterVol * bgmVol);
    }

    public void setSfxVol(float v) {
        sfxVol = clamp01(v);
    }

    public void mute() {
        mute = true;
        if (bgm != null) bgm.pause();
    }

    public void unmute() {
        mute = false;
        if (bgm != null) bgm.play();
    }

    public void dispose() {
        stopBgm();
        if (bgm != null) {
            bgm.dispose();
            bgm = null;
        }

        for (Sound s : sfx.values()) {
            s.dispose();
        }
        sfx.clear();
    }

    private float clamp01(float v) {
        if (v < 0f) return 0f;
        if (v > 1f) return 1f;
        return v;
    }
}
