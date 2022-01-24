package rainy2D.util.list;

import rainy2D.element.ElementEnemy;
import rainy2D.util.Array;

import java.awt.image.BufferedImage;

public class EnemyCacheList {

    int cacheListSize;
    int lastTakeIndex;

    Array<ElementEnemy> elements;

    public EnemyCacheList(int size) {

        cacheListSize = size;
        elements = new Array<>(size);

        for(int i = 0; i < size; i++) {
            elements.add(new ElementEnemy(0, 0, 0, 0, 0, null));
        }

    }

    public void reuse(ElementEnemy e) {

        elements.add(e);

    }

    public ElementEnemy getClone(ElementEnemy e) {

        return get(e.getX(), e.getY(), e.getWidth(), e.getHeight(), e.getSpeed(), e.getAngle(), e.getHealth(), e.getImage());

    }

    /**
     * 原理：直接返回下一位，当达到size极限时，第一个应已经补位完毕，所以可以一直循环使用
     * @return 设置好的子弹对象
     */
    public ElementEnemy get(double x, double y, int width, int height, double speed, double angle, int startHealth, BufferedImage img) {

        ElementEnemy e = elements.get(lastTakeIndex);

        e.locate(x, y);
        e.setSize(width, height);
        e.setAngle(angle);
        e.setSpeed(speed);
        e.setStartHealth(startHealth);
        e.setImage(img);
        e.setTimer(0);

        lastTakeIndex++;
        if(lastTakeIndex == cacheListSize) {
            lastTakeIndex = 0;
        }

        return e;

    }

}
