package rainy2D.resource;

import javax.sound.sampled.*;
import java.io.IOException;

public class AudioLocation extends Location {

    Clip clip;

    public AudioLocation(String name) {

        super(name);

        init("bgm", ".wav");

    }

    public Clip get() {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(AudioLocation.class.getResource("/" + folder + "/" + name + format));
            clip = AudioSystem.getClip();
            clip.open(ais);
            return clip;
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void singlePlay() {

        clip.start();

    }

    public void loopPlay(int time) {

        clip.loop(time);

    }

    public void stop() {

        clip.stop();

    }

}
