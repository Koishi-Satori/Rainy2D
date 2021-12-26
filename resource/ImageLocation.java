package rainy2D.resource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLocation {

    String path;

    public ImageLocation(String path) {

        this.path = path;

    }

    public Image getImage() {

        File file = new File(path);

        try {
            BufferedImage image = ImageIO.read(file);
            return image;
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
