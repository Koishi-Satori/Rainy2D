package rainy2D.vector;

public class R2DBullet {

    /**
     * 不可旋转子弹，不会改变方向
     * @param timer 传入screen的getTimer方法
     * @param value 子弹口数
     */
    public static double function(int timer, double value) {

        return timer * (360 / value);

    }

    /**
     * 条形子弹，一簇有多个小子弹
     * @param timer 传入screen的getTimer方法
     * @param value 子弹口数
     * @param line 子弹簇大小
     */
    public static double functionLine(int timer, double value, double line) {

        return timer * (360 / value) / line;

    }

    /**
     * 可旋转子弹，会改变方向
     * @param timer 传入screen的getTimer方法
     * @param value 子弹口数
     * @param speed 子弹口移动速度
     */
    public static double functionRotate(int timer, double value, double speed) {

        return timer * (360 / value) + speed * timer;

    }

    /**
     * 二次函数，一般呈现出条和簇的变化态
     * timer * timer * 0.0000078 + timer * 0.62（典例）
     * @param timer 传入screen的getTimer方法
     * @param value1 未知
     * @param value2 未知
     */
    public static double functionCycle(int timer, double value1, double value2) {

        return timer * timer * value1 + timer * value2;

    }

}
