package rainy2D.element.action;

import rainy2D.element.image.ElementImageOffset;
import rainy2D.render.desktop.Canvas;
import rainy2D.util.Input;
import rainy2D.util.Maths;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ElementButton extends ElementImageOffset {

    int maxWidth;
    int maxHeight;
    int minWidth;
    int minHeight;

    boolean hangingEffect = true;

    public ElementButton(double offsetX, double offsetY, int width, int height, BufferedImage img) {

        super(offsetX, offsetY, width, height, img);

        maxWidth = Maths.round(width * 1.2);
        maxHeight = Maths.round(height * 1.2);
        minWidth = width;
        minHeight = height;

    }

    public void tick(Canvas canvas) {

        if(hangingEffect) {
            if(isMouseHanging(canvas.getInput())) {
                setSize(maxWidth, maxHeight);
            } else {
                setSize(minWidth, minHeight);
            }
        }

    }

    //Button不受暂停影响
    public void update(Canvas canvas, Graphics g) {

        tick(canvas);
        render(g);
        callTimer();

    }

    public void noHangingEffect() {

        hangingEffect = false;

    }

    public void simulateMouseHanging() {

        setSize(maxWidth, maxHeight);

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
