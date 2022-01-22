package rainy2D.vector;

import rainy2D.element.Element;
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

        return distanceBetweenAB(p1.x, p1.y, p2.x, p2.y);

    }

    public static int distanceBetweenAB(Element e1, Element e2) {

        return distanceBetweenAB(e1.getX(), e1.getY(), e2.getX(), e2.getY());

    }

    public static int distanceBetweenAB(double x1, double y1, double x2, double y2) {

        int xDistance = MathData.abs(MathData.round(x1 - x2));
        int yDistance = MathData.abs(MathData.round(y1 - y2));
        return MathData.round(Math.sqrt(xDistance * xDistance + yDistance * yDistance));

    }

}
