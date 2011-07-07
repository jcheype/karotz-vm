package net.violet.karotz.vm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
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
            sb.append(new String(buffer, 0, read, "UTF-8"));
        }
        return sb.toString();
    }

    public String post(String urlString, Map<String, String> params) throws IOException{
        StringBuilder sb = new StringBuilder();
        for(String key : params.keySet() ) {
            sb.append( URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(params.get(key), "UTF-8") + "&");
        }

        // Send data
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(sb.substring(0, Math.max(0, sb.length()-1)));
        wr.flush();

        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
        StringBuilder sb2 = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb2.append(line);
        }
        wr.close();
        rd.close();

        return sb2.toString();
    }
    
    public Map<String, Object> get2(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.connect();
        HashMap<String, Object> res = new HashMap<String, Object>(2);
        Map<String, List<String>> hl = connection.getHeaderFields();
        res.put("header", hl);
        res.put("content", connection.getContent());

        return res;
    }
    public Map<String, Object> post2(String urlString, Map params) throws IOException {
        StringBuilder sb = new StringBuilder();
        for(Object key : params.keySet() ) {
            sb.append( URLEncoder.encode((String)key, "UTF-8") + "=" + URLEncoder.encode((String)(params.get(key)), "UTF-8") + "&");
        }

        // Send data
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(sb.toString().substring(0, Math.max(0, sb.length()-1)));
        wr.flush();
        
        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
        StringBuilder sb2 = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb2.append(line);
        }
        wr.close();
        rd.close();
        
        HashMap<String, Object> res = new HashMap<String, Object>(2);
        res.put("header", conn.getHeaderFields());
        res.put("content", sb2.toString());
        
        return res;
    }
    
}
