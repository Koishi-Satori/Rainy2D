package rainy2D.element;

import rainy2D.render.desktop.Window;
import rainy2D.render.graphic.Graphic;
import rainy2D.render.graphic.Graphic2D;
import rainy2D.vector.R2DVector;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 构造器：inset
 * 超类：inset
 */
public class ElementBullet extends ElementVector {

    public static int EFFECT_DOWN_VALUE = 5;

    /**
     * 两种状态，可用于判定玩家子弹和敌方子弹
     */
    public static final int HIT_PLAYER = 0;
    public static final int HIT_ENEMY = 1;

    private int state;
    private int effectWidth;
    private boolean canBeRotated;

    BufferedImage imageBefore;

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

        super(x, y, width, height, speed, angle, img);

        imageBefore = img;

        resetEffect();
        updateImage();

    }

    @Override
    public void tick(Window window) {

        appearEffect(EFFECT_DOWN_VALUE);

        checkOutWindow(window);
        locate(R2DVector.vectorX(x, speed, angle), R2DVector.vectorY(y, speed, angle));

    }

    @Override
    public void render(Graphics g) {

        if(effectWidth == width) {
            super.render(g);
        }
        else {
            Graphic.renderIn(x, y, effectWidth, effectWidth, img, g);
        }

    }

    public void resetEffect() {

        effectWidth = width * 2;

    }

    /**
     * 子弹出现时的效果
     * @param downValue 缩小速度值
     */
    private void appearEffect(int downValue) {

        effectWidth -= downValue;

        if(effectWidth < width) {
            effectWidth = width;
        }

    }

    public void updateImage() {

        if(canBeRotated && imageBefore != null) {
            img = Graphic2D.rotate(imageBefore, angle);
        }

    }

    public void rotateState(boolean canBeRotated, boolean updateImage) {

        this.canBeRotated = canBeRotated;
        if(updateImage) {
            updateImage();
        }

    }

    @Override
    public void setAngle(double angle) {

        this.angle = angle;
        updateImage();

    }

    @Override
    public void setImage(BufferedImage img) {

        this.img = img;
        imageBefore = img;
        updateImage();

    }

    public void setState(int state) {

        this.state = state;

    }

    public int getState() {

        return state;

    }

}
