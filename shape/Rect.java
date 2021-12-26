package rainy2D.shape;

public class Rect {

    int x;
    int y;
    int width;
    int height;

    public Rect(int x, int y, int width, int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    public boolean intersects(Rect rect) {

        int width1 = this.width;
        int height1 = this.height;
        int width2 = rect.width;
        int height2 = rect.height;
        int x1 = this.x;
        int y1 = this.y;
        int x2 = rect.x;
        int y2 = rect.y;

        width2 += x2;
        height2 += y2;
        width1 += x1;
        height1 += y1;

        return ((width2 < x2 || width2 > x1)
                && (height2 < y2 || height2 > y1)
                && (width1 < x1 || width1 > x2)
                && (height1 < y1 || height1 > y2));

    }

    public int getX() {

        return x;

    }

    public int getY() {

        return y;

    }

    public int getWidth() {

        return width;

    }

    public int getHeight() {

        return height;

    }

}
