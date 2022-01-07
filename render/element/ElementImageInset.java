package rainy2D.render.element;

import rainy2D.resource.ImageLocation;

/**
 * 构造器：inset
 * 超类：offset
 * 非常重要的类，实现了以中心点来实现一切活动
 * 高级的移动类都继承于它
 */
public class ElementImageInset extends ElementImageOffset {

    public ElementImageInset(double x, double y, int width, int height, ImageLocation iml) {

        super(x - width / 2, y - height / 2, width, height, iml);

    }

    public ElementImageInset(double x, double y, ImageLocation iml) {

        super(x - iml.getWidth() / 2, y - iml.getHeight() / 2, iml.getWidth(), iml.getHeight(), iml);

    }

}
