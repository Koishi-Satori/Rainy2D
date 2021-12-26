package rainy2D.pool;

import rainy2D.render.element.Element;
import rainy2D.render.element.ElementBullet;
import rainy2D.resource.ImageLocation;

import java.util.ArrayList;
import java.util.LinkedList;

public class BulletPool {

    ArrayList<ElementBullet> elements = new ArrayList<>();

    public BulletPool(int size) {

        for(int i = 0; i < size; i++) {
            this.elements.add(new ElementBullet());
            this.elements.get(i).setIndexInPool(i);
            this.elements.get(i).setUsed(false);
        }

    }

    public void reuse(ElementBullet e) {

        this.elements.get(e.getIndexInPool()).setUsed(false);

    }

    public ElementBullet get(Element shooter, int width, int height, double speed, double angle, ImageLocation iml) {

        for(int i = 0; i < elements.size(); i++) {
            ElementBullet e = elements.get(i);
            if(!e.isUsed() && e != null) {
                e.setUsed(true);
                e.setOutWindow(false);
                e.takeOutWith(shooter.getX(), shooter.getY(), width, height, speed, angle, iml);
                return e;
            }
        }

        return null;

    }

    public int size() {

        return elements.size();

    }

}
