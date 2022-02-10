package rainy2D.element.action;

import rainy2D.element.Element;
import rainy2D.element.image.ElementImageOffset;
import rainy2D.render.desktop.Canvas;
import rainy2D.render.graphic.Graphic;
import rainy2D.util.Array;
import rainy2D.util.Maths;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ElementInventory extends ElementImageOffset {

    Array<ElementButton> items = new Array<>(10);

    int maxCap;
    double eachWidth;
    double swingAngle;

    Element usedItem;
    boolean isItemUsed;

    public ElementInventory(int x, int y, int width, int height, int cap, BufferedImage img) {

        super(x, y, width, height, img);

        maxCap = cap;
        eachWidth = width / maxCap;

    }

    public void render(Graphics g) {

        ElementButton item;

        for(int i = 0; i < items.size(); i++) {
            item = items.get(i);
            Graphic.renderRotate(item, swingAngle, g);
        }

    }

    public void tick(Canvas canvas) {

        ElementButton item;

        for(int i = 0; i < items.size(); i++) {
            item = items.get(i);
            item.tick(canvas);

            if(item.isMouseClicking(canvas.getInput()) && waiter.isWaitBack()) {
                usedItem = item;
                items.remove(i);
                checkItemLocation();
                waiter.wait(20);
                isItemUsed = false;
            }
        }

        swingAngle = canvas.getCycle() * 10;

    }

    public void storage(Element item) {

        if(items.size() < maxCap) {
            ElementButton itemIn = new ElementButton(offsetX, offsetY, item.getWidth(), item.getHeight(), item.getImage());
            items.add(itemIn);
            checkItemLocation();
        }

    }

    public void checkItemLocation() {

        ElementButton item;

        for(int i = 0; i < items.size(); i++) {
            item = items.get(i);
            item.locateOffset(items.indexOf(item) * eachWidth + offsetX, item.getOffsetY());
        }

    }

    public boolean isItemUsed(Element item) {

        if(Maths.noNull(usedItem) && !isItemUsed) {
            boolean same = usedItem.getImage() == item.getImage();
            if(same) {
                isItemUsed = true;
            }
            return same;
        }

        return false;

    }

}
