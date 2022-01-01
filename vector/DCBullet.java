package rainy2D.vector;

import rainy2D.resource.ImageLocation;

public class DCBullet {

    public static final ImageLocation BL_LIGHT = new ImageLocation("img/plp.png");

    /**
     * 不可旋转子弹，不会改变方向
     * @param timer 传入screen的getTimer方法
     * @param value 子弹条数
     */
    public static double function(int timer, int value) {

        return timer * (360 / value);

    }

    /**
     * 可旋转子弹，会改变方向
     * @param timer 传入screen的getTimer方法
     * @param value 子弹条数
     * @param speed 子弹口移动速度
     */
    public static double functionRotate(int timer, int value, int speed) {

        return timer * (360 / value) + speed * timer;

    }

}
