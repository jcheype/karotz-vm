package net.violet.karotz.vm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: mush
 * Date: 7/5/11
 * Time: 8:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileJs {
    class Data{
        private String text;

        Data(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public Data read(String filename) throws IOException {
        InputStream is = new FileInputStream(filename);
        StringBuilder sb = new StringBuilder();
        byte buffer[] = new byte[1024];
        while(is.available() > 0){
            int read = is.read(buffer);
            sb.append(new String(buffer, 0, read));
        }
        return new Data(sb.toString());
    }
}
