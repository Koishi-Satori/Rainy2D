package rainy2D.render.window;

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

    //右下方检测误差值，用于防止玩家出界
    public static final int RIGHT_OFFSET = 17;
    public static final int DOWN_OFFSET = 38;

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

    }

    public void fullFrame() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(this);

    }

}
