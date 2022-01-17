package rainy2D.render.element;

import rainy2D.render.desktop.Window;
import rainy2D.render.helper.RenderHelper;
import rainy2D.resource.ImageLocation;
import rainy2D.shape.Circle;
import rainy2D.shape.Rectangle;
import rainy2D.util.MathData;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * 构造器：inset
 * 超类：inset
 */
public class ElementPlayer extends ElementImageOffset {

    double speed;
    double speedBackup;
    double speedQuick;
    double speedSlow;

    boolean left;
    boolean right;
    boolean up;
    boolean down;
    boolean shoot;
    boolean slow;
    boolean run;

    private final BufferedImage playerPoint = new ImageLocation("plp.png").get();

    public ElementPlayer(double x, double y, int width, int height, ImageLocation iml) {

        super(x, y, width, height, iml);

        this.speed = 2;
        this.speedQuick = 3.2;
        this.speedSlow = 0.7;
        this.speedBackup = this.speed;

    }

    @Override
    public void render(Graphics g) {

        super.render(g);

        if(slow) {
            RenderHelper.renderIn(x, y, 14, 14, playerPoint, g);
        }

    }

    @Override
    public void tick(Window window) {

        this.walkLeft();
        this.walkRight();
        this.walkUp();
        this.walkDown();

        if(slow) {
            this.speed = this.speedSlow;
        }
        else if(run) {
            this.speed = this.speedQuick;
        }
        else {
            this.speed = this.speedBackup;
        }

        this.checkOutField(window.getScreenIn().getField());

    }

    public void checkOutField(Rectangle field) {

        if(x < field.getX()) {
            this.locate(field.getX(), y);
        }

        if(y < field.getY()) {
            this.locate(x, field.getY());
        }

        if(x > field.getX2()) {
            this.locate(field.getX2(), y);
        }

        if(y > field.getY2()) {
            this.locate(x, field.getY2());
        }

    }

    public void playerControl(Window window) {

        KeyAdapter k = new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {

                    case KeyEvent.VK_A:
                        left = true;
                        break;
                    case KeyEvent.VK_D:
                        right = true;
                        break;
                    case KeyEvent.VK_W:
                        up = true;
                        break;
                    case KeyEvent.VK_S:
                        down = true;
                        break;
                    case KeyEvent.VK_SPACE:
                        shoot = true;
                        break;
                    case KeyEvent.VK_SHIFT:
                        slow = true;
                        break;
                    case KeyEvent.VK_CONTROL:
                        run = true;
                        break;

                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

                switch (e.getKeyCode()) {

                    case KeyEvent.VK_A:
                        left = false;
                        break;
                    case KeyEvent.VK_D:
                        right = false;
                        break;
                    case KeyEvent.VK_W:
                        up = false;
                        break;
                    case KeyEvent.VK_S:
                        down = false;
                        break;
                    case KeyEvent.VK_SPACE:
                        shoot = false;
                        break;
                    case KeyEvent.VK_SHIFT:
                        slow = false;
                        break;
                    case KeyEvent.VK_CONTROL:
                        run = false;
                        break;

                }

            }

        };

        window.addKeyListener(k);

    }

    public void walkLeft() {

        if(left) {
            this.locate(x - speed, y);
        }

    }

    public void walkRight() {

        if(right) {
            this.locate(x + speed, y);
        }

    }

    public void walkUp() {

        if(up) {
            this.locate(x, y - speed);
        }

    }

    public void walkDown() {

        if(down) {
            this.locate(x, y + speed);
        }

    }

    public boolean isShoot() {

        return shoot;

    }

    /**
     * @return 判定点 返回判定半径为：1/10 width的圆形
     */
    @Override
    public Circle getCircle() {

        return new Circle(MathData.round(x), MathData.round(y), MathData.round(width / 10.0));

    }

}
