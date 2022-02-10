package rainy2D.util.list;

import rainy2D.element.vector.ElementBullet;
import rainy2D.util.Array;

import java.awt.image.BufferedImage;

public class BulletCacheList {

    public int cacheListSize;
    public int lastTakeIndex;

    Array<ElementBullet> elements;

    public BulletCacheList(int size) {

        cacheListSize = size;
        elements = new Array<>(size);

        for(int i = 0; i < size; i++) {
            elements.add(new ElementBullet(0, 0, 0, 0, 0, null));
        }

    }

    public void reuse(ElementBullet e) {

        elements.add(e);

    }

    public ElementBullet getClone(ElementBullet b) {

        return get(b.getX(), b.getY(), b.getWidth(), b.getHeight(), b.getSpeed(), b.getAngle(), b.getForce(), b.getImage());

    }

    /**
     * 原理：直接返回下一位，当达到size极限时，第一个应已经补位完毕，所以可以一直循环使用
     * @return 设置好的子弹对象
     */
    public ElementBullet get(double x, double y, int width, int height, double speed, double angle, int force, BufferedImage img) {

        ElementBullet e = elements.get(lastTakeIndex);

        e.locate(x, y);
        e.setSize(width, height);
        e.setAngle(angle);
        e.setSpeed(speed);
        e.setForce(force);
        e.setImage(img);
        e.setTimer(0);

        lastTakeIndex++;
        if(lastTakeIndex >= cacheListSize) {
            lastTakeIndex = 0;
        }

        return e;

    }

}
