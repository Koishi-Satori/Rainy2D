package rainy2D.shape;

import rainy2D.vector.R2DVector;

public class Circle extends Shape {

    public Circle(int x, int y, int radius) {

        width = radius * 2;
        height = radius * 2;
        offsetX = x - radius;
        offsetY = y - radius;

        this.x = x;
        this.y = y;

    }

    public boolean intersects(Circle circle) {

        double length = Math.sqrt(Math.pow(x - circle.getX(), 2) + Math.pow(y - circle.getY(), 2));

        return length < getRadius() + circle.getRadius();

    }

    public boolean isPointIn(int x, int y) {

        return R2DVector.distanceBetweenAB(x, y, getX(), getY()) < getRadius();

    }

    public int getRadius() {

        return width / 2;

    }

}
