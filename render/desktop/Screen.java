package rainy2D.render.desktop;

import rainy2D.util.Input;
import rainy2D.util.Maths;

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

    public Screen(Window win) {

        window = win;//获取所处的窗口，将自己传给窗口
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

        fpsTime = Maths.round(1000.0 / fps);

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

    public void tick() {}

    //**USED FOR LOADING NEW STAGE**//
    public void loadFromCanvas(Canvas load) {

        canvas = load;//加载画布
        canvas.setMainLoopMusic(canvas.audio);//播放下一个音乐
        canvas.unPause();//解除暂停
        window.update();

    }

    public Canvas getCanvas() {

        return canvas;

    }

    //**USE JAVA SWING**//
    //canvas.paint放进主循环fps就很低，只能放在这里了
    public void paint(Graphics g) {

        if(Maths.noNull(canvas)) {
            canvas.paint(g);
        }

    }

    //**USE JAVA SWING && THREAD**//
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
                repaint();
                frame++;

                nowTime = System.nanoTime();
                updateTime = (nowTime - beforeTime) / 1000000;

                Thread.sleep(java.lang.Math.max(fpsTime - updateTime, fpsTime));

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
