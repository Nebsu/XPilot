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
 
    private File file; // fichier
    private AudioInputStream audio;
    private Clip clip;
    private static float volume = 1;

    public SFX(String filepath) {
        try {
            this.file = new File(filepath);
            if (file.exists()) {
                this.audio = AudioSystem.getAudioInputStream(file);
                this.clip = AudioSystem.getClip();
                this.clip.open(this.audio); 
            } else throw new IllegalStateException();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't find file");
            System.out.println(e);
            System.exit(1);
        }
    }

    public void playSound() throws LineUnavailableException, IOException {
        this.setVolume();
        clip.start();
    }

    public void stopSound() {
        clip.stop();
    }

    public void kill() {
        clip.close();
    }

    public void setVolume() {
        final FloatControl control =  (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = control.getMaximum() - control.getMinimum();
        float gain = (range * volume) + control.getMinimum();
        control.setValue(gain);
    }

}