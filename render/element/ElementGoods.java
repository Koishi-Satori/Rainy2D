package rainy2D.render.element;

import rainy2D.render.desktop.Window;
import rainy2D.resource.location.ImageLocation;

public class ElementGoods extends ElementImageInset {

    public static final ImageLocation POWER = new ImageLocation("pu.png");

    public ElementGoods(Element shooter, int width, int height, ImageLocation iml) {

        super(shooter.x, shooter.y, width, height, iml);

    }

    @Override
    public void tick(Window window) {

        this.locate(x, y + 2);

    }

}
