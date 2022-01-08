package rainy2D.resource.info;

import rainy2D.resource.location.ImageLocation;

public class VectorInfo {

    public double speed;
    public double angle;
    public int width;
    public int height;

    public ImageLocation iml;

    public VectorInfo(int width, int height, double speed, double angle, ImageLocation iml) {

        this.speed = speed;
        this.angle = angle;
        this.width = width;
        this.height = height;
        this.iml = iml;

    }

}
