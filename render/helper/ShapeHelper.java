package rainy2D.render.helper;

import java.awt.*;

public class ShapeHelper {

    public static void renderRect2D(int x1, int y1, int x2, int y2, Graphics g) {

        renderRect(x1, y1, x2 - x1, y2 - y1, g);

    }

    public static void renderRect(int offsetX, int offsetY, int width, int height, Graphics g) {

        g.fillRect(offsetX, offsetY, width, height);

    }

    public static void renderFrame(int x1, int y1, int x2, int y2, Graphics g) {

        g.drawRoundRect(x1, y1, x2 - x1, y2 - y1, 50, 50);

    }

    public static void renderCircle(int x, int y, int r, Graphics g) {

        g.fillOval(x - r, y - r, r * 2, r * 2);

    }

    public static void renderRing(int x, int y, int r, int width, Graphics g) {

        for(int i = 0; i < width; i++) {
            g.drawOval(x - r + i, y - r + i, r * 2 - i * 2, r * 2 - i * 2);
        }

    }

    public static void renderString(int x, int y, Font f, Color c, String str, Graphics g) {

        Color oldC = g.getColor();

        g.setColor(c);
        g.setFont(f);
        g.drawString(str, x, y);
        g.setColor(oldC);

    }

}
