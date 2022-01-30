package rainy2D.resource;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * 一个location只能储存一个音乐！注意
 */
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
            ais.close();
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

        clip.loop(time - 1);

    }

    public void stop() {

        clip.stop();

    }

}
