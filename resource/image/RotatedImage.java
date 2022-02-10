package rainy2D.resource.image;

import rainy2D.render.graphic.Graphic2D;

import java.awt.image.BufferedImage;

public class RotatedImage {

    BufferedImage img;

    double angle;

    public RotatedImage(BufferedImage img, double angle) {

        this.img = img;
        this.angle = angle;

    }

    public BufferedImage rotate() {

        return Graphic2D.rotate(img, angle);

    }

    public void setAngle(double angle) {

        this.angle = angle;

    }

    public double getAngle() {

        return angle;

    }

    public void setImage(BufferedImage img) {

        this.img = img;

    }

    public BufferedImage getImage() {

        return img;

    }

}
