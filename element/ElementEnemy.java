package rainy2D.element;

import rainy2D.render.desktop.Window;
import rainy2D.vector.R2DVector;

import java.awt.image.BufferedImage;

public class ElementEnemy extends ElementVector {

    int health;

    public ElementEnemy(double x, double y, int width, int height, double speed, double angle, BufferedImage img) {

        super(x, y, width, height, speed, angle, img);

    }

    @Override
    public void tick(Window window) {

        checkOutWindow(window);
        locate(R2DVector.vectorX(x, speed, angle), R2DVector.vectorY(y, speed, angle));

    }

    public final void setStartHealth(int health) {

        this.health = health;

    }

    public int getHealth() {

        return health;

    }

    public void hit(int force) {

        health -= force;

    }

    public boolean isDead() {

        return health <= 0;

    }

}
