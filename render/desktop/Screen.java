package rainy2D.render.desktop;

import rainy2D.element.Element;
import rainy2D.render.graphic.Graphic;
import rainy2D.render.graphic.Graphic2D;
import rainy2D.shape.Rectangle;
import rainy2D.util.Array;
import rainy2D.util.MathData;

import javax.swing.*;
import java.awt.*;

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

    public int fps;
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

    String titleName;
    Rectangle field;
    Image imageBuffer;
    Graphics graphicsBuffer;
    Color overFieldColor;

    public Window window;

    public Array<Element> imageBottom = new Array<>();
    public Array<Element> imageMiddle = new Array<>();
    public Array<Element> imageFront = new Array<>();

    boolean isPause;

    public Screen(Window window) {

        this.window = window;
        WI_WIDTH = window.getWidth();
        WI_HEIGHT = window.getHeight();

        field = new Rectangle(0, 0, WI_WIDTH, WI_HEIGHT);
        setDefaultSize(900, 600);
        setDefaultFps(60);

        new Thread(this).start();

    }

    /**
     * 设置分辨率（默认900x600）
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

        setBackground(c);

    }

    public void setColorOverField(Color c) {

        overFieldColor = c;

    }

    /**
     * 写法：继承本类，然后复写init（别忘了调用super.init()），在方法内调用add方法，写法如下：
     * this.add(pool.get(out, 25, 25, 0.4, getTimer(), new ImageLocation("img/plp.png")), bullets);
     */
    public void init() {

        init = true;

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
    public void add(Element e, Array list) {

        list.add(e);

    }

    public void bufferTick() {

        //双缓冲
        if(imageBuffer == null) {
            callChangeSize();
            graphicsBuffer = imageBuffer.getGraphics();
            graphicsBuffer.setColor(getBackground());
        }
        Graphic2D.renderRect(0, 0, WI_WIDTH, WI_HEIGHT, graphicsBuffer);
        super.paint(graphicsBuffer);

        //初始化
        if (!init) {
            init();
        }

        //计时
        cycleTime();

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
    @Override
    public void paint(Graphics g) {

        bufferTick();

        //遍历调刻和渲染(越后调用，图层处于越高层）
        //此处可以继承本类并修改
        renderBottomImage(graphicsBuffer);
        renderMiddleImage(graphicsBuffer);
        tick();
        renderFrontImage(graphicsBuffer);

        bufferPaint(g);

    }

    /**
     * 时间和循环的变化
     */
    public void cycleTime() {

        time ++;
        if(time == Integer.MAX_VALUE) {
            time = 0;
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
     * 每x刻进行一次操作
     * @param tick 间隔
     * @return 可操作时返回true
     */
    public boolean forTick(int tick) {

        return getTimer() % tick == 0;

    }

    /**
     * 每x刻进行一次操作
     * @param tick 间隔
     * @param period 持续时间
     * @return 可操作时返回true
     */
    public boolean forTick(int tick, int period) {

        return getTimer() % tick < period;

    }

    /**
     * 当设置分辨率时调用
     */
    public void callChangeSize() {

        imageBuffer = createImage(bufferWidth, bufferHeight);

    }

    public void renderBottomImage(Graphics g) {

        Element e;

        for(int i = 0; i < imageBottom.size(); i++) {
            e = imageBottom.get(i);
            e.tick(window);
            e.render(g);
        }

    }

    public void renderMiddleImage(Graphics g) {

        Element e;

        for(int i = 0; i < imageMiddle.size(); i++) {
            e = imageMiddle.get(i);
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

        isPause = !isPause;

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
                    window.setScreenIn(this);
                    repaint();
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
                    nowFps = frame + updateTime;
                    frame = 0;
                }

            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

    }

}
