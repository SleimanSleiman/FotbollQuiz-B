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
     *
     * @param filename
     * @author Ali Farhan § Sleiman Sleiman
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
     * Metod för att höja musik volymen
     * @author Ali Farhan
     */
    public void increaseVolume() {
        adjustVolume(5.0f);
    }


    /**
     * Metod för att sänka musik volymen
     * @author Ali Farhan
     */
    public void decreaseVolume() {
        adjustVolume(-5.0f);
    }


    /**
     * Justerar volymen genom att lägga till ett delta-värde till den aktuella volymnivån.
     * Säkerställer att den nya volym värdet inte går över min och max värden.
     *
     * @param delta Värdet som ska läggas till den nuvarande volymen
     * @author Ali Farhan
     */
    private void adjustVolume(float delta) {
        if (volumeControl != null) {
            float newVolume = Math.max(volumeControl.getMinimum(), Math.min(volumeControl.getMaximum(), volumeControl.getValue() + delta));
            volumeControl.setValue(newVolume);
        }
    }

    /**
     * Metod som stänger av eller sätter på ljudet
     * @author Ali Farhan
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

    public void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
            backgroundMusic.close();
        }
    }
}
