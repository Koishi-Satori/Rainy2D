package rainy2D.util;

import rainy2D.render.desktop.Screen;
import rainy2D.render.desktop.Window;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Input {

    public static final int MOUSE_LEFT = 1;
    public static final int MOUSE_RIGHT = 2;

    MouseAdapter mouse;
    KeyAdapter key;

    Window window;
    Screen screen;

    int pressKeyCode;
    int releaseKeyCode;
    int pressMouseCode;
    int releaseMouseCode;
    boolean[] keyDown = new boolean[1000];
    boolean[] mouseDown = new boolean[2];//左键1 右键2

    public Input(Window window) {

        key = new KeyAdapter() {

            public void keyPressed(KeyEvent e) {

                pressKeyCode = e.getKeyCode();
                keyDown[pressKeyCode] = true;

            }

            public void keyReleased(KeyEvent e) {

                releaseKeyCode = e.getKeyCode();
                keyDown[releaseKeyCode] = false;

            }

        };
        mouse = new MouseAdapter() {

            public void mousePressed(MouseEvent e) {

                pressMouseCode = e.getButton();

                if(pressMouseCode <= 2)
                mouseDown[pressMouseCode] = true;

            }

            public void mouseReleased(MouseEvent e) {

                releaseMouseCode = e.getButton();

                if(releaseMouseCode <= 2)
                mouseDown[releaseMouseCode] = false;

            }

        };

        window.addKeyListener(key);
        window.addMouseListener(mouse);

        screen = window.getScreenIn();

        this.window = window;

    }

    public boolean isKeyDown(int keyCode) {

        return keyDown[keyCode];

    }

    public int getMouseX() {

        return getFullMouseX() - window.getX() - screen.getCanvas().SC_LEFT;

    }

    public int getMouseY() {

        return getFullMouseY() - window.getY() - screen.getCanvas().SC_TOP;

    }

    public int getFullMouseX() {

        return MouseInfo.getPointerInfo().getLocation().x;

    }

    public int getFullMouseY() {

        return MouseInfo.getPointerInfo().getLocation().y;

    }

    public boolean isMouseClicking() {

        return mouseDown[MOUSE_LEFT] == true;

    }

}
