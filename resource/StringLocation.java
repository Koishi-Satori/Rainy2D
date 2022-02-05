package rainy2D.resource;

import rainy2D.util.Array;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class StringLocation extends Location {

    public StringLocation(String name) {

        super(name);

        init("cov", ".txt");

    }

    public Array<String> get(String charset) {

        Array<String> texts = new Array<>();
        String temp;
        String path = StringLocation.class.getResource("/" + folder + "/" + name + format).getPath();

        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(path), charset);
            BufferedReader br = new BufferedReader(isr);

            while((temp = br.readLine()) != null) {
                check(texts, temp);
            }

            isr.close();
            br.close();

            return texts;
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return Array.EMPTY;

    }

    public void check(Array<String> texts, String temp) {

        if(temp.equals("/")) {
            texts.add("");
        }
        else {
            texts.add(temp);
        }

    }

}
