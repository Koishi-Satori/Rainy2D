package rainy2D.render.element;

import rainy2D.resource.ImageLocation;

import java.awt.*;

public class ElementString extends Element {

    Color color;
    Font font;
    String str;

    public ElementString(double offsetX, double offsetY, Font font, Color color, String str) {

        super(offsetX, offsetY, 0, 0, null);

        this.str = str;
        this.font = font;
        this.color = color;

    }

    @Override
    public void render(Graphics g) {



    }

    public String getString() {

        return str;

    }

    public Font getFont() {

        return font;

    }

    public Color getColor() {

        return color;

    }

}
