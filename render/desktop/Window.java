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

        kit = Toolkit.getDefaultToolkit();
        xSize = kit.getScreenSize().width;
        ySize = kit.getScreenSize().height;

        x = (xSize - width) / 2;
        y = (ySize - height) / 2;

        this.width = width;
        this.height = height;

        setSize(width, height);
        setLocation(x, y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(title);
        setIconImage(icon);
        setResizable(false);

    }

    public void update() {

        setVisible(true);

    }

    /**
     * 全屏
     * 修了好久的bug
     */
    public void fullFrame() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(this);

        isFull = true;

        width = xSize;
        height = ySize;

        setVisible(true);

    }

    /**
     * 去除边框
     */
    public void deleteFrame() {

        setUndecorated(true);
        isNoFrame = true;

        update();

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
