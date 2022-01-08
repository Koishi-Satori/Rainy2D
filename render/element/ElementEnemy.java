package rainy2D.render.element;

import rainy2D.render.desktop.Window;
import rainy2D.resource.ImageLocation;
import rainy2D.util.MathData;
import rainy2D.vector.R2DVector;

public class ElementEnemy extends ElementImageInset {

    double speed;
    double angle;

    public ElementEnemy(double x, double y, double speed, double angle, ImageLocation iml) {

        super(x, y, iml.getWidth(), iml.getHeight(), iml);

        this.speed = speed;
        this.angle = angle;

    }

    @Override
    public void tick(Window window) {

        this.checkOutWindow(window);

    }

    public void randomMove(int randomRange) {

        this.angle += MathData.random(-randomRange, randomRange);
        this.locate(R2DVector.vectorX(x, speed, angle), R2DVector.vectorY(y, speed, angle));

    }

    public void move() {

        this.randomMove(0);

    }

}
