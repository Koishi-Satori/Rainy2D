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
    Screen screen;

    int pressKeyCode;
    int releaseKeyCode;
    boolean[] keyDown = new boolean[120];

    int mouseCode;

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

                mouseCode = e.getButton();

            }

            public void mouseReleased(MouseEvent e) {

                mouseCode = -1;

            }

        };

        window.addKeyListener(key);
        window.addMouseListener(mouse);

        screen = window.getScreenIn();

    }

    public boolean isKeyDown(int keyCode) {

        return keyDown[keyCode];

    }

    public int getPressKeyCode() {

        return pressKeyCode;

    }

    public int getReleaseKeyCode() {

        return releaseKeyCode;

    }

    public double getMouseX() {

        return MouseInfo.getPointerInfo().getLocation().getX() - screen.window.getX();

    }

    public double getMouseY() {

        return MouseInfo.getPointerInfo().getLocation().getY() - screen.window.getY();

    }

    public int getMouseCode() {

        return mouseCode;

    }

    public boolean isMouseClicking() {

        return mouseCode != -1;

    }

}
