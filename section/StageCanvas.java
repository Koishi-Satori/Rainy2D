package rainy2D.section;

import rainy2D.element.vector.ElementBoss;
import rainy2D.element.vector.ElementBullet;
import rainy2D.element.vector.ElementEnemy;
import rainy2D.element.vector.ElementVector;
import rainy2D.render.desktop.Canvas;
import rainy2D.render.desktop.Window;
import rainy2D.resource.AnimatedImage;
import rainy2D.shape.Circle;
import rainy2D.shape.Point;
import rainy2D.vector.R2DGravity;
import rainy2D.vector.R2DVector;

import java.awt.event.KeyEvent;

/**
 * Stage和Canvas的集成类
 */
public class StageCanvas extends Canvas {

    public Stage stage;
    public Conversation conv;

    boolean clearBullet;
    boolean withQuake;
    public AnimatedImage bulletEffect;//继承后加图！

    public StageCanvas(Window window) {

        super(window);

        stage = new Stage(this);

    }

    public void bulletEnemyTick() {

        int sizeE = enemies.size();
        int sizeB = bullets.size();
        ElementEnemy e;
        ElementBullet b;
        Circle ec;
        Circle bc;

        //双遍历进行基本碰撞逻辑
        for(int i = 0; i < sizeE; i++) {
            if(enemies.get(i) != null) {
                e = enemies.get(i);
                ec = e.getCircle();
                if(!e.isBoss()) {
                    enemyShootAction(e);//添加攻击
                    if(e.attacks.get(0) != null) {
                        e.attacks.get(0).tick(e);//运行攻击
                    }
                }
            }
            else {
                continue;//为空直接进行下一次循环
            }
            for(int j = 0; j < sizeB; j++) {
                if(bullets.get(j) != null) {
                    b = bullets.get(j);
                    bc = b.getCircle();
                }
                else {
                    continue;
                }
                if(ec.intersects(bc) && b.getState() == ElementBullet.HIT_ENEMY) {
                    e.hit(b.getForce());
                    if(e.isDead() && !e.isBoss()) {//检测碰撞移除敌人和子弹
                        enemies.remove(i);
                        bullets.remove(j);
                    }
                }
                tickAction(e, b);//遍历到的结果传入，复写此方法操作即可
            }
        }

    }

    public void tickAction(ElementEnemy e, ElementBullet b) {}

    public void enemyShootAction(ElementEnemy e) {}

    public boolean isConversationShowing;

    public void setConversationNow(Conversation c) {

        if(conv != c) {
            conv = c;
            clear(false);//如果对话未设置，则清除所有子弹准备开始
        }

    }

    public void conversationTick(int eachWait) {

        isConversationShowing = false;
        if(conv != null) {
            if(getInput().isKeyDown(KeyEvent.VK_Z) && screen.isWaitBack(0) && conv.canBeRender()) {
                conv.next();
                screen.wait(eachWait, 0);
            }
            if(conv.canBeRender()) {
                isConversationShowing = true;
                conv.speak(getGraphicsBuffer());
                inConversationAction();
            }
        }

    }

    public void inConversationAction() {}

    public void bossTick(ElementBoss boss, Conversation cb, Conversation ca, Point pb, Point pa) {

        if(cb.canBeRender()) {
            setConversationNow(cb);
            boss.moveTo(pb.getX(), pb.getY());//对话未完，boss不开始攻击，并且来到pb
        }
        else if(screen.isWaitBack(0)) {//对话播放完毕开始攻击，每一阶段等待75ticks
            if(boss.attacks.get(boss.getNumberOfAttacks()) != null && !ca.isShowing()) {//攻击未完并且ca未开始就继续
                if(boss.isDead()) {//每阶段完毕回血，下一攻击
                    clear(true);
                    boss.reHealth();
                    screen.wait(75, 0);
                    boss.nextAttack();
                }//攻击tick
                else if(screen.isWaitBack(0)) {
                    boss.attacks.get(boss.getNumberOfAttacks()).tick(boss);
                }
            }
            else {//攻击完毕打开ca对话，boss逃跑
                boss.setHealth(0);
                setConversationNow(ca);//最后的对话
                if(!ca.canBeRender()) {
                    boss.moveTo(pa.getX(), pa.getY());//逃跑
                    stage.nextStage();
                }
            }

        }

    }

    public void clear(boolean quake) {

        clearBullet = true;
        withQuake = quake;

    }

    public void clearTick() {

        if(clearBullet) {
            if(withQuake) {
                earthQuake(5);
            }
            bulletEffect.tick();

            for(int q = 0; q < bullets.size(); q++) {
                bullets.get(q).setImage(bulletEffect.getNowImage());
            }

            if(bulletEffect.isLastImage()) {
                clearBullet = false;
                bulletEffect.reset();
                bullets.clear();
                resetLocation();
            }
        }

    }

    public void playerDie() {}

    public void effectTick(int sizeDown, double turnSpeed, int weight) {

        ElementVector e;
        int sizeE = effects.size();

        for(int i = 0; i < sizeE; i++) {
            if(effects.get(i) != null) {
                e = effects.get(i);
                e.setSize(e.getWidth() - sizeDown, e.getHeight() - sizeDown);
                if(e.getWidth() <= 0) {
                    effects.remove(i);
                    sizeE--;
                }
                e.setAngle(e.getAngle() + turnSpeed);
                e.locate(R2DVector.vectorX(e.getX(), e.getSpeed(), e.getAngle()),
                        R2DVector.vectorY(e.getY(), R2DGravity.gravityJump(e.getTimer(), 100 / weight), R2DGravity.ANGLE_G));
            }
        }

    }

}
