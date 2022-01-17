package rainy2D.render.element;

import rainy2D.render.helper.RenderHelper;
import rainy2D.shape.Circle;
import rainy2D.render.desktop.Window;
import rainy2D.resource.ImageLocation;
import rainy2D.shape.Rectangle;
import rainy2D.util.MathData;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * 构造器：offset
 * 需要传入左上角的xy
 */
public class Element {

    @Deprecated
    public static double MAGNIFY;

    double x;
    double y;
    double offsetX;
    double offsetY;
    int width;
    int height;
    boolean outWindow;

    BufferedImage img;

    public Element(double offsetX, double offsetY, int width, int height, ImageLocation iml) {

        this(offsetX, offsetY, width, height, iml.get());

    }

    public Element(double offsetX, double offsetY, int width, int height, BufferedImage img) {

        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.x = offsetX + width / 2;
        this.y = offsetY + width / 2;
        this.width = width;
        this.height = height;
        this.img = img;

    }

    public void render(Graphics g) {

        RenderHelper.render(this, g);

    }

    public void tick(Window window) {
    }

    public boolean isMouseHanging(MouseEvent event) {

        int mouseX = event.getX();
        int mouseY = event.getY();

        return mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height;

    }

    public void checkOutWindow(Window window) {

        Rectangle field = window.getScreenIn().getField();

        if(x + width < field.getX() ||
                x > field.getX() + field.getWidth() ||
                y + height + 200 < field.getY() ||
                y > field.getY() + field.getHeight()) {
            this.setOutWindow(true);
        } else {
            this.setOutWindow(false);
        }

    }

    /**
     * 请务必使用locate设置位置！否则不会渲染
     * @param x 中心x
     * @param y 中心y
     */
    public void locate(double x, double y) {

        this.x = x;
        this.y = y;
        this.offsetX = x - width / 2;
        this.offsetY = y - height / 2;

    }

    /**
     * 请务必使用locate设置位置！否则不会渲染
     * @param offsetX 左上角x
     * @param offsetY 左上角y
     */
    public void locateOffset(double offsetX, double offsetY) {

        this.x = offsetX + width / 2;
        this.y = offsetY + height / 2;
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

    public void setOutWindow(boolean outWindow) {

        this.outWindow = outWindow;

    }

    //获取器方法
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

    public boolean isOutWindow() {

        return outWindow;

    }

    /**
     * 用于子弹判定
     * @return 返回判定半径为：1/6 width的圆形
     */
    public Circle getCircle() {

        return new Circle(MathData.round(x), MathData.round(y), MathData.round(width / 6));

    }

    public Rectangle getRect() {

        return new Rectangle(MathData.round(offsetX), MathData.round(offsetY), width, height);

    }

    public Element getClone() {

        return new Element(x, y, width, height, img);

    }

}
