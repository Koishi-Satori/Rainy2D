package rainy2D.vector;

public class EntityGravity {

    public static double gravityFall(int overLandTime) {

        return overLandTime * 9.8 / 20;

    }

    public static double gravityJump(int overLandTime, int jumpForce) {

        return jumpForce - overLandTime * 9.8 / 20;

    }

}
