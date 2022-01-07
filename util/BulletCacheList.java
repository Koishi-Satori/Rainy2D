package rainy2D.util;

import rainy2D.render.element.Element;
import rainy2D.render.element.ElementBullet;
import rainy2D.resource.ImageLocation;

import java.util.ArrayList;

public class BulletCacheList {

    ArrayList<ElementBullet> elements = new ArrayList<>();

    public BulletCacheList(int size) {

        for(int i = 0; i < size; i++) {
            this.elements.add(new ElementBullet());
        }

    }

    public void reuse(ElementBullet e) {

        this.elements.add(e);

    }

    /**
     * 四个取出子弹的方法
     * @param shooter 子弹发射者对象
     * @param speed 子弹速度
     * @param angle 子弹角度（从x轴正方向逆时针360度）
     * @param iml 路径
     * @return 返回一个带有参数的子弹
     */
    public ElementBullet get(Element shooter, int width, int height, double speed, double angle, ImageLocation iml) {

        return get(shooter.getX(), shooter.getY(), width, height, speed, angle, iml);

    }

    public ElementBullet get(double x, double y, int width, int height, double speed, double angle, ImageLocation iml) {

        ElementBullet e;

        for(int i = 0; i < elements.size(); i++) {
            e = elements.get(i);
            if(e != null) {
                e.setOutWindow(false);
                e.takeOutWith(x, y, width, height, speed, angle, iml);
                e.callImageChange();
                this.elements.remove(i);
                return e;
            }
        }

        return null;

    }

}
