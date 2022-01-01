package rainy2D.render;

import rainy2D.render.element.ElementString;
import rainy2D.util.MathData;

import java.awt.*;

public class ShapeHelper {

    public static void renderRect2D(int x1, int y1, int x2, int y2, Graphics g) {

        renderRect(x1, y1, x2 - x1, y2 - y1, g);

    }

    public static void renderRect(int x1, int y1, int width, int height, Graphics g) {

        g.fillRect(x1, y1, width, height);

    }

    public static void renderFrame(int x1, int y1, int x2, int y2, Graphics g) {

        g.drawRoundRect(x1, y1, x2 - x1, y2 - y1, 50, 50);

    }

    public static void renderString(ElementString e, Graphics g) {

        Color c = g.getColor();
        Font f = g.getFont();
        g.setColor(e.getColor());
        g.setFont(e.getFont());
        g.drawString(e.getString(), MathData.toInt(e.getOffsetX()), MathData.toInt(e.getOffsetY()));
        g.setColor(c);
        g.setFont(f);

    }

}
