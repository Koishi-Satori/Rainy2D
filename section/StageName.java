package rainy2D.section;

import rainy2D.element.image.ElementImageInset;
import rainy2D.render.graphic.Graphic;
import rainy2D.shape.Rectangle;
import rainy2D.util.Maths;

import java.awt.*;

public class StageName {

    ElementImageInset stageName;
    int bufferHeight;

    int startTime;
    int endTime;
    int effectTime;

    public StageName(ElementImageInset stn, Rectangle field, int start, int end, int effect) {

        stageName = stn;
        bufferHeight = stn.getHeight();
        startTime = start;
        endTime = end;
        effectTime = effect;

        stageName.locate(field.getX(), field.getY(0.35));

    }

    public void render(double timer, Graphics g) {

        if(timer > startTime && timer < endTime) {
            int height = bufferHeight;

            if(timer < startTime + effectTime) {
                height = Maths.round(height * ((timer - startTime) / effectTime));
            }
            else if(timer > endTime - effectTime) {
                height = Maths.round(height * ((endTime - timer) / effectTime));
            }
            stageName.setSize(stageName.getWidth(), height);

            Graphic.render(stageName, g);
        }

    }

    public boolean isEnd(int timer) {

        return timer > endTime;

    }

}
