package rainy2D.element.vector;

import rainy2D.element.image.ElementImageInset;
import rainy2D.render.desktop.Window;
import rainy2D.vector.R2DVector;

import java.awt.image.BufferedImage;

/**
 * 构造器：inset
 * 超类：inset
 */
public class ElementVector extends ElementImageInset {

    double speed;
    double speedBackup;
    double angle;

    public ElementVector(int width, int height, double speed, double angle, BufferedImage img) {

        this(0, 0, width, height, speed, angle, img);

    }

    public ElementVector(double x, double y, int width, int height, double speed, double angle, BufferedImage img) {

        super(x, y, width, height, img);

        this.speed = speed;
        this.angle = angle;

        speedBackup = speed;

    }

    @Override
    public void tick(Window window) {

        locate(R2DVector.vectorX(x, speed, angle), R2DVector.vectorY(y, speed, angle));

    }

    public void moveTo(int px, int py) {

        int distance = R2DVector.distanceBetweenAB(px, py, x, y);
        if(distance < speedBackup){
            setSpeed(0);
        }
        else {
            setSpeed(speedBackup);
        }
        setAngle(R2DVector.angleBetweenAB(px, py, x, y));

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
