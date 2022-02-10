package rainy2D.element.vector;

import rainy2D.render.desktop.Canvas;
import rainy2D.render.graphic.Graphic;
import rainy2D.render.graphic.Graphic2D;
import rainy2D.section.Attack;
import rainy2D.shape.Circle;
import rainy2D.util.Input;
import rainy2D.util.Maths;
import rainy2D.vector.R2DVector;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * 构造器：inset
 * 超类：inset
 */
public class ElementPlayer extends ElementVector {

    double speedQuick;
    double speedSlow;

    public boolean left;
    public boolean right;
    public boolean up;
    public boolean down;
    public boolean pause;

    boolean shoot;
    boolean slow;
    boolean quick;

    private BufferedImage playerPoint;

    private int BIGGEST_POINT_SIZE = 16;
    private int pointSize;
    private double pointAngle;

    Attack attack;

    public ElementPlayer(double x, double y, int width, int height, double speed, BufferedImage img, BufferedImage point) {

        super(x, y, width, height, speed, 0, img);
        
        speedQuick = speed * 1.6;
        speedSlow = speed * 0.45;
        playerPoint = point;

    }

    @Override
    public void render(Graphics g) {

        super.render(g);

        //改变pointAngle并渲染point
        pointAngle += 0.5;
        Graphic.renderIn(x, y, pointSize, pointSize, Graphic2D.rotate(playerPoint, pointAngle), g);

        //改变pointSize
        if(slow && pointSize < BIGGEST_POINT_SIZE) {
            pointSize++;
        }
        else if(pointSize > 0){
            pointSize--;
        }

    }

    @Override
    public void tick(Canvas canvas) {

        playerControl(canvas.getInput());
        checkAngle();

        if(up || left || down || right) {
            locate(R2DVector.vectorX(x, speed, angle), R2DVector.vectorY(y, speed, angle));
        }

        if(slow) {
            speed = speedSlow;
        }
        else if(quick) {
            speed = speedQuick;
        }
        else {
            speed = speedBackup;
        }

        checkOutField(canvas);

    }

    public void checkOutField(Canvas canvas) {

        if(offsetX < canvas.left) {
            locateOffset(canvas.left, offsetY);
        }

        if(offsetY < canvas.top) {
            locateOffset(offsetX, canvas.top);
        }

        if(offsetX + width > canvas.right) {
            locateOffset(canvas.right - width, offsetY);
        }

        if(offsetY + height > canvas.bottom) {
            locateOffset(offsetX, canvas.bottom - height);
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

    //**USE JAVA SWING**//
    public void playerControl(Input input) {

        left = input.isKeyDown(KeyEvent.VK_A);
        right = input.isKeyDown(KeyEvent.VK_D);
        up = input.isKeyDown(KeyEvent.VK_W);
        down = input.isKeyDown(KeyEvent.VK_S);
        slow = input.isKeyDown(KeyEvent.VK_SHIFT);
        shoot = input.isKeyDown(KeyEvent.VK_SPACE);
        quick = input.isKeyDown(KeyEvent.VK_CONTROL);
        pause = input.isKeyDown(KeyEvent.VK_ESCAPE);

    }

    public void attack() {

        if(Maths.noNull(attack)) {
            attack.tick(this);
        }

    }

    public void setAttack(Attack a) {

        attack = a;

    }

    public boolean isShoot() {

        return shoot;

    }

    /**
     * @return 判定点 返回判定半径为：1/10 width的圆形
     */
    public Circle getCircle() {

        circle.locate(Maths.round(x), Maths.round(y));
        circle.setSize(5, 5);
        return circle;

    }

    public Circle getGrazedCircle() {

        circle.locate(Maths.round(x), Maths.round(y));
        circle.setSize(50, 50);
        return circle;

    }

}
