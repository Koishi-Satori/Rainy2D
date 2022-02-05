package rainy2D.element.vector;

import java.awt.image.BufferedImage;

/**
 * 构造器：inset
 * 超类：inset
 */
public class ElementBoss extends ElementEnemy {

    /**
     * 模板构造器
     */
    public ElementBoss(int width, int height, double speed, double angle, int startHealth, BufferedImage img) {

        super(width, height, speed, angle, startHealth, img);

    }

    public ElementBoss(double x, double y, int width, int height, double speed, double angle, int startHealth, BufferedImage img) {

        super(x, y, width, height, speed, angle, startHealth, img);

    }

    public boolean isBoss() {

        return true;

    }

}
