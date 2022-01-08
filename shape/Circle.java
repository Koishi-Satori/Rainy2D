package rainy2D.shape;

import rainy2D.vector.R2DVector;

public class Circle extends Shape {

    public Circle(int x, int y, int r) {

        this.x = x;
        this.y = y;
        this.offsetX = x - r;
        this.offsetY = y - r;
        this.width = r;
        this.height = r;

    }
    
    public boolean intersects(Circle circle) {

        double length = Math.sqrt(Math.pow(x - circle.getX(), 2) + Math.pow(y - circle.getY(), 2));

        return length < width + circle.getWidth();

    }

    public boolean isPointIn(int x, int y) {

        return R2DVector.distanceBetweenAB(x, y, getCenterX(), getCenterY()) < width;

    }

}
