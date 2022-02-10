package rainy2D.resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLocation extends Location {

    public ImageLocation(String name) {

        super(name);

        init("img", ".png");

    }

    public BufferedImage get() {

        try {
            return ImageIO.read(findLocalPath("/" + folder + "/" + name + format));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public int getWidth() {

        return get().getWidth();

    }

    public int getHeight() {

        return get().getHeight();

    }

}
