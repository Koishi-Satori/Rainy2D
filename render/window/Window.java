package rainy2D.render.window;

import rainy2D.render.screen.Screen;

import javax.swing.*;
import java.awt.*;

import static test.GameTest.xSize;
import static test.GameTest.ySize;

public class Window extends JFrame {

    int x;
    int y;
    int width;
    int height;

    Toolkit kit;
    Screen screenIn;

    boolean isFull;
    boolean isNoFrame;

    public Window(String title, Image icon, int width, int height) {

        this.kit = Toolkit.getDefaultToolkit();
        this.x = getX();
        this.y = getY();
        this.width = width;
        this.height = height;

        this.setLocation((xSize - width) / 2, (ySize - height) / 2);
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title);
        this.setIconImage(icon);
        this.setResizable(false);

    }

    public void fullFrame() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(this);
        this.isFull = true;

    }

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

}
