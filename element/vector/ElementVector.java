package rainy2D.element.vector;

import rainy2D.element.image.ElementImageInset;
import rainy2D.render.desktop.Canvas;
import rainy2D.render.graphic.Graphic2D;
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

    boolean canBeRotated;

    public ElementVector(int width, int height, double speed, double angle, BufferedImage img) {

        this(0, 0, width, height, speed, angle, img);

    }

    public ElementVector(double x, double y, int width, int height, double speed, double angle, BufferedImage img) {

        super(x, y, width, height, img);

        this.speed = speed;
        this.angle = angle;

        speedBackup = speed;
        updateImage();

    }

    public void tick(Canvas canvas) {

        locate(R2DVector.vectorX(x, speed, angle), R2DVector.vectorY(y, speed, angle));

    }

    public void updateImage() {

        if(canBeRotated && imgBackup != null) {
            img = Graphic2D.rotate(imgBackup, angle);
        }

    }

    /**
     * 设置是否可以旋转
     * @param canBeRotated 是否可旋转
     * @param updateImage 是否直接更新旋转图片
     */
    public void rotateState(boolean canBeRotated, boolean updateImage) {

        this.canBeRotated = canBeRotated;
        if(updateImage) {
            updateImage();
        }

    }

    public void moveTo(int px, int py) {

        int distance = R2DVector.distanceBetweenAB(px, py, x, y);
        if(distance < speedBackup){
            setSpeed(0);
            setAngle(0);
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
        updateImage();

    }

    public void setImage(BufferedImage img) {

        super.setImage(img);
        updateImage();

    }

    public double getSpeed() {

        return speed;

    }

    public double getAngle() {

        return angle;

    }

}
