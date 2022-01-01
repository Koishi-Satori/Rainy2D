package rainy2D.vector;

import rainy2D.render.element.Element;
import rainy2D.util.MathData;

public class EntityVector {

    public static double vectorX(double x, double speed, double angle) {

        return x + speed * Math.cos(MathData.toRadians(angle));

    }

    public static double vectorY(double y, double speed, double angle) {

        return y + speed * Math.sin(MathData.toRadians(angle));

    }

    //两个element之间的角度
    public static double angleBetweenAB(Element e1, Element e2) {

        return MathData.toAngle(Math.atan2(e1.getY() - e2.getY(), e1.getX() - e2.getX()));

    }

}
