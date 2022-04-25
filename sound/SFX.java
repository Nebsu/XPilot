/**
 * This class is used to play sound effects
 */
package sound;

import java.io.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioInputStream;

public class SFX {
 
    private File file;
    private AudioInputStream audio;
    private Clip clip;
    private FloatControl GAIN;
    private static float SFX_VOLUME = 0.0f;

    public final float getMusicVolume() {return SFX_VOLUME;}
    public static final void setMusicVolume(float volume) {SFX_VOLUME = volume;}

    public SFX(String filepath) {
        try {
            this.file = new File(filepath);
            if (file.exists()) {
                this.audio = AudioSystem.getAudioInputStream(file);
                this.clip = AudioSystem.getClip();
                this.clip.open(this.audio); 
                this.initVolume();
            } else throw new IllegalStateException();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't find file");
            System.out.println(e);
            System.exit(1);
        }
    }

    private void initVolume() {
        GAIN = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        GAIN.setValue(SFX_VOLUME); // Reduce volume by 10 decibels.
    }

    public void playSound() throws LineUnavailableException, IOException {
        clip.start();
    }

    public void stopSound() {
        clip.stop();
    }

    public void kill() {
        clip.close();
    }

}