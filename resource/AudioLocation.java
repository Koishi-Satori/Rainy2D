package rainy2D.resource;

import javax.sound.sampled.*;
import java.io.IOException;

public class AudioLocation {

    String path;

    public AudioLocation(String path) {

        this.path = path;

    }

    public Clip get() {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(AudioLocation.class.getResource("/bgm/" + path));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            return clip;
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void play() {

        get().start();

    }

}
