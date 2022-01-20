package rainy2D.render.desktop;

import rainy2D.element.Element;
import rainy2D.element.ElementBullet;
import rainy2D.element.ElementEnemy;
import rainy2D.render.graphic.Graphic;
import rainy2D.render.graphic.Graphic2D;
import rainy2D.shape.Rectangle;
import rainy2D.util.BulletCacheList;
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
     * 缓存SC变量
     */
    int leftBuffer;
    int topBuffer;

    /**
     * 一些内部变量，如果你不非常了解，请不要乱动！
     * bufferWidth:缓冲图像宽
     * bufferHeight:缓冲图像高
     * totalWidth:实际显示宽度（保持比例）= SC_WIDTH
     * totalHeight：实际显示高度 = SC_HEIGHT
     */
    protected int bufferWidth;
    protected int bufferHeight;
    private double overPercent;
    private int totalWidth;
    private int totalHeight;

    private int fps;
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
    String titleName;

    Rectangle field;

    Image imageBuffer;
    Graphics graphicsBuffer;
    Color overFieldColor;

    public ArrayList<Element> imageBottom = new ArrayList<>();
    public ArrayList<ElementBullet> bullets = new ArrayList<>();
    /**
     * 由于bullets的size非常大，每次计算会耗费性能，所以用这个遍历就可以了
     */
    public int bulletsLengthNow;
    public ArrayList<ElementEnemy> enemies = new ArrayList<>();
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
     * 分辨率必须小于屏幕长宽一些
     */
    public void setDefaultSize(int width, int height) {

        this.bufferWidth = width;
        this.bufferHeight = height;
        this.overPercent =  MathData.toDouble(WI_HEIGHT) / bufferHeight;
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

    public void setTitle(String title) {

        this.titleName = title;

    }

    /**
     * 设置背景颜色（RGB）
     */
    public void setColor(Color c) {

        this.setBackground(c);

    }

    public void setColorOverField(Color c) {

        this.overFieldColor = c;

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
     * 所以严格来说只有一个image组件
     * 需要注意的是，添加组件时是绘制在缓冲图上，所以请不要使用SC系列的变量了。范围为[0, 0] ~ [bufferWidth, bufferHeight]
     * @param g 没什么可说的？
     */
    @Override
    public void paint(Graphics g) {

        //双缓冲
        if(imageBuffer == null) {
            this.callChangeSize();
            this.graphicsBuffer = this.imageBuffer.getGraphics();
            this.graphicsBuffer.setColor(getBackground());
        }
        Graphic2D.renderRect(0, 0, WI_WIDTH, WI_HEIGHT, graphicsBuffer);
        super.paint(graphicsBuffer);

        //初始化
        if (!init) {
            this.init();
        }

        //计时调刻
        this.cycleTime();
        this.tick();

        //遍历调刻和渲染(越后调用，图层处于越高层）
        this.renderBottomImage(graphicsBuffer);//包装完毕方法
        this.renderFrontImage(graphicsBuffer);//包装完毕方法
        this.bulletTick(graphicsBuffer);//包装完毕方法
        this.enemyTick(graphicsBuffer);//包装完毕方法

        this.renderInField(graphicsBuffer);//自定义方法
        this.renderOverField(graphicsBuffer);//包装完毕方法
        this.renderStrings(graphicsBuffer);//自定义方法
        this.renderAboveField(graphicsBuffer);//自定义方法

        //将缓冲图片绘制到screen上
        Graphic.render(SC_LEFT, SC_TOP, SC_WIDTH, SC_HEIGHT, imageBuffer, g);
        //当屏幕比例不对时，在两边绘制黑色方框
        Graphic2D.renderRect2D(0, 0, SC_LEFT, WI_HEIGHT, g);
        Graphic2D.renderRect2D(SC_WIDTH + SC_LEFT, 0, WI_WIDTH, WI_HEIGHT, g);

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
        }
        else if(cycle <= 0 ) {
            this.cycleState = true;
        }

        if(cycleState) {
            this.cycle += 0.01;
        }
        else {
            this.cycle -= 0.01;
        }

    }

    public boolean forTick(int tick) {

        return getTimer() % tick == 0;

    }

    /**
     * 当设置分辨率时调用
     */
    public void callChangeSize() {

        this.imageBuffer = this.createImage(bufferWidth, bufferHeight);

    }

    /**
     * 请复写这个方法，调用ShapeHelper绘制文字。
     * 为什么不将string视为一个element？因为有bug...
     * @param g graphicsBuffer
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
                e.tick(window);
                e.render(g);
            }
        }

        new Remover().start();

    }

    public void enemyTick(Graphics g) {

        ElementEnemy e;

        for (int i = 0; i < enemies.size(); i++) {
            e = enemies.get(i);
            e.tick(window);
            e.render(g);

            if(e.isOutWindow()) {
                enemies.remove(i);
            }
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

    public Image getImageBuffer() {

        return imageBuffer;

    }

    public void pause() {

        this.isPause = !isPause;

    }

    /**
     * 好吧，你还需要更自由的？
     * @return 在缓冲图作画的画笔
     */
    public Graphics getGraphicsBuffer() {

        return graphicsBuffer;

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

        this.SC_LEFT = MathData.round(leftBuffer + MathData.random(-force, force));
        this.SC_TOP = MathData.round(topBuffer + MathData.random(-force, force));

    }

    /**
     * 还原屏幕位置
     */
    public void resetLocation() {

        this.SC_LEFT = this.leftBuffer;
        this.SC_TOP = this.topBuffer;

    }

    public void clear() {

        this.enemies.clear();
        this.bullets.clear();

    }

    @Override
    public void run() {

        int frame = 0;
        long timer = System.currentTimeMillis();
        long fpsTime = MathData.round(1000.0 / fps);
        long updateTime;
        long beforeTime;
        long nowTime;

        try {
            //绘制、FPS调控
            while(true) {

                beforeTime = System.nanoTime();

                //如果没有暂停，则进行游戏主逻辑
                if(!isPause) {
                    this.window.setScreenIn(this);
                    this.repaint();
                }
                frame++;

                nowTime = System.nanoTime();
                updateTime = (nowTime - beforeTime) / 1000000;

                if(updateTime >= fpsTime) {
                    continue;
                }

                Thread.sleep(Math.max(fpsTime - updateTime, 16));

                //每秒得到fps
                if(System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    this.nowFps = frame + updateTime;
                    frame = 0;
                }

            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

    }

    private class Remover extends Thread {

        @Override
        public void run() {

            ElementBullet e;

            for (int i = 0; i < bullets.size(); i++) {
                if(bullets.get(i) != null) {
                    e = bullets.get(i);

                    if(e.isOutWindow()) {
                        bullets.remove(i);
                        bulletCache.reuse(e);
                    }
                }
            }

        }

    }

}
