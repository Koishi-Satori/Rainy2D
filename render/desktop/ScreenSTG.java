package rainy2D.render.desktop;

import rainy2D.element.ElementBullet;
import rainy2D.element.ElementEnemy;
import rainy2D.render.graphic.Graphic2D;
import rainy2D.util.Array;
import rainy2D.util.list.BulletCacheList;
import rainy2D.util.list.EnemyCacheList;

import java.awt.*;

public class ScreenSTG extends Screen {

    public Array<ElementBullet> bullets = new Array<>();
    public Array<ElementEnemy> enemies = new Array<>();

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
        tick();
        renderFrontImage(graphicsBuffer);

        bulletTick(graphicsBuffer);
        enemyTick(graphicsBuffer);

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

        g.setColor(new Color(125, 125, 125));
        Graphic2D.renderFrame(left - 5, top - 5, right + 5, bottom + 5, 5, g);

    }

    public void bulletTick(Graphics g) {

        ElementBullet e;

        for (int i = 0; i < bullets.size(); i++) {
            if(bullets.get(i) != null) {
                e = bullets.get(i);
                e.update(window, g);
            }
        }

    }

    public void enemyTick(Graphics g) {

        ElementEnemy e;

        for (int i = 0; i < enemies.size(); i++) {
            if(enemies.get(i) != null) {
                e = enemies.get(i);
                e.update(window, g);
            }
        }

        new Remover().start();

    }

    private class Remover extends Thread {

        @Override
        public void run() {

            ElementBullet b;
            ElementEnemy e;

            for (int i = 0; i < bullets.size(); i++) {
                if(bullets.get(i) != null) {
                    b = bullets.get(i);

                    if(b.checkOutWindow(window)) {
                        bullets.remove(i);
                        bulletCache.reuse(b);
                    }
                }
            }

            for (int i = 0; i < enemies.size(); i++) {
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
