package rainy2D.render.screen;

import rainy2D.render.element.ElementImageOffset;
import rainy2D.render.window.Window;
import rainy2D.resource.ImageLocation;

public class LoadingScreen extends Screen {

    public LoadingScreen(Window window) {

        super(window);

    }

    @Override
    public void init() {

        super.init();

        this.add(new ElementImageOffset(0, 0, 900, 600, new ImageLocation("img/bg.png")), imageBottom);

    }

}
