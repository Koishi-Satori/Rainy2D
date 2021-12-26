package rainy2D.render.element;

import rainy2D.render.RenderHelper;
import rainy2D.render.window.Window;
import rainy2D.resource.ImageLocation;
import rainy2D.vector.EntityVector;

import java.awt.*;

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

        super(shooter.getX(), shooter.getY(), width, height, iml);

        this.speed = speed;
        this.angle = angle;

    }

    public ElementBullet(double x, double y, int width, int height, double speed, double angle, ImageLocation iml) {

        super(x, y, width, height, iml);

        this.speed = speed;
        this.angle = angle;

    }

    //从池中取出时调用设置属性
    public void takeOutWith(double x, double y, int width, int height, double speed, double angle, ImageLocation iml) {

        this.width = width;
        this.height = height;
        this.speed = speed;
        this.angle = angle;
        this.iml = iml;
        this.x = x;
        this.y = y;

    }

    @Override
    public void tick(Window window) {

        super.tick(window);

        this.locate(EntityVector.vectorX(x, speed, angle), EntityVector.vectorY(y, speed, angle));

        if(x + width + 10 < 0 || x + 10 > window.getWidth() || y + height + 85 < window.getY() || y + 10 > window.getHeight()) {
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
