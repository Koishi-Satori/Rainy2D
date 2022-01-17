package rainy2D.render.element;

import rainy2D.render.desktop.Window;
import rainy2D.render.helper.RenderHelper;
import rainy2D.vector.R2DVector;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 构造器：inset
 * 超类：inset
 */
public class ElementBullet extends ElementImageInset {

    public static int EFFECT_DOWN_VALUE = 5;

    public static final int HIT_PLAYER = 0;
    public static final int HIT_ENEMY = 1;
    public int state;

    double speed;
    double angle;

    int effectWidth;

    /**
     * BulletCacheList用构造器
     */
    public ElementBullet() {

        this(0, 0, 0, 0, 0, 0, null);

    }

    /**
     * 推荐使用的构造器，虽然比较难看但是优化很好
     */
    public ElementBullet(double x, double y, int width, int height, double speed, double angle, BufferedImage img) {

        super(x, y, width, height, img);

        this.speed = speed;
        this.angle = angle;

        this.resetEffect();
        this.setState(0);

    }

    @Override
    public void tick(Window window) {

        this.appearEffect(EFFECT_DOWN_VALUE);

        this.locate(R2DVector.vectorX(x, speed, angle), R2DVector.vectorY(y, speed, angle));
        this.checkOutWindow(window);

    }

    @Override
    public void render(Graphics g) {

        if(effectWidth == width) {
            super.render(g);
        }
        else {
            RenderHelper.renderIn(x, y, effectWidth, effectWidth, img, g);
        }

    }

    public void resetEffect() {

        this.effectWidth = width * 2;

    }

    /**
     * 子弹出现时的效果
     * @param downValue 缩小速度值
     */
    public void appearEffect(int downValue) {

        this.effectWidth -= downValue;

        if(effectWidth < width) {
            this.effectWidth = width;
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

    public void setState(int state) {

        this.state = state;

    }

    @Override
    public ElementBullet getClone() {

        return new ElementBullet(x, y, width, height, speed, angle, img);

    }

}
