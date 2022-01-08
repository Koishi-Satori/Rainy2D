package rainy2D.util;

public class MathData {

    public static int abs(int value) {

        return value >= 0 ? value : -value;

    }

    public static int round(double value) {

        return (int) (value);

    }

    public static double toDouble(int value) {

        return Double.valueOf(value);

    }

    public static double random(int min, int max) {

        return round(Math.random() * (max - min) + min);

    }

    public static double toRadians(double angle) {

        return angle / (3.1415926535 / 180);

    }

    public static double toAngle(double theta) {

        return theta * (3.1415926535 / 180);

    }

}
