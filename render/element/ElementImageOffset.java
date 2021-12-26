package rainy2D.render.element;

import rainy2D.render.RenderHelper;
import rainy2D.render.window.Window;
import rainy2D.resource.ImageLocation;

import java.awt.*;

/**
 * 构造器：offset
 * 超类：inset
 */
public class ElementImageOffset extends Element {

    public ElementImageOffset(double offsetX, double offsetY, int width, int height, ImageLocation iml) {

        super(offsetX, offsetY, width, height, iml);

    }

    public ElementImageOffset(double offsetX, double offsetY, ImageLocation iml) {

        super(offsetX, offsetY, iml.getWidth(), iml.getHeight(), iml);

    }

}
