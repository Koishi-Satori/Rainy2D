package rainy2D.util;

import rainy2D.element.Element;
import rainy2D.element.ElementBullet;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BulletCacheList {

    int cacheListSize;
    int lastTakeIndex;

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

    public ElementBullet getClone(ElementBullet b) {

        return get(b.getX(), b.getY(), b.getWidth(), b.getHeight(), b.getSpeed(), b.getAngle(), b.getImage());

    }

    /**
     * 原理：直接返回下一位，当达到size极限时，第一个应已经补位完毕，所以可以一直循环使用
     * @return 设置好的子弹对象
     */
    public ElementBullet get(double x, double y, int width, int height, double speed, double angle, BufferedImage img) {

        ElementBullet e = elements.get(lastTakeIndex);

        e.locate(x, y);
        e.setSize(width, height);
        e.setAngle(angle);
        e.setSpeed(speed);
        e.setImage(img);
        e.resetEffect();

        this.lastTakeIndex++;
        if(lastTakeIndex == cacheListSize) {
            this.lastTakeIndex = 0;
        }

        return e;

    }

}
