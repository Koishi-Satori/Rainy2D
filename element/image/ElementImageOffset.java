package rainy2D.element.image;

import rainy2D.element.Element;

import java.awt.image.BufferedImage;

/**
 * 构造器：offset
 * 超类：offset
 */
public class ElementImageOffset extends Element {

    public ElementImageOffset(double offsetX, double offsetY, int width, int height, BufferedImage img) {

        super(offsetX, offsetY, width, height, img);

    }

    public ElementImageOffset(int width, int height, BufferedImage img) {

        super(0, 0, width, height, img);

    }

}
