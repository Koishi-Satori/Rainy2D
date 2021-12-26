package rainy2D.shape;

public class Circle {

    int x;
    int y;
    int r;

    public Circle(int x, int y, int r) {

        this.x = x;
        this.y = y;
        this.r = r;

    }

    public boolean intersects(Circle circle) {

        double length = Math.sqrt(Math.pow(x - circle.getX(), 2) + Math.pow(y - circle.getY(), 2));

        return length < r + circle.getR();

    }

    public int getX() {

        return x;

    }

    public int getY() {

        return y;

    }

    public int getR() {

        return r;

    }

}
