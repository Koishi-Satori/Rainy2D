package rainy2D.render.element;

import rainy2D.render.desktop.Window;
import rainy2D.resource.info.VectorInfo;
import rainy2D.resource.location.ImageLocation;
import rainy2D.shape.Rectangle;
import rainy2D.vector.R2DVector;

import java.awt.image.BufferedImage;

/**
 * 构造器：inset
 * 超类：inset
 */
public class ElementBullet extends ElementImageInset {

    double speed;
    double angle;

    /**
     * BulletCacheList用构造器
     */
    public ElementBullet() {

        super(0, 0, 0, 0, null);

    }

    public ElementBullet(double x, double y, double speed, double angle, ImageLocation iml) {

        this(x, y, iml.getWidth(), iml.getHeight(), speed, angle, iml);

    }

    /**
     * 推荐使用的构造器，虽然比较难看但是优化很好
     */
    public ElementBullet(double x, double y, int width, int height, double speed, double angle, ImageLocation iml) {

        super(x, y, iml.getWidth(), iml.getHeight(), iml);

        this.speed = speed;
        this.angle = angle;

    }

    /**
     * 从池中取出时调用设置属性
     */
    public void setProperties(double x, double y, int width, int height, double speed, double angle, ImageLocation iml) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.angle = angle;
        this.iml = iml;
        this.callImageChange();

    }

    @Override
    public void tick(Window window) {

        super.tick(window);

        this.locate(R2DVector.vectorX(x, speed, angle), R2DVector.vectorY(y, speed, angle));

        this.checkOutWindow(window);

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
