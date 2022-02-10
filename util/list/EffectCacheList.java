package rainy2D.util.list;

import rainy2D.element.vector.ElementVector;
import rainy2D.util.Array;

import java.awt.image.BufferedImage;

public class EffectCacheList {

    public int cacheListSize;
    public int lastTakeIndex;

    Array<ElementVector> elements;

    public EffectCacheList(int size) {

        cacheListSize = size;
        elements = new Array<>(size);

        for(int i = 0; i < size; i++) {
            elements.add(new ElementVector(0, 0, 0, 0, null));
        }

    }

    public void reuse(ElementVector e) {

        elements.add(e);

    }

    public ElementVector getClone(ElementVector e) {

        return get(e.getX(), e.getY(), e.getWidth(), e.getHeight(), e.getSpeed(), e.getAngle(), e.getImage());

    }

    /**
     * 原理：直接返回下一位，当达到size极限时，第一个应已经补位完毕，所以可以一直循环使用
     * @return 设置好的子弹对象
     */
    public ElementVector get(double x, double y, int width, int height, double speed, double angle, BufferedImage img) {

        ElementVector e = elements.get(lastTakeIndex);

        e.locate(x, y);
        e.setSize(width, height);
        e.setAngle(angle);
        e.setSpeed(speed);
        e.setImage(img);
        e.setTimer(0);

        lastTakeIndex++;
        if(lastTakeIndex >= cacheListSize) {
            lastTakeIndex = 0;
        }

        return e;

    }

}
