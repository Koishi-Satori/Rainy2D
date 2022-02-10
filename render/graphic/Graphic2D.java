package rainy2D.render.graphic;

import rainy2D.shape.Circle;
import rainy2D.shape.Rectangle;
import rainy2D.util.Maths;

import java.awt.*;
import java.awt.image.BufferedImage;

//**USE JAVA SWING && AWT**//
public class Graphic2D {

    public static Color DEFAULT_COLOR_OUT = new Color(56, 26, 24);
    public static Color DEFAULT_COLOR_IN = new Color(255, 234, 199);
    public static Color SHADOW = new Color(0, 0, 0, 100);

    public static Font DEFAULT_FONT = new Font("宋体", 1, 30);
    public static Font BIG_FONT = new Font("宋体", 1, 45);
    public static Font MID_FONT = new Font("宋体", 1, 25);
    public static Font SMALL_FONT = new Font("宋体", 1, 17);
    public static Font TINY_FONT = new Font("宋体", 1, 12);

    /**
     * 设置画笔颜色
     * @param c 颜色
     * @param g 画笔
     * @return 画笔以前的颜色
     */
    public static void setColor(Color c, Graphics g) {

        g.setColor(c);
        
    }

    public static void setFont(Font f, Graphics g) {

        g.setFont(f);

    }

    /**
     * 三种绘制矩形的方法
     */
    public static void renderRect2D(int x1, int y1, int x2, int y2, Graphics g) {

        renderRect(x1, y1, x2 - x1, y2 - y1, g);

    }

    public static void renderRect(Rectangle rect, Graphics g) {

        renderRect(rect.getOffsetX(), rect.getOffsetY(), rect.getWidth(), rect.getHeight(), g);

    }

    public static void renderRect(int offsetX, int offsetY, int width, int height, Graphics g) {

        g.fillRect(offsetX, offsetY, width, height);

    }

    /**
     * 绘制方框的方法
     */
    public static void renderFrame(int x1, int y1, int x2, int y2, int width, Graphics g) {

        for(int i = 0; i < width; i++) {
            g.drawRoundRect(x1 - i, y1 - i, x2 - x1 + i * 2, y2 - y1 + i * 2, 0, 50);
        }

    }

    /**
     * 绘制圆形的方法
     */
    public static void renderCircle(int offsetX, int offsetY, int r, Graphics g) {

        g.fillOval(offsetX, offsetY, r * 2, r * 2);

    }

    public static void renderCircle(Circle c, Graphics g) {

        renderCircle(c.getOffsetX(), c.getOffsetY(), c.getRadius(), g);

    }

    /**
     * 绘制环形的方法
     */
    public static void renderRing(int x, int y, int r, int width, Graphics g) {

        for(int i = 0; i < width; i++) {
            g.drawOval(x - r - i, y - r - i, r * 2 + i * 2, r * 2 + i * 2);
        }

    }

    /**
     * 绘制文本的方法
     */
    public static void renderString(int x, int y, String str, Graphics g) {

        g.setColor(DEFAULT_COLOR_IN);
        g.setFont(DEFAULT_FONT);

        g.drawString(str, x, y);

    }

    public static void renderString(int x, int y, String str, Color c, Font f, Graphics g) {

        g.setColor(c);
        g.setFont(f);

        g.drawString(str, x, y);

    }

    public static void renderStringOutLine(int x, int y, String str, Color ci, Color co, Font f, Graphics g) {

        int width = 1;

        renderString(x - width, y - width, str, co, f, g);
        renderString(x - width, y + width, str, co, f, g);
        renderString(x - width, y, str, co, f, g);
        renderString(x + width, y - width, str, co, f, g);
        renderString(x + width, y + width, str, co, f, g);
        renderString(x + width, y, str, co, f, g);
        renderString(x, y - width, str, co, f, g);
        renderString(x, y + width, str, co, f, g);

        renderString(x, y, str, ci, f, g);

    }

    public static void renderStringOutLine(int x, int y, String str, Font define, Graphics g) {

        renderStringOutLine(x, y, str, DEFAULT_COLOR_IN, DEFAULT_COLOR_OUT, define, g);

    }

    public static BufferedImage rotate(BufferedImage img, double angle) {

        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage imgRotated = new BufferedImage(width, height, img.getTransparency());
        Graphics2D g = (Graphics2D) imgRotated.getGraphics();

        g.rotate(Maths.toRadians(angle), width / 2, height / 2);
        Graphic.render(0, 0, img, g);
        g.dispose();

        return imgRotated;

    }

}
