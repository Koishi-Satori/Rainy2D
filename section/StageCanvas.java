package rainy2D.section;

import rainy2D.element.vector.ElementBoss;
import rainy2D.element.vector.ElementBullet;
import rainy2D.element.vector.ElementEnemy;
import rainy2D.element.vector.ElementVector;
import rainy2D.render.desktop.Canvas;
import rainy2D.render.desktop.Window;
import rainy2D.render.graphic.Graphic;
import rainy2D.render.graphic.Graphic2D;
import rainy2D.resource.image.AnimatedImage;
import rainy2D.shape.Circle;
import rainy2D.shape.Point;
import rainy2D.vector.R2DGravity;
import rainy2D.vector.R2DVector;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Stage和Canvas的集成类
 * 需要加载什么功能在复写的tick里调
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
                    if(e.attacks.size() == 0) {
                        enemyShootAction(e);//添加攻击
                    }
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
                tick(e, b, ec, bc);//遍历到的结果传入，复写此方法操作即可
            }
        }

    }

    public void tick(ElementEnemy e, ElementBullet b, Circle ec, Circle bc) {}

    public void enemyShootAction(ElementEnemy e) {}

    public boolean isConversationShowing;

    public void setConversationNow(Conversation c) {

        if(conv != c) {
            conv = c;
            clear(false);//如果对话未设置，则清除所有子弹准备开始
        }

    }

    //**PUT IN RENDER ABOVE**//
    public void conversationRender(int eachWait) {

        if(conv != null) {
            isConversationShowing = conv.isShowing;
            if(getInput().isKeyDown(KeyEvent.VK_Z) && isWaitBack(0) && conv.canBeRender() && !isPause) {
                conv.next();
                wait(eachWait, 0);
            }
            if(conv.canBeRender()) {
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
        else if(isWaitBack(0)) {//对话播放完毕开始攻击，每一阶段等待75ticks
            if(boss.getNumberOfAttacks() < boss.attacks.size() && !ca.isShowing()) {//攻击未完并且ca未开始就继续
                if(boss.isDead()) {//每阶段完毕回血，下一攻击
                    bossDie();//调用方法
                    clear(true);
                    boss.reHealth();
                    wait(75, 0);
                    boss.nextAttack();
                }//攻击tick
                else if(isWaitBack(0)) {
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

    public void bossDie() {}

    public void effectMove(ElementVector e) {

        if(e.getWidth() > 0) {
            e.setSize(e.getWidth() - 1, e.getHeight() - 1);
        }
        else {
            e.setSize(0, 0);
        }
        e.setAngle(e.getAngle() + 1);
        e.locate(R2DVector.vectorX(e.getX(), e.getSpeed(), e.getAngle()), R2DVector.vectorY(e.getY(), R2DGravity.gravityJump(e.getTimer(), 2), R2DGravity.ANGLE_G));

    }

    private boolean isBlackIn;
    private boolean isBlackOut;
    private int blackTimer;
    private StageCanvas nextCanvas;

    //**PUT IN RENDER ABOVE**//
    public void blackScreenLoad(BufferedImage imgStr) {

        if(isBlackIn || isBlackOut) {
            Graphic2D.setColor(new Color(0, 0, 0, blackTimer), getGraphicsBuffer());//渲染黑屏
            Graphic2D.renderRect(0, 0, WI_WIDTH, WI_HEIGHT, getGraphicsBuffer());

            Graphic.render(900, 650, imgStr, getGraphicsBuffer());

            if(isBlackIn) {
                blackTimer += 5;//如果是进入黑屏，提高不透明度
                if(blackTimer == 255) {
                    isBlackIn = false;
                    screen.loadFromCanvas(nextCanvas);//加载下一个画布
                    nextCanvas.blackOut();
                }
            }
            else if(isBlackOut) {
                blackTimer -= 5;
                if(blackTimer <= 0) {
                    isBlackOut = false;
                }
            }
        }

    }

    public void blackIn(StageCanvas next) {

        nextCanvas = next;
        blackTimer = 0;

        isBlackIn = true;
        isBlackOut = false;

    }

    public void blackOut() {

        blackTimer = 255;

        isBlackOut = true;
        isBlackIn = false;

    }

}
