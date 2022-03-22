package sound;

import java.io.*;
import javax.swing.JOptionPane;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioInputStream;

public class Music {

    private File musicPath;
    private AudioInputStream audio;
    private Clip clip;

    public Music(String filepath) {
        try {
            this.musicPath = new File(filepath);
            if (musicPath.exists()) {
                this.audio = AudioSystem.getAudioInputStream(musicPath);
                this.clip = AudioSystem.getClip();
            } else throw new IllegalStateException();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't find file");
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    public void playMusic() throws LineUnavailableException, IOException {
        clip.open(this.audio);
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

}