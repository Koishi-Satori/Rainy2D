package rainy2D.render.helper;

import rainy2D.render.desktop.Screen;
import rainy2D.render.element.ElementImageInset;
import rainy2D.resource.location.ImageLocation;
import rainy2D.resource.info.StringInfo;
import rainy2D.shape.Rectangle;
import rainy2D.shape.Rectangle2D;
import rainy2D.util.MathData;

import java.awt.*;

public class Conversation {

    Screen screen;

    Rectangle field;
    Rectangle textIn;

    Graphics g;
    StringInfo info;

    ElementImageInset characterA;
    ElementImageInset characterB;

    boolean isLeftSpeaking;

    public Conversation(Screen screen) {

        this.screen = screen;
        this.field = screen.getField();
        this.textIn = new Rectangle2D(field.getXWith(0.05), field.getYWith(0.7), field.getXWith(0.9), field.getYWith(0.9));

    }

    public void createConversation(ImageLocation characterA, ImageLocation characterB, StringInfo info, Graphics g) {

        this.g = g;
        this.info = info;

        this.characterA = new ElementImageInset(field.getXWith(0.15), field.getYWith(0.7), characterA);
        this.characterB = new ElementImageInset(field.getXWith(0.8), field.getYWith(0.7), characterB);
        this.setDefaultCharacterSize(450, 450);

    }

    /**
     * 设置人物图片大小，默认为450，需在createConversation后调用
     * 自动按比例计算宽度
     */
    public void setDefaultCharacterSize(int heightA, int heightB) {

        double overPercentA =  MathData.toDouble(heightA) / characterA.getHeight();
        int widthA = MathData.round(characterA.getWidth() * overPercentA);

        double overPercentB = MathData.toDouble(heightB) / characterB.getHeight();
        int widthB = MathData.round(characterB.getWidth() * overPercentB);

        this.characterA.setSize(widthA, heightA);
        this.characterB.setSize(widthB, heightB);

    }

    /**
     * 设置对话的文字并启动一个对话
     * @param str1 第一行
     * @param str2 第二行
     * @param str3 第三行
     */
    public void speak(String str1, String str2, String str3) {

        RenderHelper.render(characterA, g);
        RenderHelper.render(characterB, g);

        ShapeHelper.setColor(new Color(0, 0, 0, 120), g);
        ShapeHelper.renderRect(textIn, g);

        ShapeHelper.setColor(info.color, g);
        ShapeHelper.renderString(textIn.getXWith(0.05), textIn.getYWith(0.25), info.font, str1, g);
        ShapeHelper.renderString(textIn.getXWith(0.05), textIn.getYWith(0.5), info.font, str2, g);
        ShapeHelper.renderString(textIn.getXWith(0.05), textIn.getYWith(0.75), info.font, str3, g);

    }

}
