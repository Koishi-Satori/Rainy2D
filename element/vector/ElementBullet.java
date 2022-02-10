package rainy2D.element.vector;

import java.awt.image.BufferedImage;

/**
 * 构造器：inset
 * 超类：inset
 */
public class ElementBullet extends ElementVector {

    /**
     * 两种状态，可用于判定玩家子弹和敌方子弹
     */
    public static final int HIT_PLAYER = 0;
    public static final int HIT_ENEMY = 1;

    private int state;

    int force;

    /**
     * 模板构造器
     */
    public ElementBullet(int width, int height, double speed, double angle, int hitForce, BufferedImage img) {

        this(0, 0, width, height, speed, angle, hitForce, img);

    }

    public ElementBullet(double x, double y, int width, int height, double speed, double angle, int hitForce, BufferedImage img) {

        super(x, y, width, height, speed, angle, img);

        force = hitForce;

    }

    public void setState(int state) {

        this.state = state;

    }

    public void setForce(int force) {

        this.force = force;

    }

    public int getState() {

        return state;

    }

    public int getForce() {

        return force;

    }

}
