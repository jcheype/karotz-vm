package net.violet.karotz.vm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: mush
 * Date: 7/5/11
 * Time: 8:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileJs {
    public class Data{
        private String text;

        Data(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
    
    private final File dir;

    public FileJs(File dir) {
        this.dir = dir;
    }
    public Data read(String filename) throws IOException {
        InputStream is = new FileInputStream(new File(dir, filename));
        StringBuilder sb = new StringBuilder();
        byte buffer[] = new byte[1024];
        while(is.available() > 0){
            int read = is.read(buffer);
            sb.append(new String(buffer, 0, read));
        }
        return new Data(sb.toString());
    }
    
    public void write(String filename, String data) throws IOException {
        OutputStream os = new FileOutputStream(new File(dir, filename));
        os.write(data.getBytes());
        os.flush();
    }
}
