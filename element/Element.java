package rainy2D.element;

import rainy2D.render.desktop.Canvas;
import rainy2D.render.desktop.Window;
import rainy2D.render.graphic.Graphic;
import rainy2D.shape.Circle;
import rainy2D.shape.Rectangle;
import rainy2D.util.Maths;
import rainy2D.util.WaitTimer;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 构造器：offset
 * 需要传入左上角的xy
 */
public class Element {

    protected double x;
    protected double y;
    protected double offsetX;
    protected double offsetY;
    protected int width;
    protected int height;

    protected int timer;

    protected BufferedImage img;
    protected BufferedImage imgBackup;

    protected Circle circle;
    protected Rectangle rect;

    protected WaitTimer waiter;

    public Element(double offsetX, double offsetY, int width, int height, BufferedImage img) {

        x = offsetX + width / 2;
        y = offsetY + height / 2;

        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.img = img;
        imgBackup = img;

        circle = new Circle(0, 0, 0);
        rect = new Rectangle(0, 0, 0, 0);
        waiter = new WaitTimer();

    }

    /**
     * 模板构造器，不传入位置信息，需要后期定位
     */
    public Element(int width, int height, BufferedImage img) {

        this(0, 0, width, height, img);

    }

    public void render(Graphics g) {

        Graphic.render(this, g);

    }

    public void callTimer() {

        waiter.update();

        timer++;

    }

    public void tick(Canvas canvas) {}

    public void update(Canvas canvas, Graphics g) {

        if(!canvas.isPause) {
            tick(canvas);
        }
        render(g);
        callTimer();

    }

    public boolean checkOutWindow(Window window) {

        Rectangle field = window.getScreenIn().getCanvas().getField();

        if(x + width + 20 < field.getOffsetX() ||
                x - 20 > field.getX2() ||
                y + height + 20 < field.getOffsetY() ||
                y - 20 > field.getY2()) {
            return true;
        }

        return false;

    }

    /**
     * 请务必使用locate设置位置！否则不会渲染
     * @param x 中心x
     * @param y 中心y
     */
    public void locate(double x, double y) {

        this.x = x;
        this.y = y;
        offsetX = x - width / 2;
        offsetY = y - height / 2;

    }

    /**
     * 请务必使用locate设置位置！否则不会渲染
     * @param offsetX 左上角x
     * @param offsetY 左上角y
     */
    public void locateOffset(double offsetX, double offsetY) {

        x = offsetX + width / 2;
        y = offsetY + height / 2;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

    }

    /**
     * 已经处理了设置后位置偏移的问题
     */
    public void setSize(int width, int height) {

        //摆正位置
        locateOffset(offsetX + (this.width - width) / 2, offsetY + (this.height - height) / 2);
        this.width = width;
        this.height = height;

    }

    public void setImage(BufferedImage img) {

        this.img = img;
        imgBackup = img;

    }

    public void setTimer(int timer) {

        this.timer = timer;

    }

    public double getX() {

        return x;

    }

    public double getY() {

        return y;

    }

    public double getOffsetX() {

        return offsetX;

    }

    public double getOffsetY() {

        return offsetY;

    }

    public int getWidth() {

        return width;

    }

    public int getHeight() {

        return height;

    }

    public BufferedImage getImage() {

        return img;

    }

    public int getTimer() {

        return timer;

    }

    public boolean forTick(int tick) {

        return timer % tick == 0;

    }

    /**
     * 用于子弹判定
     * @return 返回判定半径为：1/2 width的圆形
     */
    public Circle getCircle() {

        circle.locate(Maths.round(x), Maths.round(y));
        circle.setSize(width / 2, width / 2);

        return circle;

    }

    public Rectangle getRect() {

        rect.locateOffset(Maths.round(offsetX), Maths.round(offsetY));
        rect.setSize(width, height);

        return rect;

    }

}
