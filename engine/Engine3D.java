package rainy2D.engine;

import rainy2D.render.element.Element;
import rainy2D.util.MathData;

public class Engine3D {

    /**
     * 生成伪3D运动轨迹
     * @param e 传入一个需要移动的对象
     * @param speed 移动速度
     * @param percent 视觉效果比例
     * @param startY 地平线坐标
     */
    public static void locate3D(Element e, double speed, double percent, int startY) {

        e.locate(e.getX(), e.getY() + speed / 100 * e.getY());
        e.setSize(MathData.round(e.getY() / percent - startY / percent), MathData.round(e.getY() / percent / 1.5 - startY / percent / 1.5));

    }

}
