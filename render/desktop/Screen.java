package rainy2D.render.desktop;

import rainy2D.render.helper.RenderHelper;
import rainy2D.render.helper.ShapeHelper;
import rainy2D.util.BulletCacheList;
import rainy2D.render.element.Element;
import rainy2D.render.element.ElementBullet;
import rainy2D.shape.Rectangle;
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

    int leftBuffer;
    int topBuffer;

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
    /**
     * time根据fps递增
     * cycle在[0-1]以0.01的速度循环
     */
    int time;
    double cycle;
    boolean cycleState;

    /**
     * init:第一次注册组件限制，防止多次注册
     */
    boolean init;

    Window window;
    Rectangle field;
    Image iBuffer;
    Graphics gBuffer;
    Color gBufferColor;

    public ArrayList<Element> imageBottom = new ArrayList<>();
    public ArrayList<ElementBullet> bullets = new ArrayList<>();
    public ArrayList<Element> imageFront = new ArrayList<>();

    public BulletCacheList bulletCache;

    boolean isPause;

    public Screen(Window window) {

        this.window = window;
        this.WI_WIDTH = window.getWidth();
        this.WI_HEIGHT = window.getHeight();

        this.field = new Rectangle(0, 0, WI_WIDTH, WI_HEIGHT);
        this.setDefaultSize(900, 600);
        this.setDefaultFps(60);

        new Thread(this).start();

    }

    /**
     * 设置分辨率（默认900x600）
     * 一旦设置就不要更改了哦
     * 分辨率必须与window的大小相同，否则会出现奇奇怪怪的bug
     * 分辨率必须小于屏幕长宽
     */
    public void setDefaultSize(int width, int height) {

        this.wBuffer = width;
        this.hBuffer = height;
        this.overPercent =  MathData.toDouble(WI_HEIGHT) / hBuffer;
        this.totalHeight = MathData.round(height * overPercent);
        this.totalWidth = MathData.round(width * overPercent);

        this.SC_WIDTH = totalWidth;
        this.SC_HEIGHT = totalHeight;
        this.SC_LEFT = (WI_WIDTH - SC_WIDTH) / 2;
        this.SC_TOP = (WI_HEIGHT - SC_HEIGHT) / 2;

        this.leftBuffer = this.SC_LEFT;
        this.topBuffer = this.SC_TOP;

        this.callChangeSize();

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
     */
    public void setColor(Color c) {

        this.setBackground(c);

    }

    public void setColorOverField(Color c) {

        this.gBufferColor = c;

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
    public void setRepaintField(Rectangle field) {

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
            this.callChangeSize();
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
        this.cycleTime();
        this.tick();

        //遍历调刻和渲染(越后调用，图层处于越高层）
        this.renderBottomImage(gBuffer);//包装完毕方法
        this.renderFrontImage(gBuffer);//包装完毕方法
        this.bulletTick(gBuffer);//包装完毕方法

        this.renderInField(gBuffer);//自定义方法
        this.renderOverField(gBuffer);//自定义方法
        this.renderStrings(gBuffer);//自定义方法
        this.renderAboveField(gBuffer);//自定义方法

        //将缓冲图片绘制到screen上
        RenderHelper.render(SC_LEFT, SC_TOP, SC_WIDTH, SC_HEIGHT, iBuffer, g);
        //当屏幕比例不对时，在两边绘制黑色方框
        ShapeHelper.renderRect2D(0, 0, SC_LEFT, WI_HEIGHT, g);
        ShapeHelper.renderRect2D(SC_WIDTH + SC_LEFT, 0, WI_WIDTH, WI_HEIGHT, g);

    }

    /**
     * 时间和循环的变化
     */
    public void cycleTime() {

        this.time ++;
        if(time == Integer.MAX_VALUE) {
            this.time = 0;
        }
        if(cycle >= 1) {
            this.cycleState = false;
        } else if(cycle <= 0 ) {
            this.cycleState = true;
        }
        if(cycleState) {
            this.cycle += 0.01;
        } else {
            this.cycle -= 0.01;
        }

    }

    /**
     * 当设置分辨率时调用
     */
    public void callChangeSize() {

        this.iBuffer = this.createImage(wBuffer, hBuffer);

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

    /**
     * 填充除field以外的部分
     * @param g 画笔
     */
    public void renderOverField(Graphics g) {

        int left = this.field.getX();
        int top = this.field.getY();
        int right = this.field.getX2();
        int bottom = this.field.getY2();

        g.setColor(gBufferColor);

        ShapeHelper.renderRect(0, 0, left, WI_HEIGHT, g);
        ShapeHelper.renderRect(0, 0, WI_WIDTH, top, g);
        ShapeHelper.renderRect(right, top, WI_WIDTH - right, WI_HEIGHT - top, g);
        ShapeHelper.renderRect(left, bottom, WI_WIDTH - left, WI_HEIGHT - bottom, g);

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
     * @return 获得一个循环数值
     */
    public double getCycle() {

        return cycle;

    }

    /**
     * @return 玩家可移动的范围，也就是游戏内容范围
     */
    public Rectangle getField() {

        return field;

    }

    public double getPercent() {

        return overPercent;

    }

    public void pause() {

        this.isPause = !isPause;

    }

    /**
     * 好吧，你还需要更自由的？
     * @return 在缓冲图作画的画笔
     */
    public Graphics getGraphicsBuffer() {

        return gBuffer;

    }

    public Point randomPoint() {

        int x = MathData.round(MathData.random(field.getX(), field.getX2()));
        int y = MathData.round(MathData.random(field.getY(), field.getY2()));
        return new Point(x, y);

    }

    /**
     * 屏幕摇晃
     * @param force 力度
     */
    public void earthQuake(int force) {

        this.SC_LEFT = MathData.round(leftBuffer + MathData.random(- force, force));
        this.SC_TOP = MathData.round(topBuffer + MathData.random(- force, force));

    }

    /**
     * 还原屏幕位置
     */
    public void resetLocation() {

        this.SC_LEFT = this.leftBuffer;
        this.SC_TOP = this.topBuffer;

    }

    public void clear() {

        this.time = 0;
        this.bullets.clear();

    }

    @Override
    public void run() {

        try {
            //绘制、FPS调控
            while(true) {

                long fpsTime = MathData.toLong(1000.0 / fps * 1000000.0);
                long beforeNano = System.nanoTime();

                if(!isPause) {
                    this.repaint();
                    this.window.setScreenIn(this);
                }

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

    private class BulletRemover extends Thread {

        @Override
        public void run() {

            ElementBullet e;
            int length = bullets.size();

            for (int i = 0; i < length; i++) {
                e = bullets.get(i);

                if(e == null) {
                    continue;
                }

                if(e.isOutWindow()) {
                    bullets.remove(e);
                    bulletCache.reuse(e);
                    length--;
                }
            }

        }

    }

}
