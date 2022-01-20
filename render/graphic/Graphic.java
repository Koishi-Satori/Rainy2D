package rainy2D.render.graphic;

import rainy2D.element.Element;
import rainy2D.resource.ImageLocation;
import rainy2D.util.MathData;

import java.awt.*;

public class Graphic {

    /**
     * 传入element自动渲染
     * @param e 一个element
     * @param g 画笔
     */
    public static void render(Element e, Graphics g) {

        render(e.getOffsetX(), e.getOffsetY(), e.getWidth(), e.getHeight(), e.getImage(), g);

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
    public static void renderIn(double x, double y, double width, double height, ImageLocation iml, Graphics g) {

        render(x - width / 2, y - height / 2, width, height, iml, g);

    }

        public static void renderIn(double x, double y, double width, double height, Image img, Graphics g) {

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
    public static void render(double offsetX, double offsetY, double width, double height, ImageLocation iml, Graphics g) {

        render(offsetX, offsetY, width, height, iml.get(), g);

    }

    public static void render(double offsetX, double offsetY, double width, double height, Image img, Graphics g) {

        g.drawImage(img, MathData.round(offsetX), MathData.round(offsetY), MathData.round(width), MathData.round(height), null);

    }

    /**
     * 自动长宽渲染
     * @param offsetX 左上角坐标x
     * @param offsetY 左上角坐标y
     * @param iml 图片路径
     * @param g 画笔
     */
    public static void render(double offsetX, double offsetY, ImageLocation iml, Graphics g) {

        render(offsetX, offsetY, iml.getWidth(), iml.getHeight(), iml, g);

    }

    public static void render(double offsetX, double offsetY, Image img, Graphics g) {

        render(offsetX, offsetY, img.getWidth(null), img.getHeight(null), img, g);

    }

}
