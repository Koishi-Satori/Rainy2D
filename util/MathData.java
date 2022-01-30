package rainy2D.util;

public class MathData {

    public static final double PI = 3.1415926535;

    public static int abs(int value) {

        return value >= 0 ? value : -value;

    }

    public static double absDouble(double value) {

        return value >= 0 ? value : -value;

    }

    public static int round(double value) {

        return (int) (value);

    }

    public static boolean inEqual(double value1, double value2, double maxField) {

        return absDouble(value1 - value2) < maxField;

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

    public static double downValue(double value, double downIncrease) {

        return downValue(value, downIncrease, 0);

    }

    public static double downValue(double value, double downIncrease, double min) {

        if(value - downIncrease > min) {
            return value - downIncrease;
        }
        else if(value - downIncrease <= min) {
            return min;
        }

        return value;

    }

    public static double upValue(double value, double increase, double max) {

        if(value + increase < max) {
            return value + increase;
        }
        else if(value + increase >= max) {
            return max;
        }

        return value;

    }

}
