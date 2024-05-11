package Model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
//@author Sleiman Sleiman


public class Music {
    private Clip backgroundMusic;
    private FloatControl volumeControl;

    public Music(String filename) {
        playBackgroundMusic(filename);
    }

    private void playBackgroundMusic(String filename) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
            volumeControl = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
    }

    public void setVolume(float volume) {
        if (volumeControl != null) {
            volumeControl.setValue(volume);
        }
    }

    public void mute(boolean mute) {
        if (volumeControl != null) {
            if (mute) {
                // Set volume to minimum (mute)
                volumeControl.setValue(volumeControl.getMinimum());
            } else {
                // Set volume to the previous value before mute
                volumeControl.setValue(volumeControl.getMaximum());
            }
        }
    }
}
