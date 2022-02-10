package rainy2D.render.desktop;

import rainy2D.element.Element;
import rainy2D.element.action.ElementButton;
import rainy2D.element.vector.ElementBullet;
import rainy2D.element.vector.ElementEnemy;
import rainy2D.element.vector.ElementVector;
import rainy2D.render.graphic.Graphic;
import rainy2D.render.graphic.Graphic2D;
import rainy2D.resource.AudioLocation;
import rainy2D.shape.Point;
import rainy2D.shape.Rectangle;
import rainy2D.util.Array;
import rainy2D.util.Input;
import rainy2D.util.Maths;
import rainy2D.util.WaitTimer;
import rainy2D.util.list.BulletCacheList;
import rainy2D.util.list.EffectCacheList;
import rainy2D.util.list.EnemyCacheList;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
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
     * l/t/r/b:field四周坐标
     */
    protected int bufferWidth;
    protected int bufferHeight;
    double overPercent;
    int totalWidth;
    int totalHeight;

    public int left;
    public int top;
    public int right;
    public int bottom;

    /**
     * 缓存SC变量
     */
    int leftBuffer;
    int topBuffer;

    Image imageBuffer;
    Graphics graphicsBuffer;

    BufferedImage overFieldImage;

    public Rectangle field;
    public Window window;
    public Screen screen;

    /**
     * time根据fps递增
     * cycle在[0-1]以0.01的速度循环
     */
    int timer;
    double cycle;
    private double cycleSpeed;
    private boolean cycleState;

    boolean init;
    /**
     * Pause暂停的原理：
     * 暂停元素tick和画布tick
     * 如果复写其他的方法不会被暂停！！
     */
    public boolean isPause;

    public Array<Element> imageBottom = new Array<>(50);
    public Array<Element> imageMiddle = new Array<>(50);
    public Array<Element> imageFront = new Array<>(50);
    public Array<Element> imageAbove = new Array<>(50);
    public Array<ElementButton> buttons = new Array<>(50);

    public Array<ElementBullet> bullets = new Array<>(4000);
    public Array<ElementEnemy> enemies = new Array<>(200);
    public Array<ElementVector> effects = new Array<>(500);

    public BulletCacheList bulletCache = new BulletCacheList(10000);
    public EnemyCacheList enemyCache = new EnemyCacheList(1000);
    public EffectCacheList effectCache = new EffectCacheList(1000);

    public Array<WaitTimer> waitRequests = new Array<>(10);

    public AudioLocation audio;

    ExecutorService service = Executors.newCachedThreadPool();

    public Canvas(Window win) {

        window = win;//获取所处的窗口和屏幕
        screen = window.getScreenIn();

        WI_WIDTH = window.getWidth();
        WI_HEIGHT = window.getHeight();

        for(int i = 0; i < 10; i++) {
            waitRequests.add(new WaitTimer());
        }

        field = new Rectangle(0, 0, WI_WIDTH, WI_HEIGHT);
        setDefaultSize(1000, 700);

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
        overPercent =  Maths.toDouble(WI_HEIGHT) / bufferHeight;
        totalHeight = Maths.round(height * overPercent);
        totalWidth = Maths.round(width * overPercent);//计算显示宽高

        SC_WIDTH = totalWidth;
        SC_HEIGHT = totalHeight;
        SC_LEFT = (WI_WIDTH - SC_WIDTH) / 2;
        SC_TOP = (WI_HEIGHT - SC_HEIGHT) / 2;//设置SC参数

        leftBuffer = SC_LEFT;
        topBuffer = SC_TOP;//屏幕震动缓存

        callChangeSize();

    }

    public void setRepaintField(Rectangle rect) {

        field = rect;

        left = field.getOffsetX();
        top = field.getOffsetY();
        right = field.getX2();
        bottom = field.getY2();

    }

    public void setImageOverField(BufferedImage img) {

        overFieldImage = img;

    }

    /**
     * 当设置分辨率时调用
     */
    public void callChangeSize() {

        imageBuffer = screen.createImage(bufferWidth, bufferHeight);

    }

    public void setMainLoopMusic(AudioLocation aul) {

        if(Maths.noNull(audio)) {
            audio.stop();
        }
        if(Maths.noNull(aul)) {
            audio = aul;
            audio.loopPlay();
        }

    }

    public void init() {

        init = true;

    }

    /**
     * 时间和循环的变化
     */
    public void cycleTime() {

        timer++;

        if(cycle >= 1) {
            cycleState = false;
        }
        else if(cycle <= 0 ) {
            cycleState = true;
        }

        if(cycleState) {
            cycleSpeed += 0.02;
        }
        else {
            cycleSpeed -= 0.02;
        }

        cycle = Maths.easySlow(3, cycleSpeed);

        for(int i = 0; i < waitRequests.size(); i++) {
            waitRequests.get(i).update();
        }

    }

    /**
     * 等待一段时间后不断触发isWaitBack方法
     * if(isWaitBack())
     * wait(50);
     * 这样可以做成定时触发
     */
    public void wait(int waitTime, int requestNum) {

        waitRequests.get(requestNum).wait(waitTime);

    }

    public boolean isWaitBack(int requestNum) {

        return waitRequests.get(requestNum).isWaitBack();

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
        if(imageBuffer == null || graphicsBuffer == null) {
            callChangeSize();
            graphicsBuffer = imageBuffer.getGraphics();
        }
        //Graphic2D.renderRect(0, 0, WI_WIDTH, WI_HEIGHT, graphicsBuffer);

    }

    public void bufferPaint(Graphics g) {

        //将缓冲图片绘制到screen上
        Graphic.render(SC_LEFT, SC_TOP, SC_WIDTH, SC_HEIGHT, imageBuffer, g);
        //当屏幕比例不对时，在两边绘制黑色方框
        Graphic2D.renderRect2D(0, 0, SC_LEFT, WI_HEIGHT, g);
        Graphic2D.renderRect2D(SC_WIDTH + SC_LEFT, 0, WI_WIDTH, WI_HEIGHT, g);

    }

    /**
     * 默认生成的是一个1000x700的图像
     * 放置组件都要在这个范围内进行，然后按比例绘制到屏幕上。
     * 原理：
     * 先在底层的window上设置相同大小的screen，然后创建缓冲图片，在图片上绘制画面，并按比例缩放到window上。
     * 所以严格来说只有一个image组件
     * 需要注意的是，添加组件时是绘制在缓冲图上，所以请不要使用SC系列的变量了。范围为[0, 0] ~ [bufferWidth, bufferHeight]
     * @param g 没什么可说的？
     * @Layers
     * bottom(field底层 背景)-middle(field中层)--front(field高层 玩家)-buttons-above(field之上 信息栏)
     * @LayersYouCanUse
     * only public method
     */
    public void paint(Graphics g) {

        if (!init) {
            init();
        }

        //mouseMotionFrame();
        bufferTick();

        renderBottomImage(graphicsBuffer);
        renderBottomField(graphicsBuffer);//CUSTOM

        //**OVERRIDE**//
        if(!isPause) {
            tick();
            cycleTime();
        }
        renderMiddleImage(graphicsBuffer);
        renderMiddleField(graphicsBuffer);//CUSTOM

        renderFrontImage(graphicsBuffer);
        renderFrontField(graphicsBuffer);//CUSTOM

        effectTick(graphicsBuffer);
        enemyTick(graphicsBuffer);
        bulletTick(graphicsBuffer);

        renderOverBE(graphicsBuffer);//CUSTOM

        renderFrame(graphicsBuffer);

        buttonAction();
        buttonTick(graphicsBuffer);

        renderAboveImage(graphicsBuffer);
        renderAboveField(graphicsBuffer);//CUSTOM

        bufferPaint(g);

    }

    public void buttonAction() {}

    public void tick() {}

    public void render(Graphics g) {}

    public void renderBottomField(Graphics g) {}

    public void renderMiddleField(Graphics g) {}

    public void renderFrontField(Graphics g) {}

    public void renderOverBE(Graphics g) {}

    public void renderAboveField(Graphics g) {}

    private void renderBottomImage(Graphics g) {

        Element e;
        int size = imageBottom.size();

        for(int i = 0; i < size; i++) {
            e = imageBottom.get(i);
            e.update(this, g);
        }

    }

    private void renderMiddleImage(Graphics g) {

        Element e;
        int size = imageMiddle.size();

        for(int i = 0; i < size; i++) {
            e = imageMiddle.get(i);
            e.update(this, g);
        }

    }

    private void renderFrontImage(Graphics g) {

        Element e;
        int size = imageFront.size();

        for(int i = 0; i < size; i++) {
            e = imageFront.get(i);
            e.update(this, g);
        }

    }

    private void renderAboveImage(Graphics g) {

        Element e;
        int size = imageAbove.size();

        for(int i = 0; i < size; i++) {
            e = imageAbove.get(i);
            e.update(this, g);
        }

    }

    private void buttonTick(Graphics g) {

        ElementButton e;
        int size = buttons.size();

        for (int i = 0; i < size; i++) {
            if(buttons.get(i) != null) {
                e = buttons.get(i);
                e.update(this, g);
            }
        }

    }

    private void bulletTick(Graphics g) {

        ElementBullet e;
        int size = bullets.size();

        for (int i = 0; i < size; i++) {
            if(bullets.get(i) != null) {
                e = bullets.get(i);
                e.update(this, g);
            }
        }

    }

    private void enemyTick(Graphics g) {

        ElementEnemy e;
        int size = enemies.size();

        for (int i = 0; i < size; i++) {
            if(enemies.get(i) != null) {
                e = enemies.get(i);
                e.update(this, g);
            }
        }

    }

    private void effectTick(Graphics g) {

        ElementVector e;
        int size = effects.size();

        for (int i = 0; i < size; i++) {
            if(effects.get(i) != null) {
                e = effects.get(i);
                e.update(this, g);
                effectMove(e);
            }
        }

        service.submit(new Remover());

    }

    public void effectMove(ElementVector e) {}

    /**
     * 填充除field以外的部分
     * @param g 画笔
     */
    private void renderFrame(Graphics g) {

        double ixr = right / overPercent;
        double ixl = left / overPercent;
        double iyt = top / overPercent;
        double iyb = bottom / overPercent;//图片裁剪参数

        if(overFieldImage != null) {
            Graphic.renderCut(0 ,0, ixl, bufferHeight, 0, 0, left, SC_HEIGHT, overFieldImage, g);
            Graphic.renderCut(0, 0, bufferWidth, iyt, 0, 0, SC_WIDTH, top, overFieldImage, g);
            Graphic.renderCut(ixr, iyt, bufferWidth - ixr, bufferHeight - iyt, right, top, SC_WIDTH - right, SC_HEIGHT - top, overFieldImage, g);
            Graphic.renderCut(ixl, iyb, bufferWidth - ixl, bufferHeight - iyb, left, bottom, SC_WIDTH - left, SC_HEIGHT - bottom, overFieldImage, g);
        }

        Graphic2D.setColor(Graphic2D.SHADOW, g);
        Graphic2D.renderFrame(left, top, right, bottom, 5, g);

        //自定义边框（JFrame边框有误差）
        //Graphic2D.setColor(frameColor, g);
        //Graphic2D.renderFrame(0, 0, WI_WIDTH, WI_HEIGHT, frameWidth, g);
        //Graphic2D.renderRect(0, 0, WI_WIDTH, frameTop, g);

    }

    /**
     * 暂停时发生的逻辑
     */
    public void pauseTick() {}

    public void mouseMotionFrame() {

        Input i = screen.input;

        if(i.isMouseClicking() && i.getMouseY() < 20) {
            window.setLocation(i.getFullMouseX(), i.getFullMouseY());
        }

    }

    public Input getInput() {

        return screen.input;

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
     * @return 在缓冲图作画的画笔
     */
    public Graphics getGraphicsBuffer() {

        return graphicsBuffer;

    }

    public Point randomPoint() {

        int x = Maths.round(Maths.random(field.getOffsetX(), field.getX2()));
        int y = Maths.round(Maths.random(field.getOffsetY(), field.getY2()));
        return new Point(x, y);

    }

    /**
     * 屏幕摇晃
     * @param force 力度
     */
    public void earthQuake(int force) {

        SC_LEFT = Maths.round(leftBuffer + Maths.random(-force, force));
        SC_TOP = Maths.round(topBuffer + Maths.random(-force, force));

    }

    /**
     * 还原屏幕位置
     */
    public void resetLocation() {

        SC_LEFT = leftBuffer;
        SC_TOP = topBuffer;

    }

    public void unPause() {

        isPause = false;

    }

    /**
     * 手动加入tick才有暂停功能！
     */
    public void checkPause() {

        if(screen.input.isKeyDown(KeyEvent.VK_ESCAPE)) {
            isPause = true;
        }

    }

    private class Remover extends Thread {

        @Override
        public void run() {

            ElementBullet b;
            ElementEnemy e;
            ElementVector v;

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

            size = effects.size();

            for (int i = 0; i < size; i++) {
                if(effects.get(i) != null) {
                    v = effects.get(i);

                    if(v.checkOutWindow(window) || v.getWidth() <= 0) {
                        effects.remove(i);
                        effectCache.reuse(v);
                    }
                }
            }

        }

    }

}
