package rainy2D.render;

import rainy2D.render.element.Element;
import rainy2D.resource.ImageLocation;
import rainy2D.util.MathData;

import java.awt.*;

public class RenderHelper {

    /**
     * 传入element自动渲染
     * @param e 一个element
     * @param g 画笔
     */
    public static void render(Element e, Graphics g) {

        render(MathData.toInt(e.getOffsetX()), MathData.toInt(e.getOffsetY()), e.getWidth(), e.getHeight(), e.getImage(), g);

    }

    /**
     * 中心坐标渲染
     * @param x 中心坐标x
     * @param y 中心坐标y
     * @param width 宽度
     * @param height 高度
     * @param iml 图片路径
     * @param g 画笔
     */
    public static void renderIn(int x, int y, int width, int height, ImageLocation iml, Graphics g) {

        render(x - width / 2, y - height / 2, width, height, iml, g);

    }

    public static void renderIn(int x, int y, int width, int height, Image img, Graphics g) {

        render(x - width / 2, y - height / 2, width, height, img, g);

    }

    /**
     * 标准坐标渲染
     * @param offsetX 左上角坐标x
     * @param offsetY 左上角坐标y
     * @param width 宽度
     * @param height 高度
     * @param iml 图片路径
     * @param g 画笔
     */
    public static void render(int offsetX, int offsetY, int width, int height, ImageLocation iml, Graphics g) {

        render(offsetX, offsetY, width, height, iml.get(), g);

    }

    public static void render(int offsetX, int offsetY, int width, int height, Image img, Graphics g) {

        g.drawImage(img, offsetX, offsetY, width, height, null);

    }

    /**
     * 自动长宽渲染
     * @param offsetX 左上角坐标x
     * @param offsetY 左上角坐标y
     * @param iml 图片路径
     * @param g 画笔
     */
    public static void render(int offsetX, int offsetY, ImageLocation iml, Graphics g) {

        render(offsetX, offsetY, iml.getWidth(), iml.getHeight(), iml, g);

    }

    public static void render(int offsetX, int offsetY, Image img, Graphics g) {

        render(offsetX, offsetY, img.getWidth(null), img.getHeight(null), img, g);

    }

}
