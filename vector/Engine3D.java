package rainy2D.vector;

import rainy2D.element.Element;
import rainy2D.util.Maths;

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
        e.setSize(Maths.round(e.getY() / percent - startY / percent), Maths.round(e.getY() / percent / 2 - startY / percent / 2));

    }

}
