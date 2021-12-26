package rainy2D.render.element;

import rainy2D.render.RenderHelper;
import rainy2D.shape.Circle;
import rainy2D.render.window.Window;
import rainy2D.resource.ImageLocation;
import rainy2D.shape.Rect;

import java.awt.*;
import java.awt.event.MouseEvent;

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
    boolean visible;
    boolean outWindow;
    ImageLocation iml;

    int tickTime;
    boolean isUsed;
    int indexInPool;

    public Element(double offsetX, double offsetY, int width, int height, ImageLocation iml) {

        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.x = offsetX + width / 2;
        this.y = offsetY + width / 2;
        this.width = width;
        this.height = height;
        this.iml = iml;
        this.visible = true;

    }

    public void render(Graphics g) {

        RenderHelper.render((int) offsetX, (int) offsetY, width, height, iml, g);

    }

    public void tick(Window window) {
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

    public void size(int width, int height) {

        this.width = width;
        this.height = height;

    }

    public void setImage(ImageLocation location) {

        this.iml = location;

    }

    public void setIndexInPool(int indexInPool) {

        this.indexInPool = indexInPool;

    }

    public void setVisible(boolean visible) {

        this.visible = visible;

    }

    public void setUsed(boolean used) {

        isUsed = used;

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

    public ImageLocation getIml() {

        return iml;

    }

    public int getIndexInPool() {

        return indexInPool;

    }

    public boolean isVisible() {

        return visible;

    }

    public boolean isUsed() {

        return isUsed;

    }

    public boolean isOutWindow() {

        return outWindow;

    }

    public Circle getCircle() {

        return new Circle((int) x, (int) y, width / 2 - 3);

    }

    public Rect getRect() {

        return new Rect((int) offsetX, (int) offsetY, width, height);

    }

}
