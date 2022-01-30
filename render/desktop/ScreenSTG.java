package rainy2D.render.desktop;

import rainy2D.element.vector.ElementBoss;
import rainy2D.element.vector.ElementBullet;
import rainy2D.element.vector.ElementEnemy;
import rainy2D.render.graphic.Graphic2D;
import rainy2D.util.Array;
import rainy2D.util.list.BulletCacheList;
import rainy2D.util.list.EnemyCacheList;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScreenSTG extends Screen {

    ExecutorService service = Executors.newCachedThreadPool();

    public Array<ElementBullet> bullets = new Array<>(4000);
    public Array<ElementEnemy> enemies = new Array<>(200);
    public Array<ElementBoss> bosses = new Array<>(5);

    public BulletCacheList bulletCache;
    public EnemyCacheList enemyCache;

    public ScreenSTG(Window window) {

        super(window);

    }

    @Override
    public void paint(Graphics g) {

        bufferTick();

        renderBottomImage(graphicsBuffer);
        renderMiddleImage(graphicsBuffer);
        renderFrontImage(graphicsBuffer);
        tick();
        bulletTick(graphicsBuffer);
        enemyTick(graphicsBuffer);
        bossTick(graphicsBuffer);

        renderOverField(graphicsBuffer);

        bufferPaint(g);

    }

    /**
     * 填充除field以外的部分
     * @param g 画笔
     */
    public void renderOverField(Graphics g) {

        int left = field.getOffsetX();
        int top = field.getOffsetY();
        int right = field.getX2();
        int bottom = field.getY2();

        g.setColor(overFieldColor);

        Graphic2D.renderRect(0, 0, left, WI_HEIGHT, g);
        Graphic2D.renderRect(0, 0, WI_WIDTH, top, g);
        Graphic2D.renderRect(right, top, WI_WIDTH - right, WI_HEIGHT - top, g);
        Graphic2D.renderRect(left, bottom, WI_WIDTH - left, WI_HEIGHT - bottom, g);

    }

    public void bulletTick(Graphics g) {

        ElementBullet e;
        int size = bullets.size();

        for (int i = 0; i < size; i++) {
            if(bullets.get(i) != null) {
                e = bullets.get(i);
                e.update(window, g);
            }
        }

    }

    public void enemyTick(Graphics g) {

        ElementEnemy e;
        int size = enemies.size();

        for (int i = 0; i < size; i++) {
            if(enemies.get(i) != null) {
                e = enemies.get(i);
                e.update(window, g);
            }
        }

        service.submit(new Remover());

    }

    public void bossTick(Graphics g) {

        ElementBoss e;
        int size = bosses.size();

        for (int i = 0; i < size; i++) {
            if(bosses.get(i) != null) {
                e = bosses.get(i);
                e.update(window, g);
            }
        }

    }

    private class Remover extends Thread {

        @Override
        public void run() {

            ElementBullet b;
            ElementEnemy e;
            int size = bullets.size();

            for (int i = 0; i < size; i++) {
                if(bullets.get(i) != null) {
                    b = bullets.get(i);

                    if(b.checkOutWindow(window)) {
                        bullets.remove(i);
                        bulletCache.reuse(b);
                    }
                }
            }

            size = enemies.size();

            for (int i = 0; i < size; i++) {
                if(enemies.get(i) != null) {
                    e = enemies.get(i);

                    if(e.checkOutWindow(window)) {
                        enemies.remove(i);
                        enemyCache.reuse(e);
                    }
                }
            }

        }

    }

}
