package rainy2D.shape;

public class Shape {

    int x;
    int y;
    int offsetX;
    int offsetY;
    int width;
    int height;

    public int getX() {

        return x;

    }

    public int getY() {

        return y;

    }

    public int getOffsetX() {

        return offsetX;

    }

    public int getOffsetY() {

        return offsetY;

    }

    public int getWidth() {

        return width;

    }

    public int getHeight() {

        return height;

    }

    public int getX2() {

        return width - x;

    }

    public int getY2() {

        return height - y;

    }

    public int getCenterX() {

        return x + width / 2;

    }

    public int getCenterY() {

        return y + height / 2;

    }

}
