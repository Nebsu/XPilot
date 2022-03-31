package sound;

import java.io.*;
import javax.swing.JOptionPane;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioInputStream;

public class Music {

    private File musicPath; // audio file
    private AudioInputStream audio; // audio input
    private Clip clip; // sample clip
    private static float volume = 1; // volume
    
    public Music(String filepath) {
        try {
            this.musicPath = new File(filepath);
            if (musicPath.exists()) {
                this.audio = AudioSystem.getAudioInputStream(musicPath);
                this.clip = AudioSystem.getClip();
            } else throw new FileNotFoundException();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't find file");
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    public void playMusic() throws LineUnavailableException, IOException {
        clip.open(this.audio);
        this.setVolume();
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        // Pause / Resume :
        // JOptionPane.showMessageDialog(null, "Click ok to pause");
        // long clipTimePosition = clip.getMicrosecondPosition();
        // clip.stop();
        // JOptionPane.showMessageDialog(null, "Hit ok to resume");
        // clip.setMicrosecondPosition(clipTimePosition);
        // clip.start();
        // JOptionPane.showMessageDialog(null, "Press OK to stop playing");
    }

    public void stopMusic() {
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