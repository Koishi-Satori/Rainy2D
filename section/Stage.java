package rainy2D.section;

import rainy2D.element.Element;
import rainy2D.element.vector.ElementBullet;
import rainy2D.element.vector.ElementEnemy;
import rainy2D.element.vector.ElementVector;
import rainy2D.render.desktop.Canvas;
import rainy2D.shape.Direction;
import rainy2D.shape.Rectangle;
import rainy2D.util.Maths;

import java.awt.image.BufferedImage;

/**
 * 游戏大部分的操作包装在这个类中
 * 注意！！！：此类一定要在setRepaintField之后实例化
 */
public class Stage {

    double rank = 1;
    public int nowStage = 1;

    public Canvas canvas;
    public Rectangle field;

    //field四边位置
    int left;
    int top;
    int right;
    int bottom;

    public Stage(Canvas c) {

        canvas = c;

        field = canvas.getField();

        left = field.getOffsetX();
        top = field.getOffsetY();
        right = field.getX2();
        bottom = field.getY2();

    }

    public void nextStage() {

        nowStage++;

    }

    //1 ~ N RANK
    public void translateRank(double r) {

        rank += r;

    }

    public double getRankedSpeed() {

        return rank / 10 + 1;

    }

    /**
     * 添加一个敌人
     * @param e 敌人模板
     * @param appearPercent 出现位置在field一条边上的百分比
     * @param direction 出现方向
     * @return 生成的新敌人
     */
    public ElementEnemy addEnemy(ElementEnemy e, double appearPercent, int direction) {

        ElementEnemy enemy = canvas.enemyCache.getClone(e);

        if(direction == Direction.LEFT) {
            enemy.setAngle(0);
            enemy.locate(left - enemy.getWidth() / 2, field.getY(appearPercent));
        }
        else if(direction == Direction.RIGHT) {
            enemy.setAngle(180);
            enemy.locate(right + enemy.getWidth() / 2, field.getY(appearPercent));
        }
        else if(direction == Direction.UP) {
            enemy.setAngle(90);
            enemy.locate(field.getX(appearPercent), top - enemy.getHeight() / 2);
        }
        else if(direction == Direction.DOWN) {
            enemy.setAngle(270);
            enemy.locate(field.getX(appearPercent), bottom + enemy.getHeight() / 2);
        }

        canvas.enemies.add(enemy);

        return enemy;

    }

    public void addEnemies(ElementEnemy e, double appearPercent, double percentIncrease, int direction, int number) {

        for(int i = 0; i < number; i++) {
            addEnemy(e,appearPercent + percentIncrease * i, direction);
        }

    }

    /**
     * 敌方攻击
     * @param e 遍历得到一个敌人
     * @param b 子弹模板（切记要使用模板而不是new对象，否则会极大影响性能）
     * @param angle 初始角度
     * @param canBeRotated 是否允许子弹的图片旋转
     * @return 返回发射出去的子弹，可以进一步操控
     */
    public ElementBullet enemyShoot(Element e, ElementBullet b, double angle, boolean canBeRotated) {

        //复制一份子弹并设置信息
        ElementBullet bullet = canvas.bulletCache.getClone(b);

        bullet.setSpeed(b.getSpeed() * getRankedSpeed());
        bullet.rotateState(canBeRotated, false);
        bullet.locate(e.getX(), e.getY());
        bullet.setAngle(angle);
        bullet.setState(ElementBullet.HIT_PLAYER);

        //添加
        canvas.bullets.add(bullet);

        return bullet;

    }

    public void enemyRingShoot(Element e, ElementBullet b, double angle, int value, boolean canBeRotated) {

        for(int i = 0; i < value; i++) {
            enemyShoot(e, b, 360.0 / value * i + angle, canBeRotated);
        }

    }

    /**
     * 检测一个敌人是否已经准备发射子弹
     * @param e 敌人模板（一般用于boss）
     * @return 是否已经存在集合里
     */
    public boolean isEnemyReady(ElementEnemy e) {

        return canvas.enemies.contains(e);

    }

    /**
     * 玩家攻击
     * @param e 发射对象
     * @param b 子弹模板（切记要使用模板而不是new对象，否则会极大影响性能）
     * @param canBeRotated 是否允许子弹的图片旋转
     * @return 返回发射出去的子弹，可以进一步操控
     */
    public void playerShoot(Element e, ElementBullet b, double angle, boolean canBeRotated) {

        ElementBullet bullet = canvas.bulletCache.getClone(b);

        bullet.rotateState(canBeRotated, false);
        bullet.locate(e.getX(), e.getY());
        bullet.setAngle(angle);
        bullet.setState(ElementBullet.HIT_ENEMY);

        canvas.bullets.add(bullet);

    }

    public void addEffect(double x, double y, double speed, int sizeLeast, BufferedImage img) {

        ElementVector effect = canvas.effectCache.get(x, y, sizeLeast, sizeLeast, speed, Maths.random(0, 360), img);
        effect.rotateState(true, true);
        canvas.effects.add(effect);

    }

}
