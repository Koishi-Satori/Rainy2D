package rainy2D.resource;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class AnimatedImage {

    int imgViewTimer;
    int time;

    int indexOfArray;

    public ArrayList<BufferedImage> imgArray = new ArrayList<>();

    public AnimatedImage(int imgViewTimer) {

        this.imgViewTimer = imgViewTimer;

    }

    public void addImage(BufferedImage img) {

        this.imgArray.add(img);

    }

    public void tick() {

        this.time++;

        if(time % imgViewTimer == 0) {
            this.indexOfArray++;

            if(indexOfArray == imgArray.size()) {
                this.indexOfArray = 0;
            }
        }

    }

    public void reset() {

        this.indexOfArray = 0;

    }

    public BufferedImage getNowImage() {

        return imgArray.get(indexOfArray);

    }

    public int getNowIndex() {

        return indexOfArray;

    }

    public int indexSize() {

        return imgArray.size() - 1;

    }

}
