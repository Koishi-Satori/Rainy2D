package rainy2D.render.element;

import rainy2D.render.RenderHelper;
import rainy2D.shape.Circle;
import rainy2D.render.window.Window;
import rainy2D.resource.ImageLocation;
import rainy2D.shape.Rect;
import rainy2D.util.MathData;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * 构造器：offset
 * 需要传入左上角的xy
 */
public class Element {

    double x;
    double y;
    double offsetX;
    double offsetY;
    int width;
    int height;
    boolean outWindow;

    ImageLocation iml;
    BufferedImage img;

    public Element(double offsetX, double offsetY, int width, int height, ImageLocation iml) {

        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.x = offsetX + width / 2;
        this.y = offsetY + width / 2;
        this.width = width;
        this.height = height;
        this.iml = iml;
        this.callImageChange();

    }

    public void render(Graphics g) {

        RenderHelper.render(this, g);

    }

    public void tick(Window window) {
    }

    public void callImageChange() {

        if(iml != null) {
            this.img = iml.get();
        }

    }

    public boolean isMouseHanging(MouseEvent event) {

        int mouseX = event.getX();
        int mouseY = event.getY();

        return mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height;

    }

    //设置器方法
    public void locate(double x, double y) {

        this.x = x;
        this.y = y;
        this.offsetX = x - width / 2;
        this.offsetY = y - height / 2;

    }

    public void locateOffset(double offsetX, double offsetY) {

        this.x = offsetX + width / 2;
        this.y = offsetY + height / 2;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

    }

    public void size(int width, int height) {

        this.width = width;
        this.height = height;

    }

    public void setImageLocation(ImageLocation iml) {

        this.iml = iml;
        this.callImageChange();

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

    public Circle getCircle() {

        return new Circle(MathData.toInt(x), MathData.toInt(y), MathData.toInt(width / 6));

    }

    public Rect getRect() {

        return new Rect(MathData.toInt(offsetX), MathData.toInt(offsetY), width, height);

    }

}
