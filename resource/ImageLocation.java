package rainy2D.resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLocation {

    String path;

    public ImageLocation(String path) {

        this.path = path;

    }

    public BufferedImage getImage() {

        try {

            return ImageIO.read(ImageLocation.class.getResource("/" + path));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public int getWidth() {

        return getImage().getWidth(null);

    }

    public int getHeight() {

        return getImage().getHeight(null);

    }

}
