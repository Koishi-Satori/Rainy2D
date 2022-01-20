package rainy2D.element;

import rainy2D.resource.ImageLocation;

import java.awt.image.BufferedImage;

/**
 * 构造器：offset
 * 超类：offset
 */
public class ElementImageOffset extends Element {

    public ElementImageOffset(double offsetX, double offsetY, int width, int height, BufferedImage img) {

        super(offsetX, offsetY, width, height, img);

    }

    public ElementImageOffset(double offsetX, double offsetY, int width, int height, ImageLocation iml) {

        super(offsetX, offsetY, width, height, iml);

    }

}
