package rainy2D.element.action;

import rainy2D.element.image.ElementImageInset;
import rainy2D.render.desktop.Window;
import rainy2D.util.Input;
import rainy2D.util.MathData;

import java.awt.image.BufferedImage;

public class ElementButton extends ElementImageInset {

    int maxWidth;
    int maxHeight;
    int minWidth;
    int minHeight;

    public ElementButton(double x, double y, int width, int height, BufferedImage img) {

        super(x, y, width, height, img);

        maxWidth = MathData.round(width * 1.2);
        maxHeight = MathData.round(height * 1.2);
        minWidth = width;
        minHeight = height;

    }

    @Override
    public void tick(Window window) {

        if(isMouseHanging(window.getScreenIn().input)) {
            setSize(maxWidth, maxHeight);
        }
        else {
            setSize(minWidth, minHeight);
        }

    }

    public boolean isMouseHanging(Input input) {

        double mouseX = input.getMouseX();
        double mouseY = input.getMouseY();

        return mouseX >= offsetX && mouseY >= offsetY && mouseX <= offsetX + width && mouseY <= offsetY + height;

    }

    public boolean isMouseClicking(Input input) {

        return isMouseHanging(input) && input.isMouseClicking();

    }

}
