package rainy2D.util;

import rainy2D.render.element.Element;
import rainy2D.render.element.ElementBullet;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BulletCacheList {

    int cacheListSize;

    ArrayList<ElementBullet> elements = new ArrayList<>();

    public BulletCacheList(int size) {

        this.cacheListSize = size;

        for(int i = 0; i < size; i++) {
            this.elements.add(new ElementBullet());
        }

    }

    public void reuse(ElementBullet e) {

        e.setOutWindow(false);
        this.elements.add(e);

    }

    /**
     * 四个取出子弹的方法
     * @param shooter 子弹发射者对象
     * @return 返回一个带有参数的子弹
     */
    public ElementBullet get(Element shooter, int width, int height, double speed, double angle, BufferedImage img) {

        return get(shooter.getX(), shooter.getY(), width, height, speed, angle, img);

    }

    public ElementBullet get(double x, double y, int width, int height, double speed, double angle, BufferedImage img) {

        ElementBullet e;
        int length = this.elements.size();

        for(int i = 0; i < cacheListSize; i++) {
            if(!(length == 0)) {
                e = elements.get(i);
                e.setProperties(x, y, width, height, speed, angle, img);
                this.elements.remove(i);
                return e;
            } else {
                break;
            }
        }

        return new ElementBullet(x, y, width, height, speed, angle, img);

    }

}
