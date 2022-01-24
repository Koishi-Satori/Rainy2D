package rainy2D.element;

import rainy2D.render.desktop.Window;
import rainy2D.vector.R2DVector;

import java.awt.image.BufferedImage;

/**
 * 构造器：inset
 * 超类：inset
 */
public class ElementVector extends ElementImageInset {

    double speed;
    double angle;

    public ElementVector(int width, int height, double speed, double angle, BufferedImage img) {

        super(0, 0, width, height, img);

        this.speed = speed;
        this.angle = angle;

    }

    public ElementVector(double x, double y, int width, int height, double speed, double angle, BufferedImage img) {

        super(x, y, width, height, img);

        this.speed = speed;
        this.angle = angle;

    }

    @Override
    public void tick(Window window) {

        locate(R2DVector.vectorX(x, speed, angle), R2DVector.vectorY(y, speed, angle));

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
