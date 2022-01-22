package rainy2D.shape;

import rainy2D.util.MathData;

/**
 * x、y即为element中的offset x、y
 * 如要获取中心，可用百分比，circle可直接获取
 * x2、y2为右下角的坐标
 */
public class Shape {

    int x;
    int y;
    int width;
    int height;

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

    /**
     * 比例获取方法
     * @param percent 百分比
     * @return 百分之x的长度
     */
    public int getX(double percent) {

        return MathData.round(x + width * percent);

    }

    public int getY(double percent) {

        return MathData.round(y + height * percent);

    }

    public int getWidth(double percent) {

        return MathData.round(width * percent);

    }

    public int getHeight(double percent) {

        return MathData.round(height * percent);

    }

    public int getX2() {

        return width - x;

    }

    public int getY2() {

        return height - y;

    }

}
