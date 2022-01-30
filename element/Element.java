package rainy2D.element;

import rainy2D.render.desktop.Window;
import rainy2D.render.graphic.Graphic;
import rainy2D.resource.ImageLocation;
import rainy2D.shape.Circle;
import rainy2D.shape.Rectangle;
import rainy2D.util.MathData;

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

    public Element(double offsetX, double offsetY, int width, int height, ImageLocation iml) {

        this(offsetX, offsetY, width, height, iml.get());

    }

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

    }

    public void render(Graphics g) {

        Graphic.render(this, g);

    }

    public void tick(Window window) {}

    public void update(Window window, Graphics g) {

        tick(window);
        render(g);

        timer++;
        if(timer > 360) {
            timer = 0;
        }

    }

    public boolean checkOutWindow(Window window) {

        Rectangle field = window.getScreenIn().getField();

        if(x + width + 20 < field.getOffsetX() ||
                x - 30 > field.getX2() ||
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

    public void setSize(int width, int height) {

        this.width = width;
        this.height = height;

    }

    public void setImage(BufferedImage img) {

        this.img = img;

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

        circle.locate(MathData.round(x), MathData.round(y));
        circle.setSize(width / 2, width / 2);

        return circle;

    }

    public Rectangle getRect() {

        rect.locateOffset(MathData.round(offsetX), MathData.round(offsetY));
        rect.setSize(width, height);

        return rect;

    }

}
