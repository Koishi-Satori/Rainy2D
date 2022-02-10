package rainy2D.element.action;

import rainy2D.element.image.ElementImageOffset;
import rainy2D.render.graphic.Graphic;
import rainy2D.util.Maths;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ElementProgressBar extends ElementImageOffset {

    int value;
    int maxValue;

    public ElementProgressBar(double offsetX, double offsetY, int width, int height, int maxValue, BufferedImage img) {

        super(offsetX, offsetY, width, height, img);

        this.maxValue = maxValue;

    }

    public void render(Graphics g) {

        Graphic.render(offsetX, offsetY, Maths.toDouble(value) / maxValue * width, height, img, g);

    }

    public void setValue(int value) {

        this.value = value;

    }

}
