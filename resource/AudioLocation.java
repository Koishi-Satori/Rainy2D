package rainy2D.resource;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class AudioLocation implements Location {

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
