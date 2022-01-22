package rainy2D.shape;

import rainy2D.vector.R2DVector;

public class Circle extends Shape {

    int radius;

    public Circle(int x, int y, int r) {

        this.x = x - r;
        this.y = y - r;
        this.radius = r;

    }

    public boolean intersects(Circle circle) {

        double length = Math.sqrt(Math.pow(x - circle.getCenterX(), 2) + Math.pow(y - circle.getCenterY(), 2));

        return length < radius + circle.getRadius();

    }

    public boolean isPointIn(int x, int y) {

        return R2DVector.distanceBetweenAB(x, y, getCenterX(), getCenterY()) < getRadius();

    }

    public int getCenterX() {

        return x + width;

    }

    public int getCenterY() {

        return y + width;

    }

    public int getRadius() {

        return radius;

    }

}
