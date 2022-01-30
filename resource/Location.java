package rainy2D.resource;

public class Location {

    String folder;
    String format;

    String name;

    public Location(String name) {

        this.name = name;

    }

    public void init(String headFolderName, String imageFormat) {

        folder = headFolderName;
        format = imageFormat;

    }

}
