/** This class is used to play the music files */

package src.sound;

import java.io.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioInputStream;

public final class Music {

    private File musicPath;
    private AudioInputStream audio;
    private Clip clip;
    private FloatControl GAIN;
    private static float MUSIC_VOLUME = 0.0f;
    private long startPos;
    
    public Music(String filepath) {
        try {
            this.musicPath = new File(filepath);
            if (musicPath.exists()) {
                this.audio = AudioSystem.getAudioInputStream(musicPath);
                this.clip = AudioSystem.getClip();
                this.clip.open(this.audio);
                this.startPos = 0;
                this.initVolume();
            } else throw new FileNotFoundException();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't find file");
            System.out.println(e);
            System.exit(1);
        }
    }

    private final void initVolume() {
        GAIN = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        GAIN.setValue(MUSIC_VOLUME);
    }

    // Getters / Setters :
    public final float getMusicVolume() {return MUSIC_VOLUME;}
    public static final void setMusicVolume(float volume) {MUSIC_VOLUME = volume;}

    public final void changeGain(float gain) {
        GAIN.setValue(gain);
    }

    public final void playMusic(boolean reset) throws LineUnavailableException, IOException {
        if (!reset) {
            clip.setMicrosecondPosition(startPos);
        }else{
            this.startPos = 0;
            clip.setMicrosecondPosition(startPos);
        }
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public final void stopMusic() {
        startPos = clip.getMicrosecondPosition();
        clip.stop();
    }

}