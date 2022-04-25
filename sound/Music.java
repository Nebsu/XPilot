/**
 * This class is used to play music
 */
package sound;

import java.io.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioInputStream;

public class Music {

    private File musicPath;
    private AudioInputStream audio;
    private Clip clip;
    private FloatControl GAIN;
    private static float MUSIC_VOLUME = 0.0f;

    public final float getMusicVolume() {return MUSIC_VOLUME;}
    public static final void setMusicVolume(float volume) {MUSIC_VOLUME = volume;}
    
    public Music(String filepath) {
        try {
            this.musicPath = new File(filepath);
            if (musicPath.exists()) {
                this.audio = AudioSystem.getAudioInputStream(musicPath);
                this.clip = AudioSystem.getClip();
                this.clip.open(this.audio);
                this.initVolume();
            } else throw new FileNotFoundException();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't find file");
            System.out.println(e);
            System.exit(1);
        }
    }

    private void initVolume() {
        GAIN = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        GAIN.setValue(MUSIC_VOLUME);
    }

    public void changeGain(float gain) {
        GAIN.setValue(gain);
    }

    public void playMusic() throws LineUnavailableException, IOException {
        clip.setMicrosecondPosition(0);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopMusic() {
        clip.stop();
    }

    public void kill() {
        clip.close();
    }

}