package rainy2D.section.stage;

import rainy2D.engine.Engine3D;
import rainy2D.render.desktop.Screen;
import rainy2D.render.element.Element;
import rainy2D.render.element.ElementBullet;
import rainy2D.render.element.ElementEnemy;
import rainy2D.render.element.ElementPlayer;
import rainy2D.shape.Direction;
import rainy2D.shape.Rectangle;
import rainy2D.util.MathData;

/**
 * 游戏大部分的操作包装在这个类中
 */
public class Stage {

    Screen screen;
    Rectangle field;

    public Stage(Screen screen) {

        this.screen = screen;
        this.field = screen.getField();

    }

    public ElementEnemy addEnemy(ElementEnemy e, double appearPercent, int direction) {

        ElementEnemy enemy = e.getClone();

        if(direction == Direction.LEFT) {
            enemy.setAngle(e.getAngle());
            enemy.locate(field.getX(), field.getPY(appearPercent));
        }
        else if(direction == Direction.RIGHT){
            enemy.setAngle(180 - e.getAngle());
            enemy.locate(field.getX2(), field.getPY(appearPercent));
        }
        else if(direction == Direction.UP || direction == Direction.DOWN) {
            enemy.setAngle(e.getAngle());
            enemy.locate(field.getPX(appearPercent), field.getY());
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

        ElementBullet bullet = this.screen.bulletCache.getClone(b);
        bullet.locate(e.getX(), e.getY());
        bullet.setAngle(angle);

        this.screen.add(bullet, screen.bullets);

        return bullet;

    }

    /**
     * 玩家攻击
     * @param e 玩家
     * @param b 子弹模板（切记要使用模板而不是new对象，否则会极大影响性能）
     * @return 返回发射出去的子弹，可以进一步操控
     */
    public ElementBullet shoot(ElementPlayer e, ElementBullet b) {

        ElementBullet bullet = screen.bulletCache.getClone(b);
        bullet.locate(e.getX(), e.getY());
        bullet.setAngle(-90 + MathData.random(-5, 5));

        this.screen.add(bullet, screen.bullets);

        return bullet;

    }

    public void backgroundImage3DRender(Element e, double speed, double percent, int startY) {

        Element bgImage;

        if(screen.imageBottom.size() < 500) {
            bgImage = e.getClone();
            this.screen.add(bgImage, screen.imageBottom);
            bgImage.locate(screen.randomPoint().x, startY);
        }

        for(int i = 0; i < screen.imageBottom.size(); i++) {
            bgImage = screen.imageBottom.get(i);

            Engine3D.locate3D(bgImage, speed, percent, startY);

            if(!bgImage.getRect().intersects(screen.getField()) && bgImage.getY() > 200) {
                this.screen.imageBottom.remove(bgImage);
            }
        }

    }

}
