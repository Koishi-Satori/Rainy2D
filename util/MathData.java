package rainy2D.util;

public class MathData {

    public static int toInt(double value) {

        return (int) value;

    }

    public static double toDouble(int value) {

        return (double) value;

    }

    public static double toDouble(long value) {

        return (double) value;

    }

    public static long toLong(double value) {

        return (long) value;

    }

    public static double random(int min, int max) {

        double r = Math.random() * max * 2;
        while(!(r > min && r < max)) {
            r = Math.random() * max * 2;
        }
        return r;

    }

    public static double toTheta(double angle) {

        return angle / (3.1415926535 / 180);

    }

    public static double toAngle(double theta) {

        return theta * (3.1415926535 / 180);

    }

}
