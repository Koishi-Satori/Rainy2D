package rainy2D.vector;

public class DCBullet {

    //不可旋转子弹，不会改变方向，value代表子弹条数
    public static double function(int timer, int value) {

        return timer * (360 / value);

    }

    //可旋转子弹，value代表子弹条数，speed决定了子弹旋转速度
    public static double functionRotate(int timer, int value, int speed) {

        return timer * (360 / value) + speed * timer;

    }

}
