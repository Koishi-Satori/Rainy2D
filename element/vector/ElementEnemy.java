package rainy2D.element.vector;

import java.awt.image.BufferedImage;

/**
 * 构造器：inset
 * 超类：inset
 */
public class ElementEnemy extends ElementVector {

    int health;
    int maxHealth;

    /**
     * 模板构造器
     */
    public ElementEnemy(int width, int height, double speed, double angle, int startHealth, BufferedImage img) {

        this(0, 0, width, height, speed, angle, startHealth, img);

    }

    public ElementEnemy(double x, double y, int width, int height, double speed, double angle, int startHealth, BufferedImage img) {

        super(x, y, width, height, speed, angle, img);

        health = startHealth;
        maxHealth = startHealth;

    }

    public void setHealth(int health) {

        this.health = health;

    }

    public void reHealth() {

        health = maxHealth;

    }

    public int getHealth() {

        return health;

    }

    public int getMaxHealth() {

        return maxHealth;

    }

    public void hit(int force) {

        health -= force;

    }

    public boolean isDead() {

        return health <= 0;

    }

}
