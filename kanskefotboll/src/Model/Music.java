package Model;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class Music {
    private Clip backgroundMusic;
    private FloatControl volumeControl;
    private float previousVolume = 0.0f;
    private boolean isMuted = false;


    public Music(String filename) {
        playBackgroundMusic(filename);
    }


    /**
     * Plays the background music from the specified file.
     * If the music is already playing, nothing happens.
     * The sound is played continuously.
     *
     * @param filename The name of the audio file to be played.
     * @author Ali Farhan & Sleiman Sleiman
     */
    public void playBackgroundMusic(String filename) {
        try {
            if (backgroundMusic != null && backgroundMusic.isRunning()) {
                return;
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);


            if (backgroundMusic.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumeControl = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
            }


        } catch (Exception e) {
            System.err.println("Error while initializing audio playback: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Increases the volume of the music.
     *
     * @uthor Ali Farhan
     */
    public void increaseVolume() {
        adjustVolume(5.0f);
    }


    /**
     * Decreases the volume of the music.
     *
     * @uthor Ali Farhan
     */
    public void decreaseVolume() {
        adjustVolume(-5.0f);
    }


    /**
     * Adjusts the volume by adding a delta value to the current volume level.
     * Ensures that the new volume value does not exceed minimum and maximum values.
     *
     * @param delta The value to be added to the current volume.
     * @uthor Ali Farhan
     */
    private void adjustVolume(float delta) {
        if (volumeControl != null) {
            float newVolume = Math.max(volumeControl.getMinimum(), Math.min(volumeControl.getMaximum(), volumeControl.getValue() + delta));
            volumeControl.setValue(newVolume);
        }
    }


    /**
     * Turns the sound off or on.
     * When the sound is turned off, the previous volume level is saved and the volume is set to minimum.
     * When the sound is turned on again, the previous volume level is restored.
     *
     * @uthor Ali Farhan
     */
    public void mute() {
        if (volumeControl != null) {
            if (!isMuted) {
                previousVolume = volumeControl.getValue();
                volumeControl.setValue(volumeControl.getMinimum());
                isMuted = true;
            } else {
                volumeControl.setValue(previousVolume);
                isMuted = false;
            }
        }
    }
}
