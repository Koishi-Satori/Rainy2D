package rainy2D.shape;

public class Rect2D extends Rect {

    public Rect2D(int x1, int y1, int x2, int y2) {

        super(x1, y1, x2 - x1, y2 - y1);

    }

}
