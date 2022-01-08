package rainy2D.render.element;

import rainy2D.render.desktop.Window;
import rainy2D.render.helper.RenderHelper;
import rainy2D.resource.ImageLocation;
import rainy2D.vector.R2DVector;

import java.awt.*;
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

        super(0, 0, 0, 0, (BufferedImage) null);

    }

    public ElementBullet(double x, double y, int width, int height, double speed, double angle, ImageLocation iml) {

        this(x, y, width, height, speed, angle, iml.get());

    }

    /**
     * 推荐使用的构造器，虽然比较难看但是优化很好
     */
    public ElementBullet(double x, double y, int width, int height, double speed, double angle, BufferedImage img) {

        super(x, y, width, height, img);

        this.speed = speed;
        this.angle = angle;

        this.effectWidth = width * 2;

    }

    /**
     * 从池中取出时调用设置属性
     * 用图片是为了节约性能，高度复用
     */
    public void setProperties(double x, double y, int width, int height, double speed, double angle, BufferedImage img) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.effectWidth = width * 2;
        this.height = height;
        this.speed = speed;
        this.angle = angle;
        this.img = img;

    }

    @Override
    public void tick(Window window) {

        super.tick(window);

        this.appearEffect(5);

        this.locate(R2DVector.vectorX(x, speed, angle), R2DVector.vectorY(y, speed, angle));
        this.checkOutWindow(window);

    }

    @Override
    public void render(Graphics g) {

        super.render(g);

        if(effectWidth > width) {
            RenderHelper.renderIn(x, y, effectWidth, effectWidth, img, g);
        }

    }

    int effectWidth;

    /**
     * 子弹出现时的效果
     * @param downValue 缩小速度值
     */
    public void appearEffect(int downValue) {

        if(effectWidth - downValue > 0) {
            this.effectWidth -= downValue;
        }
        else {
            this.effectWidth = 0;
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
