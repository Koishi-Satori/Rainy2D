package rainy2D.section.conversation;

import rainy2D.render.desktop.Screen;
import rainy2D.render.element.ElementImageInset;
import rainy2D.render.helper.RenderHelper;
import rainy2D.render.helper.ShapeHelper;
import rainy2D.shape.Rectangle;
import rainy2D.util.MathData;

import java.awt.*;

public class Conversation {

    public static Color DEFAULT_COLOR = new Color(221, 221, 221);
    public static Font DEFAULT_FONT = new Font("微软雅黑", Font.BOLD, 30);

    Screen screen;

    Rectangle field;
    Rectangle textIn;

    String str1;
    String str2;
    String str3;

    ElementImageInset character;
    int width;
    int height;

    public Conversation(Screen screen) {

        this.screen = screen;
        this.field = screen.getField();
        this.textIn = new Rectangle(field.getPX(0.1), field.getPY(0.7), field.getPWidth(0.8), field.getPHeight(0.2)) ;

    }

    /**
     * Conversation对象创建后先调用此方法初始化！
     * @param character 对话讲话人物
     */
    public void createConversation(ElementImageInset character) {

        this.character = character;
        this.character.locate(field.getPX(0.15), field.getPY(0.7));
        this.sizeOption(character.getImage().getHeight());

    }

    public void sizeOption(double heightImage) {

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

    public void speak(Graphics g) {

        RenderHelper.render(character, g);

        ShapeHelper.setColor(new Color(0, 0, 0, 120), g);
        ShapeHelper.renderRect(textIn, g);

        ShapeHelper.setColor(DEFAULT_COLOR, g);
        ShapeHelper.renderString(textIn.getPX(0.05), textIn.getPY(0.25), DEFAULT_FONT, str1, g);
        ShapeHelper.renderString(textIn.getPX(0.05), textIn.getPY(0.5), DEFAULT_FONT, str2, g);
        ShapeHelper.renderString(textIn.getPX(0.05), textIn.getPY(0.75), DEFAULT_FONT, str3, g);

    }

}
