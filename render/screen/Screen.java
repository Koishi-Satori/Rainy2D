package rainy2D.render.screen;

import rainy2D.pool.BulletPool;
import rainy2D.pool.EffectPool;
import rainy2D.render.RenderHelper;
import rainy2D.render.ShapeHelper;
import rainy2D.render.element.Element;
import rainy2D.render.element.ElementBullet;
import rainy2D.render.element.ElementEffect;
import rainy2D.render.window.Window;
import rainy2D.shape.Rect;
import rainy2D.util.MathData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Screen extends JPanel implements Runnable {

    public int SC_LEFT;
    public int SC_TOP;
    public int SC_WIDTH;
    public int SC_HEIGHT;
    public int WI_WIDTH;
    public int WI_HEIGHT;

    /**
     * 一些内部变量，如果你不非常了解，请不要乱动！
     * wBuffer:缓冲图像宽
     * hBuffer:缓冲图像高
     * totalWidth:实际显示宽度（保持比例）
     * totalHeight：实际显示高度
     */
    private int wBuffer;
    private int hBuffer;
    private double overPercent;
    private int totalWidth;
    private int totalHeight;

    int fps;
    public double nowFps;
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
    public ArrayList<ElementEffect> effects = new ArrayList<>();

    public BulletPool bulletPool;

    public Screen(Window window) {

        this.window = window;
        this.WI_WIDTH = window.getWidth();
        this.WI_HEIGHT = window.getHeight();

        this.field = new Rect(0, 0, WI_WIDTH, WI_HEIGHT);
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
        this.overPercent =  MathData.toDouble(WI_HEIGHT) / hBuffer;
        this.totalHeight = MathData.toInt(height * overPercent);
        this.totalWidth = MathData.toInt(width * overPercent);

        this.SC_WIDTH = totalWidth;
        this.SC_HEIGHT = totalHeight;
        this.SC_LEFT = (WI_WIDTH - SC_WIDTH) / 2;
        this.SC_TOP = (WI_HEIGHT - SC_HEIGHT) / 2;

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
     * 继承并复写，逻辑都在这里进行。
     */
    public void tick() {
    }

    /**
     * 添加元素进list，就会被渲染
     * @param e 所有render.element包中的类
     * @param list 参考类的几个数组，imageBottom为背景，bullets为子弹，imageFront为UI
     */
    public void add(Element e, ArrayList list) {

        list.add(e);

    }

    /**
     * 默认生成的是一个900x600的图像
     * 放置组件都要在这个范围内进行，然后按比例绘制到屏幕上。
     * 原理：
     * 先在底层的window上设置相同大小的screen，然后创建缓冲图片，在图片上绘制画面，并按比例缩放到window上。
     * 所以严格来说只有一个image组件的样子（笑）
     * 需要注意的是，添加组件时是绘制在缓冲图上，所以请不要使用SC系列的变量了。范围为[0, 0] ~ [wBuffer, hBuffer]
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
        ShapeHelper.renderRect(0, 0, WI_WIDTH, WI_HEIGHT, gBuffer);
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
        this.tick();

        //遍历调刻和渲染(越后调用，图层处于越高层）
        this.renderBottomImage(gBuffer);//包装完毕方法
        this.renderFrontImage(gBuffer);//包装完毕方法
        this.bulletTick(gBuffer);//包装完毕方法

        this.renderInField(gBuffer);//自定义方法
        this.renderOverField(gBuffer);//自定义方法
        this.renderStrings(gBuffer);//自定义方法
        this.renderAboveField(gBuffer);//自定义方法

        RenderHelper.render(SC_LEFT, SC_TOP, SC_WIDTH, SC_HEIGHT, iBuffer, g);
        ShapeHelper.renderRect2D(0, 0, SC_LEFT, WI_HEIGHT, g);
        ShapeHelper.renderRect2D(SC_WIDTH + SC_LEFT, 0, WI_WIDTH, WI_HEIGHT, g);

    }

    /**
     * 请复写这个方法，调用ShapeHelper绘制文字。
     * 为什么不将string视为一个element？因为有bug...
     * @param g gBuffer
     * @layer top
     */
    public void renderStrings(Graphics g) {
    }

    /**
     * 在field范围内高自由度地绘制
     */
    public void renderInField(Graphics g) {
    }

    /**
     * 在最顶层自由绘制
     */
    public void renderAboveField(Graphics g) {
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

                if(time % 5 == 0) {
                    this.nowFps = fps - totalNano / 1000000.0;
                }

            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void renderOverField(Graphics g) {

        int left = this.field.getX();
        int top = this.field.getY();
        int right = this.field.getX2();
        int bottom = this.field.getY2();

        g.fillRect(0, 0, left, WI_HEIGHT);
        g.fillRect(0, 0, WI_WIDTH, top);
        g.fillRect(right, top, WI_WIDTH - right, WI_HEIGHT - top);
        g.fillRect(left, bottom, WI_WIDTH - left, WI_HEIGHT - bottom);

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

    /**
     * @return 获得一个计时器
     */
    public int getTimer() {

        return time;

    }

    /**
     * @return 玩家可移动的范围，也就是游戏内容范围
     */
    public Rect getField() {

        return field;

    }

    /**
     * 好吧，你还需要更自由的？
     * @return 在缓冲图作画的画笔
     */
    public Graphics getGraphicsBuffer() {

        return gBuffer;

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
                    bulletPool.reuse(e);
                    length--;
                }
            }

            window.setTitle("RL-01 ~ White building in Youkaity [ BL: " + iterator + " ]");

        }

    }

}
