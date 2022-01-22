package rainy2D.section.stg;

import rainy2D.element.ElementBullet;
import rainy2D.element.ElementEnemy;
import rainy2D.element.ElementPlayer;
import rainy2D.render.desktop.ScreenSTG;
import rainy2D.shape.Direction;
import rainy2D.shape.Rectangle;
import rainy2D.util.MathData;

/**
 * 游戏大部分的操作包装在这个类中
 */
public class Stage {

    ScreenSTG screen;
    Rectangle field;

    public Stage(ScreenSTG screen) {

        this.screen = screen;
        this.field = screen.getField();

    }

    /**
     * 添加一个敌人
     * @param e 敌人模板
     * @param appearPercent 出现位置在field一条边上的百分比
     * @param direction 出现方向
     * @return 生成的新敌人
     */
    public ElementEnemy addEnemy(ElementEnemy e, double appearPercent, int direction) {

        ElementEnemy enemy = new ElementEnemy(0, 0, e.getWidth(), e.getHeight(), e.getSpeed(), 0, e.getImage());

        if(direction == Direction.LEFT) {
            enemy.setAngle(e.getAngle());
            enemy.locate(field.getX(), field.getY(appearPercent));
        }
        else if(direction == Direction.RIGHT){
            enemy.setAngle(180 - e.getAngle());
            enemy.locate(field.getX2(), field.getY(appearPercent));
        }
        else if(direction == Direction.UP || direction == Direction.DOWN) {
            enemy.setAngle(e.getAngle());
            enemy.locate(field.getX(appearPercent), field.getY());
        }

        this.screen.add(enemy, screen.enemies);

        return enemy;

    }

    /**
     * 敌方攻击
     * @param e 遍历得到一个敌人
     * @param b 子弹模板（切记要使用模板而不是new对象，否则会极大影响性能）
     * @param angle 初始角度
     * @return 返回发射出去的子弹，可以进一步操控
     */
    public ElementBullet enemyShoot(ElementEnemy e, ElementBullet b, double angle) {

        //复制一份子弹并设置信息
        ElementBullet bullet = this.screen.bulletCache.getClone(b);

        bullet.locate(e.getX(), e.getY());
        bullet.setAngle(angle);
        bullet.setState(ElementBullet.HIT_PLAYER);

        //添加
        this.screen.add(bullet, screen.bullets);

        return bullet;

    }

    public void enemyRingShoot(ElementEnemy e, ElementBullet b, int value) {

        for(int i = 0; i < value; i++) {
            this.enemyShoot(e, b, 360.0 / value * i);
        }

    }

    /**
     * 检测一个敌人是否已经准备发射子弹
     * @param e 敌人模板（一般用于boss）
     * @return 是否已经存在集合里
     */
    public boolean isEnemyReady(ElementEnemy e) {

        return screen.enemies.contains(e);

    }

    /**
     * 玩家攻击
     * @param e 玩家
     * @param b 子弹模板（切记要使用模板而不是new对象，否则会极大影响性能）
     * @return 返回发射出去的子弹，可以进一步操控
     */
    public ElementBullet shoot(ElementPlayer e, ElementBullet b) {

        ElementBullet bullet = screen.bulletCache.getClone(b);

        bullet.locate(e.getX(), e.getY() - 12);
        bullet.setAngle(-90 + MathData.random(-5, 5));//略微散开的子弹
        bullet.setState(ElementBullet.HIT_ENEMY);

        this.screen.add(bullet, screen.bullets);

        return bullet;

    }

}
