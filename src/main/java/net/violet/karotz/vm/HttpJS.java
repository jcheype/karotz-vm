package net.violet.karotz.vm;


import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
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

    private HttpClient getClient() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
//        TrustStrategy trustStrategy = new
//                TrustStrategy(){
//                    public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//                        return true;
//                    }
//                };
//        X509HostnameVerifier hostnameVerifier = new
//                AllowAllHostnameVerifier();
//        SSLSocketFactory sslSf = new
//                SSLSocketFactory(trustStrategy, hostnameVerifier);
//
//        Scheme https = new Scheme("https", 443,
//                sslSf);
//        SchemeRegistry schemeRegistry = new SchemeRegistry();
//        schemeRegistry.register(https);
//
//        ClientConnectionManager connection = new
//                ThreadSafeClientConnManager(schemeRegistry);

        DefaultHttpClient httpClient = new DefaultHttpClient();
        return httpClient;
    }

    private String read(InputStream is) throws IOException {
        return read(is, "UTF-8");
    }

    private String read(InputStream is, String contentEncoding) throws IOException {
        System.out.println("encoding: " + contentEncoding);
        StringBuilder sb = new StringBuilder();
        byte buffer[] = new byte[1024];
        int read;
        while ((read = is.read(buffer)) > 0) {
            sb.append(new String(buffer, 0, read, contentEncoding));
        }
        return sb.toString();
    }

    private byte[] readByte(InputStream is) throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        byte buffer[] = new byte[1024];
        int read;
        while ((read = is.read(buffer)) != -1) {
            ba.write(buffer, 0, read);
        }
        return ba.toByteArray();
    }

    private Header[] map2Headers(Map<String, String> headersMap) {
        Header headers[] = new Header[headersMap.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : headersMap.entrySet()) {
            headers[i++] = new BasicHeader(entry.getKey(), entry.getValue());
        }
        return headers;
    }

    private String headers2String(HttpMessage httpMessage) {
        StringBuilder sb = new StringBuilder();
        for (Header header : httpMessage.getAllHeaders()) {
            sb.append(header.getName());
            sb.append(": ");
            sb.append(header.getValue());
            sb.append("\n");
        }

        return sb.toString();
    }

    public String get(String urlString, Map<String, String> headers) throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        return get2(urlString, headers).get("content");
    }

    public String post(String urlString, Map<String, String> params, Map<String, String> headers, Boolean isMultipart) throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        return post2(urlString, params, headers, isMultipart).get("content");
    }

    public Map<String, String> get2(String urlString, Map<String, String> headersMap) throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        HttpClient httpclient = getClient();

        HttpGet httpGet = new HttpGet(urlString);

        if (headersMap != null) {
            httpGet.setHeaders(map2Headers(headersMap));
        }

        HttpResponse response = httpclient.execute(httpGet);


        StringBuilder sb = new StringBuilder();
        for (Header header : response.getAllHeaders()) {
            sb.append(header.getName());
            sb.append(": ");
            sb.append(header.getValue());
            sb.append("\n");
        }

        Header contentEncoding = response.getEntity().getContentEncoding();
        String encoding = "UTF-8";
        if (contentEncoding != null)
            encoding = contentEncoding.getValue();

        Map<String, String> res = new HashMap<String, String>();
        res.put("header", sb.toString());
        res.put("content", read(response.getEntity().getContent(), encoding));
        return res;
    }

    public Map<String, String> post2(String urlString, Map<String, String> params, Map<String, String> headersMap, Boolean isMultipart) throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        HttpClient httpclient = getClient();
        httpclient.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);

        HttpPost httpPost = new HttpPost(urlString);

        if (headersMap == null)
            headersMap = new HashMap<String, String>();
        HttpEntity httpEntity;

        if (isMultipart) {
            MultipartEntity mpe = new MultipartEntity();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getValue().startsWith("__PATH:")) {
                    String filename = entry.getValue().substring("__PATH:".length());
                    InputStream stream = getClass().getResourceAsStream("/" + filename);

                    mpe.addPart(entry.getKey(), new ByteArrayBody(readByte(stream), filename));
                } else
                    mpe.addPart(entry.getKey(), new StringBody(entry.getValue(), "", Charset.forName("UTF-8")));
            }
            httpEntity = mpe;
        } else {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpEntity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
        }
        httpPost.setEntity(httpEntity);

        if (headersMap != null)
            httpPost.setHeaders(map2Headers(headersMap));

        HttpResponse response = httpclient.execute(httpPost);

        StringBuilder sb = new StringBuilder();
        for (Header header : response.getAllHeaders()) {
            sb.append(header.getName());
            sb.append(": ");
            sb.append(header.getValue());
            sb.append("\n");
        }

        Header contentEncoding = response.getEntity().getContentEncoding();
        String encoding = "UTF-8";
        if (contentEncoding != null)
            encoding = contentEncoding.getValue();

        Map<String, String> res = new HashMap<String, String>();
        res.put("header", sb.toString());
        res.put("content", read(response.getEntity().getContent(), encoding));

        return res;
    }


}
