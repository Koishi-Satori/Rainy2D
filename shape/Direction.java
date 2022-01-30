package rainy2D.shape;

public class Direction {

    public static final int LEFT = 0;
    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;

    public static int rotateRight(int direction) {

        return direction == DOWN ? LEFT : direction + 1;

    }

    public static int rotateLeft(int direction) {

        return direction == LEFT ? DOWN : direction - 1;

    }

    public static int opposite(int direction) {

        if(direction + 2 > 3) {
            return direction - 2;
        }
        else {
            return direction + 2;
        }

    }

}
