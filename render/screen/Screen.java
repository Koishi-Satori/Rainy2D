package rainy2D.render.screen;

import rainy2D.pool.BulletPool;
import rainy2D.render.RenderHelper;
import rainy2D.render.element.Element;
import rainy2D.render.element.ElementBullet;
import rainy2D.render.window.Window;
import rainy2D.shape.Rect;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Screen extends JPanel implements Runnable {

    public static int SC_WIDTH;
    public static int SC_HEIGHT;

    int centeredX;
    int centeredY;

    int fps;
    int time;

    boolean init;

    Window window;

    public ArrayList<Element> elementsSlow = new ArrayList<>();
    public ArrayList<ElementBullet> bullets = new ArrayList<>();
    public ArrayList<Element> elementsQuick = new ArrayList<>();

    public BulletPool pool;

    public Screen(Window window, int fps) {

        this.fps = fps;
        this.window = window;

        this.SC_WIDTH = this.window.getWidth();
        this.SC_HEIGHT = this.window.getHeight();
        this.centeredX = window.getWidth() / 2;
        this.centeredY = window.getHeight() / 2;

        Thread paint = new Thread(this);
        paint.start();

    }

    public void init() {

        this.init = true;

    }

    public void tick() {
    }

    public void add(Element e, ArrayList list) {

        list.add(e);

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        //初始化
        if (!init) {
            this.init();
        }

        //计时调刻
        this.time ++;
        if(time > Integer.MAX_VALUE) {
            this.time = 0;
        }
        this.tick();

        //遍历调刻和渲染
        this.defaultTick(g);
        this.quickTick(g);
        this.bulletTick(g);

    }

    @Override
    public void run() {

        try {
            //绘制、FPS调控
            while(true) {

                long fpsTime = (long) (1000.0 / fps * 1000000.0);
                long beforeNano = System.nanoTime();

                this.repaint();

                long totalNano = System.nanoTime() - beforeNano;

                if(totalNano > fpsTime) {
                    continue;
                }

                Thread.sleep((fpsTime - (System.nanoTime() - beforeNano)) / 1000000);

                while((System.nanoTime()) - beforeNano < fpsTime) {

                    System.nanoTime();

                }

            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void bulletTick(Graphics g) {

        ElementBullet e;
        new BulletRemover().start();

        for (int i = 0; i < bullets.size(); i++) {
            e = bullets.get(i);
            e.tick(window);
            e.render(g);
        }

    }

    public void defaultTick(Graphics g) {

        Element e;

        for(int i = 0; i < elementsSlow.size(); i++) {
            e = elementsSlow.get(i);
            e.render(g);
        }

    }

    public void quickTick(Graphics g) {

        Element e;

        for(int i = 0; i < elementsQuick.size(); i++) {
            e = elementsQuick.get(i);
            e.tick(window);
            e.render(g);
        }

    }

    public int getTimer() {

        return time;

    }

    public void clear() {

        this.time = 0;
        this.bullets.clear();

    }

    private class BulletRemover extends Thread {

        @Override
        public void run() {

            ElementBullet e;
            int length = bullets.size();

            for (int i = 0; i < length; i++) {
                e = bullets.get(i);

                if(e.isOutWindow()) {
                    bullets.remove(e);
                    pool.reuse(e);
                    length--;
                }
            }

        }

    }

}
