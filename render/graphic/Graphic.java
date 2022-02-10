package rainy2D.render.graphic;

import rainy2D.element.Element;
import rainy2D.resource.ImageLocation;
import rainy2D.util.Maths;

import java.awt.*;
import java.awt.image.BufferedImage;

//**USE JAVA SWING**//
public class Graphic {

    public static BufferedImage cut(double x, double y, double width, double height, BufferedImage img) {

        return img.getSubimage(Maths.round(x), Maths.round(y), Maths.round(width), Maths.round(height));

    }

    /**
     * 自动切割图片绘制，需要保证图片与画布大小一致
     */
    public static void renderCut(double offsetX, double offsetY, double width, double height, BufferedImage img, Graphics g) {

        renderCut(offsetX, offsetY, width, height, offsetX, offsetY, width, height, img, g);

    }

    /**
     * 可变切割渲染
     */
    public static void renderCut(double ix, double iy, double iw, double ih, double offsetX, double offsetY, double width, double height, BufferedImage img, Graphics g) {

        render(offsetX, offsetY, width, height, cut(ix, iy, iw, ih, img), g);

    }

    /**
     * 传入element自动渲染
     * @param e 一个element
     * @param g 画笔
     */
    public static void render(Element e, Graphics g) {

        render(e.getOffsetX(), e.getOffsetY(), e.getWidth(), e.getHeight(), e.getImage(), g);

    }

    public static void renderRotate(Element e, double angle, Graphics g) {

        render(e.getOffsetX(), e.getOffsetY(), e.getWidth(), e.getHeight(), Graphic2D.rotate(e.getImage(), angle), g);

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

    public static void renderIn(double x, double y, double width, double height, BufferedImage img, Graphics g) {

        render(x - width / 2, y - height / 2, width, height, img, g);

    }

    public static void renderIn(double x, double y, BufferedImage img, Graphics g) {

        int w = img.getWidth();
        int h = img.getHeight();

        render(x - w / 2, y - h / 2, w, h, img, g);

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

        g.drawImage(img, Maths.round(offsetX), Maths.round(offsetY), Maths.round(width), Maths.round(height), null);

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
