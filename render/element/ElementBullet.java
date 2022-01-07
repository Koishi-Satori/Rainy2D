package rainy2D.render.element;

import rainy2D.render.desktop.Window;
import rainy2D.resource.ImageLocation;
import rainy2D.shape.Rectangle;
import rainy2D.vector.R2DVector;

/**
 * 构造器：inset (shooter)
 * 超类：inset
 */
public class ElementBullet extends ElementImageInset {

    double speed;
    double angle;

    //BulletPool用构造器
    public ElementBullet() {

        super(0, 0, 0, 0, null);

    }

    public ElementBullet(Element shooter, int width, int height, double speed, double angle, ImageLocation iml) {

        super(shooter.x, shooter.y, width, height, iml);

        this.speed = speed;
        this.angle = angle;

    }

    public ElementBullet(double x, double y, int width, int height, double speed, double angle, ImageLocation iml) {

        super(x, y, width, height, iml);

        this.speed = speed;
        this.angle = angle;

    }

    /**
     * 从池中取出时调用设置属性
     */
    public void takeOutWith(double x, double y, int width, int height, double speed, double angle, ImageLocation iml) {

        this.width = width;
        this.height = height;
        this.speed = speed;
        this.angle = angle;
        this.iml = iml;
        this.callImageChange();
        this.x = x;
        this.y = y;

    }

    @Override
    public void tick(Window window) {

        super.tick(window);

        this.locate(R2DVector.vectorX(x, speed, angle), R2DVector.vectorY(y, speed, angle));

        Rectangle field = window.getScreenIn().getField();

        if(x + width < field.getX() ||
                x > field.getX() + field.getWidth() ||
                y + height < field.getY() ||
                y > field.getY() + field.getHeight()) {
            this.setOutWindow(true);
        } else {
            this.setOutWindow(false);
        }

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
