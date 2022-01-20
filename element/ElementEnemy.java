package rainy2D.element;

import rainy2D.render.desktop.Window;
import rainy2D.vector.R2DVector;

import java.awt.image.BufferedImage;

public class ElementEnemy extends ElementVector {

    public ElementEnemy(double x, double y, int width, int height, double speed, double angle, BufferedImage img) {

        super(x, y, width, height, speed, angle, img);

    }

    @Override
    public void tick(Window window) {

        this.checkOutWindow(window);
        this.locate(R2DVector.vectorX(x, speed, angle), R2DVector.vectorY(y, speed, angle));

    }

    @Override
    public ElementEnemy getClone() {

        return new ElementEnemy(x, y, width, height, speed, angle, img);

    }

}
