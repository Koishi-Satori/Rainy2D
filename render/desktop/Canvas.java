package rainy2D.render.desktop;

import rainy2D.element.Element;
import rainy2D.render.graphic.Graphic;
import rainy2D.render.graphic.Graphic2D;
import rainy2D.shape.Rectangle;
import rainy2D.util.Array;

import java.awt.*;

public class Canvas {

    public Canvas() {



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
    public void tick() {}

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
    public void paint(Graphics g) {

        bufferTick();

        //遍历调刻和渲染(越后调用，图层处于越高层）
        //此处可以继承本类并修改
        renderBottomImage(graphicsBuffer);
        renderMiddleImage(graphicsBuffer);
        renderFrontImage(graphicsBuffer);
        tick();

        bufferPaint(g);

    }

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

    /**
     * 当设置分辨率时调用
     */
    public void callChangeSize() {

        imageBuffer = createImage(bufferWidth, bufferHeight);

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

}
