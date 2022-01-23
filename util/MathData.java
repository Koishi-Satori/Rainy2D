package rainy2D.util;

public class MathData {

    public static final double PI = 3.1415926535;

    public static int abs(int value) {

        return value >= 0 ? value : -value;

    }

    public static int round(double value) {

        return (int) (value);

    }

    public static boolean inEqual(double value1, double value2, double maxField) {

        if(value1 > value2) {
            return value1 - value2 < maxField;
        }
        else if(value1 < value2) {
            return value2 - value1 < maxField;
        }

        return true;

    }

    public static double toDouble(int value) {

        return Double.valueOf(value);

    }

    public static double random(double min, double max) {

        return Math.random() * (max - min) + min;

    }

    public static double toRadians(double angle) {

        return angle / (180 / PI);

    }

    public static double toAngle(double theta) {

        return theta * (180 / PI);

    }

}
