package rainy2D.section.conversation;

import java.awt.*;

public class StringInfo {

    public Font font;
    public Color color;

    public StringInfo(Font font, Color color) {

        this.font = font;
        this.color = color;

    }

    public StringInfo(String fontName, int fontSize, Color color) {

        this.font = new Font(fontName, 0, fontSize);
        this.color = color;

    }

}
