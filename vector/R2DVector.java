package rainy2D.vector;

import rainy2D.render.element.Element;
import rainy2D.util.MathData;

import java.awt.*;

public class R2DVector {

    public static double vectorX(double x, double speed, double angle) {

        return x + speed * Math.cos(MathData.toRadians(angle));

    }

    public static double vectorY(double y, double speed, double angle) {

        return y + speed * Math.sin(MathData.toRadians(angle));

    }

    /**
     * @param e1 被动对象
     * @param e2 主动对象
     * @return 两个element之间的角度
     */
    public static double angleBetweenAB(Element e1, Element e2) {

        return MathData.toAngle(Math.atan2(e1.getY() - e2.getY(), e1.getX() - e2.getX()));

    }

    /**
     * @param p1
     * @param p2
     * @return 两点之间的距离
     */
    public static int distanceBetweenAB(Point p1, Point p2) {

        int xDistance = MathData.abs(p1.x - p2.x);
        int yDistance = MathData.abs(p1.y - p2.y);
        return MathData.round(Math.sqrt(xDistance * xDistance + yDistance * yDistance));

    }

    public static int distanceBetweenAB(int x1, int y1, int x2, int y2) {

        int xDistance = MathData.abs(x1 - x2);
        int yDistance = MathData.abs(y1 - y2);
        return MathData.round(Math.sqrt(xDistance * xDistance + yDistance * yDistance));

    }

}
