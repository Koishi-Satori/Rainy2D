package rainy2D.render.screen;

import rainy2D.render.element.Element;
import rainy2D.shape.Rect;

public class Chunk {

    Rect chunk;

    public Chunk(Rect chunk) {

        this.chunk = chunk;

    }

    public boolean isInChunk(Element e) {

        return e.getRect().intersects(chunk);

    }

}
