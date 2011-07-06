package net.violet.karotz.vm;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Julien Cheype
 * Date: 7/5/11
 * Time: 6:21 PM
 */
public class HttpJS {

    public String get(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.connect();
        InputStream is = connection.getInputStream();
        StringBuilder sb = new StringBuilder();
        byte buffer[] = new byte[1024];
        int read;
        while((read = is.read(buffer))>0 ){
            sb.append(new String(buffer, 0, read));
        }
        return sb.toString();
    }

    public String post(String urlString, Map params){
        throw new UnsupportedOperationException();
    }
}
