package rainy2D.render.desktop;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    int x;
    int y;
    int width;
    int height;

    int xSize;
    int ySize;

    Toolkit kit;
    Screen screenIn;

    boolean isFull;
    boolean isNoFrame;

    public Window(String title, Image icon, int width, int height) {

        this.kit = Toolkit.getDefaultToolkit();
        this.xSize = kit.getScreenSize().width;
        this.ySize = kit.getScreenSize().height;
        this.x = getX();
        this.y = getY();
        this.width = width;
        this.height = height;

        this.setSize(width, height);
        this.setLocation((xSize - width) / 2, (ySize - height) / 2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title);
        this.setIconImage(icon);
        this.setResizable(false);

    }

    /**
     * 全屏
     * 修了好久的bug
     */
    public void fullFrame() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(this);
        this.isFull = true;
        this.width = xSize;
        this.height = ySize;

    }

    /**
     * 去除边框
     */
    public void deleteFrame() {

        this.setUndecorated(true);
        this.isNoFrame = true;

    }

    public void setScreenIn(Screen screenIn) {

        this.screenIn = screenIn;

    }

    public Screen getScreenIn() {

        return screenIn;

    }

    public boolean isFull() {

        return isFull;

    }

    public boolean isNoFrame() {

        return isNoFrame;

    }

    public int getWidth() {

        return width;

    }

    public int getHeight() {

        return height;

    }

}
