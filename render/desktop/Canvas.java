package rainy2D.render.desktop;

import rainy2D.element.Element;
import rainy2D.element.vector.ElementBoss;
import rainy2D.element.vector.ElementBullet;
import rainy2D.element.vector.ElementEnemy;
import rainy2D.render.graphic.Graphic;
import rainy2D.render.graphic.Graphic2D;
import rainy2D.shape.Rectangle;
import rainy2D.util.Array;
import rainy2D.util.MathData;
import rainy2D.util.list.BulletCacheList;
import rainy2D.util.list.EnemyCacheList;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 初始化之后load进screen
 */
public class Canvas {

    public int SC_LEFT;
    public int SC_TOP;
    public int SC_WIDTH;
    public int SC_HEIGHT;
    public int WI_WIDTH;
    public int WI_HEIGHT;

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

    /**
     * 缓存SC变量
     */
    int leftBuffer;
    int topBuffer;

    Image imageBuffer;
    Graphics graphicsBuffer;
    Color overFieldColor;

    public Rectangle field;
    public Window window;
    public Screen screen;

    /**
     * time根据fps递增
     * cycle在[0-1]以0.01的速度循环
     */
    int timer;
    double cycle;
    boolean cycleState;

    boolean init;

    public Array<Element> imageBottom = new Array<>(50);
    public Array<Element> imageMiddle = new Array<>(50);
    public Array<Element> imageFront = new Array<>(50);

    public Array<ElementBullet> bullets = new Array<>(4000);
    public Array<ElementEnemy> enemies = new Array<>(200);
    public Array<ElementBoss> bosses = new Array<>(5);

    public BulletCacheList bulletCache;
    public EnemyCacheList enemyCache;

    ExecutorService service = Executors.newCachedThreadPool();

    public Canvas(Window window) {

        this.window = window;//获取所处的窗口和屏幕
        screen = window.getScreenIn();

        WI_WIDTH = window.getWidth();
        WI_HEIGHT = window.getHeight();

        field = new Rectangle(0, 0, WI_WIDTH, WI_HEIGHT);
        setDefaultSize(1000, 700);
        setColorOverField(new Color(42, 42, 42,255));

    }

    /**
     * 设置分辨率（默认1000x700）
     * 一旦设置就不要更改了哦
     * 分辨率必须与window的大小相同，否则会出现奇奇怪怪的bug
     * 分辨率必须小于屏幕长宽一些
     */
    public void setDefaultSize(int width, int height) {

        bufferWidth = width;
        bufferHeight = height;
        overPercent =  MathData.toDouble(WI_HEIGHT) / bufferHeight;
        totalHeight = MathData.round(height * overPercent);
        totalWidth = MathData.round(width * overPercent);

        SC_WIDTH = totalWidth;
        SC_HEIGHT = totalHeight;
        SC_LEFT = (WI_WIDTH - SC_WIDTH) / 2;
        SC_TOP = (WI_HEIGHT - SC_HEIGHT) / 2;

        leftBuffer = SC_LEFT;
        topBuffer = SC_TOP;

        callChangeSize();

    }

    public void setRepaintField(Rectangle field) {

            this.field = field;

    }

    public void setColorOverField(Color c) {

        overFieldColor = c;

    }

    /**
     * 当设置分辨率时调用
     */
    public void callChangeSize() {

        imageBuffer = screen.createImage(bufferWidth, bufferHeight);

    }

    public void init() {

        init = true;

    }

    public void tick() {}

    /**
     * 时间和循环的变化
     */
    public void cycleTime() {

        timer++;
        if(timer > 36000) {
            timer = 0;
        }

        if(cycle >= 1) {
            cycleState = false;
        }
        else if(cycle <= 0 ) {
            cycleState = true;
        }

        if(cycleState) {
            cycle += 0.01;
        }
        else {
            cycle -= 0.01;
        }

    }

    /**
     * 每360刻进行tick次操作
     * @return 可操作时返回true
     */
    public boolean forTick(int tick) {

        return timer % tick == 0;

    }

    public void bufferTick() {

        //双缓冲
        if(imageBuffer == null) {
            callChangeSize();
            graphicsBuffer = imageBuffer.getGraphics();
        }
        Graphic2D.renderRect(0, 0, WI_WIDTH, WI_HEIGHT, graphicsBuffer);

        //初始化
        if (!init) {
            init();
        }

    }

    public void bufferPaint(Graphics g) {

        //将缓冲图片绘制到screen上
        Graphic.render(SC_LEFT, SC_TOP, SC_WIDTH, SC_HEIGHT, imageBuffer, g);
        //当屏幕比例不对时，在两边绘制黑色方框
        Graphic2D.renderRect2D(0, 0, SC_LEFT, WI_HEIGHT, g);
        Graphic2D.renderRect2D(SC_WIDTH + SC_LEFT, 0, WI_WIDTH, WI_HEIGHT, g);

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
    public void paint(Graphics g) {

        cycleTime();
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

    public void renderBottomImage(Graphics g) {

        Element e;
        int size = imageBottom.size();

        for(int i = 0; i < size; i++) {
            e = imageBottom.get(i);
            e.update(window, g);
        }

    }

    public void renderMiddleImage(Graphics g) {

        Element e;
        int size = imageMiddle.size();

        for(int i = 0; i < size; i++) {
            e = imageMiddle.get(i);
            e.update(window, g);
        }

    }

    public void renderFrontImage(Graphics g) {

        Element e;
        int size = imageFront.size();

        for(int i = 0; i < size; i++) {
            e = imageFront.get(i);
            e.update(window, g);
        }

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

    /**
     * @return 获得一个计时器
     */
    public int getTimer() {

        return timer;

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

    /**
     * 好吧，你还需要更自由的？
     * @return 在缓冲图作画的画笔
     */
    public Graphics getGraphicsBuffer() {

        return graphicsBuffer;

    }

    public Point randomPoint() {

        int x = MathData.round(MathData.random(field.getOffsetX(), field.getX2()));
        int y = MathData.round(MathData.random(field.getOffsetY(), field.getY2()));
        return new Point(x, y);

    }

    /**
     * 屏幕摇晃
     * @param force 力度
     */
    public void earthQuake(int force) {

        SC_LEFT = MathData.round(leftBuffer + MathData.random(-force, force));
        SC_TOP = MathData.round(topBuffer + MathData.random(-force, force));

    }

    /**
     * 还原屏幕位置
     */
    public void resetLocation() {

        SC_LEFT = leftBuffer;
        SC_TOP = topBuffer;

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
