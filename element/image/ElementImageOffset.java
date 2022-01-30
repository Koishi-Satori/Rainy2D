package rainy2D.element.image;

import rainy2D.element.Element;
import rainy2D.shape.Rectangle;

import java.awt.image.BufferedImage;

/**
 * 构造器：offset
 * 超类：offset
 */
public class ElementImageOffset extends Element {

    public ElementImageOffset(double offsetX, double offsetY, int width, int height, BufferedImage img) {

        super(offsetX, offsetY, width, height, img);

    }

    public ElementImageOffset(Rectangle rect, BufferedImage img) {

        super(rect.getOffsetX(), rect.getOffsetY(), rect.getWidth(), rect.getHeight(), img);

    }

}
