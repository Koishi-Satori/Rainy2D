package rainy2D.resource;

import rainy2D.util.Array;

import java.io.*;

public class StringLocation extends Location {

    public static String UTF8 = "UTF-8";

    public StringLocation(String name) {

        super(name);

        init("cov", ".txt");

    }

    public Array<String> get(String charset) {

        Array<String> texts = new Array<>();
        String temp;
        InputStream is = findLocalStream("/" + folder + "/" + name + format);

        try {
            InputStreamReader isr = new InputStreamReader(is, charset);
            BufferedReader br = new BufferedReader(isr);

            while((temp = br.readLine()) != null) {
                check(texts, temp);
            }

            is.close();
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

        if(temp.equals("/")) {//只输入斜线视为空行
            texts.add("");
        }
        else {
            texts.add(temp);
        }

    }

}
