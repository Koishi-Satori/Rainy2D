package rainy2D.render;

import rainy2D.render.element.Element;
import rainy2D.render.element.ElementBullet;
import rainy2D.resource.ImageLocation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class RenderHelper {

    //对象渲染(inset)
    public static void renderIn(Element e, Graphics g) {

        render((int) e.getOffsetX(), (int) e.getOffsetY(), e.getWidth(), e.getHeight(), e.getIml(), g);

    }

    //渲染(inset)
    public static void renderIn(int x, int y, int width, int height, ImageLocation iml, Graphics g) {

        render(x - width / 2, y - height / 2, width, height, iml, g);

    }

    public static void renderIn(int x, int y, int width, int height, Image im, Graphics g) {

        render(x - width / 2, y - height / 2, width, height, im, g);

    }

    //渲染(offset)
    public static void render(int offsetX, int offsetY, int width, int height, ImageLocation iml, Graphics g) {

        g.drawImage(iml.getImage(), offsetX, offsetY, width, height, null);

    }

    public static void render(int offsetX, int offsetY, int width, int height, Image im, Graphics g) {

        g.drawImage(im, offsetX, offsetY, width, height, null);

    }

    //背景渲染(offset)
    public static void render(int offsetX, int offsetY, ImageLocation iml, Graphics g) {

        render(offsetX, offsetY, iml.getWidth(), iml.getHeight(), iml, g);

    }

    public static void render(int offsetX, int offsetY, Image im, Graphics g) {

        render(offsetX, offsetY, im.getWidth(null), im.getHeight(null), im, g);

    }

}
