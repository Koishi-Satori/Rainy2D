package rainy2D.render.element;

import rainy2D.render.RenderHelper;
import rainy2D.render.window.Window;
import rainy2D.resource.ImageLocation;

import java.awt.*;

public class ElementEffect extends ElementImageInset {

    int state;

    //EffectPool用构造器
    public ElementEffect() {

        super(0, 0, 0, 0, null);

    }

    public ElementEffect(Element shooter, int width, int height, ImageLocation iml) {

        super(shooter.x, shooter.y, width, height, iml);

    }

    @Override
    public void tick(Window window) {

        this.state++;
        if(state > 4) {
            state = 4;
        }

        this.setImageLocation(new ImageLocation("bo0" + state + ".png"));

    }

    public void takeOutWith(double x, double y, int width, int height, ImageLocation iml) {

        this.width = width;
        this.height = height;
        this.iml = iml;
        this.callImageChange();
        this.x = x;
        this.y = y;

    }

    public boolean isFinished() {

        return state == 4;

    }

}
