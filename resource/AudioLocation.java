package rainy2D.resource;

import javax.sound.sampled.*;
import java.io.IOException;

public class AudioLocation extends Location {

    Clip playNow;

    public AudioLocation(String name) {

        super(name);

        init("bgm", ".wav");
        get();

    }

    public Clip get() {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(findLocalPath("/" + folder + "/" + name + format));
            playNow = AudioSystem.getClip();
            playNow.open(ais);
            ais.close();
            return playNow;
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static void newAudioPlayer(String name) {

        new AudioLocation(name).singlePlay();

    }

    public void singlePlay() {

        playNow.start();

    }

    public void loopPlay() {

        playNow.loop(-1);

    }

    public void stop() {

        playNow.stop();

    }

    public void setVolume(float value) {

        FloatControl fc = (FloatControl) playNow.getControl(FloatControl.Type.MASTER_GAIN);
        fc.setValue(value);

    }

}
