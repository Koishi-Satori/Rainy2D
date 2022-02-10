package rainy2D.resource;

import java.io.InputStream;
import java.net.URL;

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

    public URL findLocalPath(String path) {

        return getClass().getResource(path);

    }

    public InputStream findLocalStream(String path) {

        return getClass().getResourceAsStream(path);

    }

}
