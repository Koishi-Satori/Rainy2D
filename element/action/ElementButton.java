package rainy2D.element.action;

import rainy2D.element.image.ElementImageInset;
import rainy2D.render.desktop.Screen;
import rainy2D.render.desktop.Window;
import rainy2D.util.Action;

import java.awt.image.BufferedImage;

public class ElementButton extends ElementImageInset {

    Action action;
    BufferedImage imgHanging;

    public ElementButton(double x, double y, int width, int height, BufferedImage img, BufferedImage imgHanging) {

        super(x, y, width, height, img);

        this.imgHanging = imgHanging;

    }

    @Override
    public void tick(Window window) {

        if(isMouseHanging(window.getScreenIn())) {
            setImage(imgHanging);
        }
        else {
            setImage(imgBackup);
        }

    }

    public boolean isMouseHanging(Screen screen) {

        double mouseX = screen.input.getMouseX();
        double mouseY = screen.input.getMouseY();

        return mouseX >= offsetX && mouseY >= offsetY && mouseX <= offsetX + width && mouseY <= offsetY + height;

    }

    public boolean isMouseClicking(Screen screen) {

        return isMouseHanging(screen) && screen.input.isMouseClicking();

    }

}
