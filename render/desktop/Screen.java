package rainy2D.render.desktop;

import rainy2D.util.Array;
import rainy2D.util.Input;
import rainy2D.util.MathData;
import rainy2D.util.WaitTimer;

import javax.swing.*;
import java.awt.*;

/**
 * 初始化之后add进window
 */
public class Screen extends JPanel implements Runnable {

    private int fpsTime;
    public double nowFps;

    public String titleName;

    public Canvas canvas;

    public Window window;
    public Input input;

    boolean isPause;

    public Array<WaitTimer> waitRequests = new Array<>(10);

    public Screen(Window window) {

        this.window = window;//获取所处的窗口，将自己传给窗口
        window.setScreenIn(this);

        input = new Input(window);

        setDefaultFps(60);

        new Thread(this).start();

    }

    /**
     * 设置刷新率（默认60）
     * @param fps 越大刷新越快，时间加速、缓慢也许可以用到
     */
    public void setDefaultFps(int fps) {

        fpsTime = MathData.round(1000.0 / fps);

    }

    public void setTitle(String title) {

        this.titleName = title;

    }

    /**
     * 设置背景颜色（RGB）
     */
    public void setColor(Color c) {

        setBackground(c);

    }

    public void tick() {

        for(int i = 0; i < waitRequests.size(); i++) {
            waitRequests.get(i).update();
        }

    }

    public void loadFromCanvas(Canvas canvas) {

        this.canvas = canvas;

    }

    public void paint(Graphics g) {

        tick();

        if(canvas != null) {
            canvas.paint(g);
        }

    }

    /**
     * 等待一段时间后不断触发isWaitBack方法
     * if(isWaitBack())
     * wait(50);
     * 这样可以做成定时触发
     */
    public void wait(int waitTime, int requestNum) {

        waitRequests.get(requestNum).wait(waitTime);

    }

    public boolean isWaitBack(int requestNum) {

        return waitRequests.get(requestNum).isWaitBack();

    }

    public Canvas getCanvas() {

        return canvas;

    }

    public void pause() {

        isPause = !isPause;

    }

    public void run() {

        int frame = 0;
        long timer = System.currentTimeMillis();
        long updateTime;
        long beforeTime;
        long nowTime;

        try {
            //绘制、FPS调控
            while(true) {

                beforeTime = System.nanoTime();

                //如果没有暂停，则进行游戏主逻辑
                if(!isPause) {
                    repaint();
                }
                frame++;

                nowTime = System.nanoTime();
                updateTime = (nowTime - beforeTime) / 1000000;

                Thread.sleep(Math.max(fpsTime - updateTime, fpsTime));

                //每秒得到fps
                if(System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    nowFps = frame + updateTime;
                    frame = 0;
                }

            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

    }

}
