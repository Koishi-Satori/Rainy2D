package rainy2D.section;

import rainy2D.element.Element;
import rainy2D.render.desktop.Canvas;

/**
 * 使用本类时先继承出MyAttack，构造函数传入自己的canvas，然后再继承，就能使用自己的资源了
 */
public class Attack {

    public Stage s;
    public Canvas c;

    public Attack(Canvas canvas) {

        s = new Stage(canvas);
        c = canvas;

    }

    public void tick(Element e) {}

}
