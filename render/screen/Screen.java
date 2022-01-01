package rainy2D.render.screen;

import rainy2D.pool.BulletPool;
import rainy2D.render.RenderHelper;
import rainy2D.render.ShapeHelper;
import rainy2D.render.element.Element;
import rainy2D.render.element.ElementBullet;
import rainy2D.render.window.Window;
import rainy2D.shape.Rect;
import rainy2D.util.MathData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Screen extends JPanel implements Runnable {

    public int SC_WIDTH;
    public int SC_HEIGHT;

    int wBuffer;
    int hBuffer;
    double overPercent;
    int totalWidth;
    int totalHeight;

    int centeredX;
    int centeredY;

    int fps;
    int time;

    /**
     * init:第一次注册组件限制，防止多次注册
     */
    boolean init;

    Window window;
    Rect field;
    Image iBuffer;
    Graphics gBuffer;

    public ArrayList<Element> imageBottom = new ArrayList<>();
    public ArrayList<ElementBullet> bullets = new ArrayList<>();
    public ArrayList<Element> imageFront = new ArrayList<>();

    public BulletPool pool;

    public Screen(Window window) {

        this.window = window;

        this.SC_WIDTH = this.window.getWidth();
        this.SC_HEIGHT = this.window.getHeight();
        this.centeredX = window.getWidth() / 2;
        this.centeredY = window.getHeight() / 2;
        this.field = new Rect(0, 0, SC_WIDTH, SC_HEIGHT);
        this.setDefaultSize(900, 600);
        this.setDefaultFps(60);

        new Thread(this).start();

    }

    /**
     * 设置分辨率（默认900x600）
     * 一旦设置就不要更改了哦
     */
    public void setDefaultSize(int width, int height) {

        this.wBuffer = width;
        this.hBuffer = height;
        this.overPercent =  MathData.toDouble(SC_HEIGHT) / hBuffer;
        this.totalHeight = MathData.toInt(height * overPercent);
        this.totalWidth = MathData.toInt(width * overPercent);

    }

    /**
     * 设置刷新率（默认60）
     * @param fps 越大刷新越快，时间加速、缓慢也许可以用到
     */
    public void setDefaultFps(int fps) {

        this.fps = fps;

    }

    /**
     * 设置背景颜色（RGB）
     * @param r 红色
     * @param g 绿色
     * @param b 蓝色
     */
    public void setColor(int r, int g, int b) {

        this.setBackground(new Color(r, g, b));

    }

    /**
     * 写法：继承本类，然后复写init（别忘了调用super.init()），在方法内调用add方法，写法如下：
     * this.add(pool.get(out, 25, 25, 0.4, getTimer(), new ImageLocation("img/plp.png")), bullets);
     */
    public void init() {

        this.init = true;

    }

    /**
     * 子类的构造器中调用，传入刷新的区域（游戏屏幕区）。不调用默认全屏刷新
     * @param field 一个矩形，在shape包中。
     */
    public void setRepaintField(Rect field) {

        this.field = field;

    }

    /**
     * 继承并复写，相当于swing默认的paint方法
     * @param g 即paint的画笔
     */
    public void tick(Graphics g) {
    }

    /**
     * 添加元素进list，就会被渲染
     * @param e 所有render.element包中的类
     * @param list 参考类的三个数组，imageBottom为背景，bullets为子弹，imageFront为UI
     */
    public void add(Element e, ArrayList list) {

        list.add(e);

    }

    /**
     * 默认生成的是一个900x600的图像
     * 放置组件都要在这个范围内进行，然后按比例绘制到屏幕上。
     * @param g 没什么可说的？
     */
    @Override
    public void paint(Graphics g) {

        //双缓冲
        if(iBuffer == null) {
            this.iBuffer = this.createImage(wBuffer, hBuffer);
            this.gBuffer = this.iBuffer.getGraphics();
            this.gBuffer.setColor(getBackground());
        }
        ShapeHelper.renderRect(0, 0, SC_WIDTH, SC_HEIGHT, gBuffer);
        super.paint(gBuffer);

        //初始化
        if (!init) {
            this.init();
        }

        //计时调刻
        this.time ++;
        if(time == Integer.MAX_VALUE) {
            this.time = 0;
        }
        this.tick(gBuffer);

        //遍历调刻和渲染
        this.renderBottomImage(gBuffer);
        this.renderFrontImage(gBuffer);
        this.bulletTick(gBuffer);
        this.drawOverField(gBuffer);

        int outScreenOffset = (SC_WIDTH - totalWidth) / 2;
        RenderHelper.render(outScreenOffset, 0, totalWidth, totalHeight, iBuffer, g);
        ShapeHelper.renderRect2D(0, 0, outScreenOffset, SC_HEIGHT, g);
        ShapeHelper.renderRect2D(totalWidth + outScreenOffset, 0, SC_WIDTH, SC_HEIGHT, g);

    }

    @Override
    public void update(Graphics g) {

        this.paint(g);

    }

    @Override
    public void run() {

        try {
            //绘制、FPS调控
            while(true) {

                long fpsTime = MathData.toLong(1000.0 / fps * 1000000.0);
                long beforeNano = System.nanoTime();

                this.repaint();
                this.window.setScreenIn(this);

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

    public void drawOverField(Graphics g) {

        int left = this.field.getX();
        int top = this.field.getY();
        int right = this.field.getX2();
        int bottom = this.field.getY2();

        g.fillRect(0, 0, left, SC_HEIGHT);
        g.fillRect(0, 0, SC_WIDTH, top);
        g.fillRect(right, top, SC_WIDTH - right, SC_HEIGHT - top);
        g.fillRect(left, bottom, SC_WIDTH - left, SC_HEIGHT - bottom);

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

    public void renderBottomImage(Graphics g) {

        Element e;

        for(int i = 0; i < imageBottom.size(); i++) {
            e = imageBottom.get(i);
            e.tick(window);
            e.render(g);
        }

    }

    public void renderFrontImage(Graphics g) {

        Element e;

        for(int i = 0; i < imageFront.size(); i++) {
            e = imageFront.get(i);
            e.tick(window);
            e.render(g);
        }

    }

    public int getTimer() {

        return time;

    }

    public Rect getField() {

        return field;

    }

    public Point randomPoint() {

        int x = MathData.toInt(MathData.random(field.getX(), field.getX2()));
        int y = MathData.toInt(MathData.random(field.getY(), field.getY2()));
        return new Point(x, y);

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
            int iterator = 0;

            for (int i = 0; i < length; i++) {
                e = bullets.get(i);
                iterator++;

                if(e.isOutWindow()) {
                    bullets.remove(e);
                    pool.reuse(e);
                    length--;
                }
            }

            window.setTitle("RL-01 ~ White building in Youkaity [ BL: " + iterator + " ]");

        }

    }

}
