package rainy2D.element;

import rainy2D.render.desktop.Window;
import rainy2D.render.graphic.Graphic;
import rainy2D.render.graphic.Graphic2D;
import rainy2D.resource.ImageLocation;
import rainy2D.shape.Circle;
import rainy2D.shape.Rectangle;
import rainy2D.util.MathData;
import rainy2D.vector.R2DVector;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * 构造器：inset
 * 超类：inset
 */
public class ElementPlayer extends ElementVector {
    
    double speedBackup;
    double speedQuick;
    double speedSlow;

    public boolean left;
    public boolean right;
    public boolean up;
    public boolean down;

    boolean shoot;
    boolean slow;
    boolean run;

    private BufferedImage playerPoint = new ImageLocation("plp.png").get();

    private int BIGGEST_POINT_SIZE = 16;
    private int pointSize;
    private double pointAngle;

    public ElementPlayer(double x, double y, int width, int height, BufferedImage img) {

        super(x, y, width, height, 3.5, 0, img);
        
        this.speedQuick = 4.8;
        this.speedSlow = 0.8;
        this.speedBackup = speed;

    }

    @Override
    public void render(Graphics g) {

        super.render(g);

        //改变pointAngle并渲染point
        this.pointAngle += 0.5;
        Graphic.renderIn(x, y, pointSize, pointSize, Graphic2D.rotate(playerPoint, pointAngle), g);

        //改变pointSize
        if(slow && pointSize < BIGGEST_POINT_SIZE) {
            this.pointSize++;
        }
        else if(pointSize > 0){
            this.pointSize--;
        }

    }

    @Override
    public void tick(Window window) {

        this.checkAngle();
        if(up || left || down || right) {
            this.locate(R2DVector.vectorX(x, speed, angle), R2DVector.vectorY(y, speed, angle));
        }

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
    
    public void checkAngle() {
        
        if(up && right) {
            setAngle(315);
        }
        else if(up && left) {
            setAngle(225);
        }
        else if(down && left) {
            setAngle(135);
        }
        else if(down && right) {
            setAngle(45);
        }
        else if(up) {
            setAngle(270);
        }
        else if(left) {
            setAngle(180);
        }
        else if(down) {
            setAngle(90);
        }
        else if(right) {
            setAngle(0);
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
