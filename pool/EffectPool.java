package rainy2D.pool;

import rainy2D.render.element.Element;
import rainy2D.render.element.ElementBullet;
import rainy2D.render.element.ElementEffect;
import rainy2D.resource.ImageLocation;

import java.util.ArrayList;

public class EffectPool {

    ArrayList<ElementEffect> elements = new ArrayList<>();

    public EffectPool(int size) {

        for(int i = 0; i < size; i++) {
            this.elements.add(new ElementEffect());
        }

    }

    public void reuse(ElementEffect e) {

        this.elements.add(e);

    }

    public ElementEffect get(double x, double y, ImageLocation iml) {

        return get(x, y, iml.getWidth(), iml.getHeight(), iml);

    }

    public ElementEffect get(Element shooter, int width, int height, ImageLocation iml) {

        return get(shooter.getX(), shooter.getY(), width, height, iml);

    }

    public ElementEffect get(double x, double y, int width, int height, ImageLocation iml) {

        ElementEffect e;

        for(int i = 0; i < elements.size(); i++) {
            e = elements.get(i);
            if(e != null) {
                e.takeOutWith(x, y, width, height, iml);
                e.callImageChange();
                this.elements.remove(i);
                return e;
            }
        }

        return null;

    }

}
