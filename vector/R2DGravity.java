package rainy2D.vector;

public class R2DGravity {

    public static final double ANGLE_G = -90;

    public static double gravityFall(int overLandTime) {

        return overLandTime * 9.8 / 20;

    }

    public static double gravityJump(int overLandTime, int jumpForce) {

        return jumpForce - overLandTime * 9.8 / 20;

    }

}
