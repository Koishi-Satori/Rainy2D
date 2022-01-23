package rainy2D.util.list;

import rainy2D.element.ElementBullet;
import rainy2D.util.Array;

import java.awt.image.BufferedImage;

public class BulletCacheList {

    int cacheListSize;
    int lastTakeIndex;

    Array<ElementBullet> elements;

    public BulletCacheList(int size) {

        cacheListSize = size;
        elements = new Array<>(size);

        for(int i = 0; i < size; i++) {
            elements.add(new ElementBullet());
        }

    }

    public void reuse(ElementBullet e) {

        e.setOutWindow(false);
        elements.add(e);

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

        lastTakeIndex++;
        if(lastTakeIndex == cacheListSize) {
            lastTakeIndex = 0;
        }

        return e;

    }

}
