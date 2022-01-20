package rainy2D.section.conversation;

import rainy2D.render.desktop.Screen;
import rainy2D.element.ElementImageInset;
import rainy2D.render.graphic.Graphic2D;
import rainy2D.render.graphic.Graphic;
import rainy2D.shape.Rectangle;
import rainy2D.util.MathData;

import java.awt.*;

public class Conversation {

    public static Color DEFAULT_COLOR = new Color(221, 221, 221);
    public static Color DEFAULT_BG_COLOR = new Color(0, 0, 0, 120);
    public static Font DEFAULT_FONT = new Font("微软雅黑", Font.BOLD, 30);

    Screen screen;

    Rectangle field;
    Rectangle textIn;

    String str1;
    String str2;
    String str3;

    ElementImageInset character;

    public Conversation(Screen screen) {

        this.screen = screen;
        this.field = screen.getField();
        this.textIn = new Rectangle(field.getX(0.1), field.getY(0.7), field.getWidth(0.8), field.getHeight(0.2)) ;

    }

    /**
     * Conversation对象创建后先调用此方法初始化！
     * @param character 对话讲话人物
     */
    public void createConversation(ElementImageInset character) {

        this.character = character;
        this.character.locate(field.getX(0.15), field.getY(0.7));
        this.sizeOption(character.getImage().getHeight());

    }

    private void sizeOption(double heightImage) {

        double percent = this.character.getHeight() / heightImage;
        this.character.setSize(MathData.round(character.getImage().getWidth() * percent), character.getHeight());

    }

    /**
     * 设置对话的文字
     * @param str1 第一行
     * @param str2 第二行
     * @param str3 第三行
     */
    public void change(String str1, String str2, String str3) {

        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;

    }

    /**
     * 渲染对话，每刻调用
     * @param g 画笔
     */
    public void speak(Graphics g) {

        Graphic.render(character, g);

        Graphic2D.setColor(DEFAULT_BG_COLOR, g);
        Graphic2D.renderRect(textIn, g);

        Graphic2D.setColor(DEFAULT_COLOR, g);
        Graphic2D.setFont(DEFAULT_FONT, g);
        Graphic2D.renderString(textIn.getX(0.05), textIn.getY(0.25), str1, g);
        Graphic2D.renderString(textIn.getX(0.05), textIn.getY(0.5), str2, g);
        Graphic2D.renderString(textIn.getX(0.05), textIn.getY(0.75), str3, g);

    }

}
