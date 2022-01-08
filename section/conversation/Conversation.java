package rainy2D.section.conversation;

import rainy2D.render.desktop.Screen;
import rainy2D.render.element.ElementImageInset;
import rainy2D.render.helper.RenderHelper;
import rainy2D.render.helper.ShapeHelper;
import rainy2D.shape.Rectangle;
import rainy2D.shape.Rectangle2D;

import java.awt.*;

public class Conversation {

    Screen screen;

    Rectangle field;
    Rectangle textIn;

    Graphics g;
    StringInfo style;

    ElementImageInset character;

    public Conversation(Screen screen) {

        this.screen = screen;
        this.field = screen.getField();
        this.textIn = new Rectangle2D(field.getXWith(0.05), field.getYWith(0.7), field.getXWith(0.9), field.getYWith(0.9));

    }

    /**
     * Conversation对象创建后先调用此方法初始化！
     * @param info 人物渲染信息
     * @param style 文字信息
     * @param g 画笔
     */
    public void createConversation(RenderInfo info, StringInfo style, Graphics g) {

        this.g = g;
        this.style = style;

        //生成对话人物
        this.character = new ElementImageInset(field.getXWith(0.15), field.getYWith(0.7), info.width, info.height, info.iml.get());

    }

    /**
     * 设置对话的文字并启动一个对话
     * @param str1 第一行
     * @param str2 第二行
     * @param str3 第三行
     */
    public void speak(String str1, String str2, String str3) {

        RenderHelper.render(character, g);

        ShapeHelper.setColor(new Color(0, 0, 0, 120), g);
        ShapeHelper.renderRect(textIn, g);

        ShapeHelper.setColor(style.color, g);
        ShapeHelper.renderString(textIn.getXWith(0.05), textIn.getYWith(0.25), style.font, str1, g);
        ShapeHelper.renderString(textIn.getXWith(0.05), textIn.getYWith(0.5), style.font, str2, g);
        ShapeHelper.renderString(textIn.getXWith(0.05), textIn.getYWith(0.75), style.font, str3, g);

    }

}
