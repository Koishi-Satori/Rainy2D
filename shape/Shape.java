package rainy2D.shape;

import rainy2D.util.MathData;

public class Shape {

    int x;
    int y;
    int offsetX;
    int offsetY;
    int width;
    int height;

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

    /**
     * 比例获取方法
     * @param percent 百分比
     * @return 百分之x的长度
     */
    public int getX(double percent) {

        return MathData.round(offsetX + width * percent);

    }

    public int getY(double percent) {

        return MathData.round(offsetY + height * percent);

    }

    public int getWidth(double percent) {

        return MathData.round(width * percent);

    }

    public int getHeight(double percent) {

        return MathData.round(height * percent);

    }

    public int getX2() {

        return offsetX + width;

    }

    public int getY2() {

        return offsetY + height;

    }

    public int getX() {

        return x;

    }

    public int getY() {

        return y;

    }

}
