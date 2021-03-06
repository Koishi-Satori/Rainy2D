package rainy2D.vector;

import rainy2D.element.Element;
import rainy2D.util.Maths;

public class R2DVector {

    public static double vectorX(double x, double speed, double angle) {

        return x + speed * java.lang.Math.cos(Maths.toRadians(angle));

    }

    public static double vectorY(double y, double speed, double angle) {

        return y + speed * java.lang.Math.sin(Maths.toRadians(angle));

    }

    /**
     * @param e1 被动对象
     * @param e2 主动对象
     * @return 两个element之间的角度
     */
    public static double angleBetweenAB(Element e1, Element e2) {

        return Maths.toAngle(java.lang.Math.atan2(e1.getY() - e2.getY(), e1.getX() - e2.getX()));

    }

    public static double angleBetweenAB(double x1, double y1, double x2, double y2) {

        return Maths.toAngle(java.lang.Math.atan2(y1 - y2, x1 - x2));

    }

    public static int distanceBetweenAB(Element e1, Element e2) {

        return distanceBetweenAB(e1.getX(), e1.getY(), e2.getX(), e2.getY());

    }

    public static int distanceBetweenAB(double x1, double y1, double x2, double y2) {

        int xDistance = Maths.abs(Maths.round(x1 - x2));
        int yDistance = Maths.abs(Maths.round(y1 - y2));
        return Maths.round(java.lang.Math.sqrt(xDistance * xDistance + yDistance * yDistance));

    }

}
