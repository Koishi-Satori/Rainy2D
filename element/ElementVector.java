package rainy2D.element;

import java.awt.image.BufferedImage;

public class ElementVector extends ElementImageInset {

    double speed;
    double angle;

    public ElementVector(double x, double y, int width, int height, double speed, double angle, BufferedImage img) {

        super(x, y, width, height, img);

        this.speed = speed;
        this.angle = angle;

    }

    public void setSpeed(double speed) {

        this.speed = speed;

    }

    public void setAngle(double angle) {

        this.angle = angle;

    }

    public double getSpeed() {

        return speed;

    }

    public double getAngle() {

        return angle;

    }

}
