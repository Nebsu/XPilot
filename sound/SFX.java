/** This class is used to play the sound effects files*/

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

    private final void initVolume() {
        GAIN = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        GAIN.setValue(SFX_VOLUME); // Reduce volume by 10 decibels.
    }

    // Getters / Setters :
    public final float getMusicVolume() {return SFX_VOLUME;}
    public static final void setMusicVolume(float volume) {SFX_VOLUME = volume;}

    public final void playSound() throws LineUnavailableException, IOException {
        clip.start();
    }

    public final void stopSound() {
        clip.stop();
    }

}