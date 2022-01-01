package rainy2D.render.element;

import rainy2D.render.RenderHelper;
import rainy2D.render.window.Window;
import rainy2D.resource.ImageLocation;
import rainy2D.shape.Circle;
import rainy2D.shape.Rect;
import rainy2D.util.MathData;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 构造器：inset
 * 超类：inset
 */
public class ElementPlayer extends ElementImageOffset {

    double speed;
    double speedQuick;
    double speedSlow;
    int health;
    int defence;
    int magicLevel;
    int bomb;

    boolean left;
    boolean right;
    boolean up;
    boolean down;
    boolean shoot;
    boolean shift;

    public ElementPlayer(double x, double y, ImageLocation iml) {

        super(x, y, 32, 48, iml);

        this.speed = 3.5;
        this.speedQuick = this.speed;
        this.speedSlow = 1;
        this.health = 3;
        this.defence = 0;
        this.magicLevel = 1500;
        this.bomb = 2;

    }

    @Override
    public void render(Graphics g) {

        super.render(g);

        if(shift) {
            RenderHelper.renderIn(MathData.toInt(x), MathData.toInt(y), 12, 12, new ImageLocation("img/plp.png"), g);
        }

    }

    @Override
    public void tick(Window window) {

        super.tick(window);

        if(left)
            this.walkLeft();
        if(right)
            this.walkRight();
        if(up)
            this.walkUp();
        if(down)
            this.walkDown();

        this.checkIfOutField(window.getScreenIn().getField());

    }

    public void checkIfOutField(Rect field) {

        if(offsetX < field.getX()) {
            this.locateOffset(field.getX(), offsetY);
        }

        if(offsetY < field.getY()) {
            this.locateOffset(offsetX, field.getY());
        }

        if(x + width / 2 > field.getX2()) {
            this.locate(field.getX2() - width / 2, y);
        }

        if(y + height / 2 > field.getY2()) {
            this.locate(x, field.getY2() - height / 2);
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
                        speed = speedSlow;
                        shift = true;
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
                        speed = speedQuick;
                        shift = false;
                        break;

                }

            }

        };

        window.addKeyListener(k);

    }

    public void walkLeft() {

        this.locate(x - speed, y);

    }

    public void walkRight() {

        this.locate(x + speed, y);

    }

    public void walkUp() {

        this.locate(x, y - speed);

    }

    public void walkDown() {

        this.locate(x, y + speed);

    }

    public boolean isShoot() {

        return shoot;

    }

    //返回判定点的circle
    @Override
    public Circle getCircle() {

        return new Circle(MathData.toInt(x), MathData.toInt(y), MathData.toInt(width / 8));

    }

}
