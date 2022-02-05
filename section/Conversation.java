package rainy2D.section;

import rainy2D.element.image.ElementImageInset;
import rainy2D.render.desktop.Canvas;
import rainy2D.render.graphic.Graphic;
import rainy2D.render.graphic.Graphic2D;
import rainy2D.resource.StringLocation;
import rainy2D.shape.Direction;
import rainy2D.shape.Rectangle;
import rainy2D.shape.Rectangle2D;
import rainy2D.util.Array;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Conversation {

    public static Color DEFAULT_COLOR = new Color(221, 221, 221);
    public static Color DEFAULT_BG_COLOR = new Color(0, 0, 0, 120);
    public static Font DEFAULT_FONT = new Font("", 0, 25);

    Canvas canvas;
    Rectangle field;
    Rectangle2D textIn;

    ElementImageInset characterA;
    ElementImageInset characterB;

    int directionSpeak;
    int textIndex;

    boolean isShowing;

    public Array<String> texts = new Array<>(100);

    public Conversation(Canvas canvas) {

        this.canvas = canvas;

        field = this.canvas.getField();
        textIn = new Rectangle2D(field.getX(0.05), field.getY(0.85), field.getX(0.95), field.getY(0.85)) ;

    }

    public void readFromTxt(StringLocation stl, String charset) {

        texts = stl.get(charset);

    }

    /**
     * Conversation对象创建后先调用此方法初始化！
     */
    public void createConversation(ElementImageInset left, ElementImageInset right) {

        characterA = left;
        characterB = right;
        //定位在屏幕外
        characterA.locate(field.getOffsetX() - characterA.getWidth() / 2, field.getY(0.7));
        characterB.locate(field.getX2() + characterB.getWidth() / 2, field.getY(0.7));

    }

    public void change(int directionSpeak, BufferedImage img) {

        if(directionSpeak == Direction.LEFT) {
            characterA.setImage(img);
        }
        else if(directionSpeak == Direction.RIGHT) {
            characterB.setImage(img);
        }

    }

    public void next() {

        textIndex++;
        directionSpeak = Direction.opposite(directionSpeak);

    }

    public int getTextIndex() {

        return textIndex;

    }

    public int getIndexOfTexts() {

        return texts.size() / 2 - 1; //[0~n]（每两个string为一个text）

    }

    /**
     * 令人疑惑的是，这方法会多返回一个值--这是为了让文本框最后有淡出的时间
     * @return 是否可以渲染
     */
    public boolean canBeRender() {

        return textIndex <= getIndexOfTexts() + 1;

    }

    private boolean hasText() {

        return textIndex <= getIndexOfTexts();

    }

    public boolean isShowing() {

        return isShowing;

    }

    private void effectBuffer() {

        double ax = characterA.getX();
        double bx = characterB.getX();

        //如果人物未出现，则让人物进入屏幕
        if(ax < field.getX(0.1) && bx > field.getX(0.9) && hasText()) {
            characterA.locate(ax + 6, characterA.getY());
            characterB.locate(bx - 6, characterB.getY());
            //文本框缓慢出现
            if(textIn.getY2() < field.getY(0.95)) {
                textIn.setSize(textIn.getWidth(), textIn.getHeight() + 2);
            }
        }
        //如果没有文本了，让人物离开屏幕
        if(!hasText()) {
            characterA.locate(ax - 6, characterA.getY());
            characterB.locate(bx + 6, characterB.getY());
            //同时文本框缓慢消失
            if(textIn.getHeight() > 0) {
                textIn.setSize(textIn.getWidth(), textIn.getHeight() - 2);
            }
            else {
                next();
                isShowing = false;
            }
        }

    }

    /**
     * 渲染对话，每刻调用
     * @param g 画笔
     */
    public void speak(Graphics g) {

        isShowing = true;
        effectBuffer();

        Graphic.render(characterA, g);
        Graphic.render(characterB, g);

        Graphic2D.setColor(DEFAULT_BG_COLOR, g);
        Graphic2D.renderRect(textIn, g);

        Graphic2D.setColor(DEFAULT_COLOR, g);
        Graphic2D.setFont(DEFAULT_FONT, g);

        if(textIn.getHeight() > DEFAULT_FONT.getSize() && hasText()) {
            Graphic2D.renderString(textIn.getX(0.1), textIn.getY(0.4), texts.get(textIndex * 2), g);
            Graphic2D.renderString(textIn.getX(0.1), textIn.getY(0.8), texts.get(textIndex * 2 + 1), g);
        }

    }

}
