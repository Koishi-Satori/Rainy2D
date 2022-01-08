package rainy2D.render.helper;

import rainy2D.shape.Rectangle;

import java.awt.*;

public class ShapeHelper {

    /**
     * 设置画笔颜色
     * @param c 颜色
     * @param g 画笔
     * @return 画笔以前的颜色
     */
    public static Color setColor(Color c, Graphics g) {
        
        Color oldC = g.getColor();
        g.setColor(c);
        
        return oldC;
        
    }
    
    public static void renderRect2D(int x1, int y1, int x2, int y2, Graphics g) {

        renderRect(x1, y1, x2 - x1, y2 - y1, g);

    }

    public static void renderRect(Rectangle rect, Graphics g) {

        renderRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), g);

    }

    public static void renderRect(int offsetX, int offsetY, int width, int height, Graphics g) {

        g.fillRect(offsetX, offsetY, width, height);

    }

    public static void renderFrame(int x1, int y1, int x2, int y2, int width, Graphics g) {

        for(int i = 0; i < width; i++) {
            g.drawRoundRect(x1 + i, y1 + i, x2 - x1 - i * 2, y2 - y1 - i * 2, 0, 50);
        }

    }

    public static void renderCircle(int x, int y, int r, Graphics g) {

        g.fillOval(x - r, y - r, r * 2, r * 2);

    }

    public static void renderRing(int x, int y, int r, int width, Graphics g) {

        for(int i = 0; i < width; i++) {
            g.drawOval(x - r + i, y - r + i, r * 2 - i * 2, r * 2 - i * 2);
        }

    }

    public static void renderString(int x, int y, Font f, String str, Graphics g) {

        g.setFont(f);
        g.drawString(str, x, y);

    }

}
