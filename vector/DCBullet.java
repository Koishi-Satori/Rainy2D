package rainy2D.vector;

import rainy2D.resource.ImageLocation;

public class DCBullet {

    public static final ImageLocation BL_LIGHT = new ImageLocation("img/plp.png");

    /**
     * 不可旋转子弹，不会改变方向
     * @param timer 传入screen的getTimer方法
     * @param value 子弹口数
     */
    public static double function(int timer, int value) {

        return timer * (360 / value);

    }

    /**
     * 条形子弹，一簇有多个小子弹
     * @param timer 传入screen的getTimer方法
     * @param value 子弹口数
     * @param line 子弹簇大小
     */
    public static double functionLine(int timer, int value, int line) {

        return timer * (360 / value) / line;

    }

    /**
     * 可旋转子弹，会改变方向
     * @param timer 传入screen的getTimer方法
     * @param value 子弹口数
     * @param speed 子弹口移动速度
     */
    public static double functionRotate(int timer, int value, int speed) {

        return timer * (360 / value) + speed * timer;

    }

}
