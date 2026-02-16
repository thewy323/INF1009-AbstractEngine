package com.inf1009.engine.sound;

import com.badlogic.gdx.Gdx;

// Concrete implementation of audio playback using LibGDX
public class Sound extends SoundOutputDevice {

    private String soundFile;
    private com.badlogic.gdx.audio.Sound sfx;
    private com.badlogic.gdx.audio.Music music;

    public Sound() {}

    // Initializes sound with file and volume settings
    public Sound(String soundFile, boolean isMusic, int volume) {
        this.soundFile = soundFile;
        this.isMusic = isMusic;
        setVolume(volume);
    }

    public String getSoundFile() {
        return soundFile;
    }

    public void setSoundFile(String soundFile) {
        this.soundFile = soundFile;
    }

    // Updates volume and applies to active music
    @Override
    public void setVolume(int volume) {
        super.setVolume(volume);
        if (music != null) {
            music.setVolume(this.volume / 100f);
        }
    }

    // Plays sound effect or looping music
    @Override
    public void playSound(String soundFile, boolean isMusic) {

        this.soundFile = soundFile;
        this.isMusic = isMusic;

        if (soundFile == null) return;

        if (isMusic) {

            if (music == null) {
                music = Gdx.audio.newMusic(Gdx.files.internal(soundFile));
                music.setLooping(true);
            }

            music.setVolume(volume / 100f);
            music.play();

        } else {

            if (sfx != null) {
                sfx.stop();
                sfx.dispose();
                sfx = null;
            }

            sfx = Gdx.audio.newSound(Gdx.files.internal(soundFile));
            sfx.play(volume / 100f);
        }
    }

    // Pauses active music
    public void pauseMusic() {
        if (music != null) music.pause();
    }

    // Resumes paused music
    public void resumeMusic() {
        if (music != null) music.play();
    }

    // Stops and disposes audio resources
    @Override
    public void stopSound() {

        if (music != null) {
            music.stop();
            music.dispose();
            music = null;
        }

        if (sfx != null) {
            sfx.stop();
            sfx.dispose();
            sfx = null;
        }
    }
}
