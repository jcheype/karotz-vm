package net.violet.karotz.vm;


import java.io.*;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.mina.util.Base64;
import sun.org.mozilla.javascript.internal.Context;
import sun.org.mozilla.javascript.internal.ScriptableObject;

/**
 * Created by IntelliJ IDEA.
 * User: Julien Cheype
 * Date: 8/10/11
 * Time: 3:06 PM
 */
public class UtilJs2 {
    private final ScriptableObject scope;
    private final File dir;
    private final Timer timer = new Timer();


    public UtilJs2(ScriptableObject scope, File dir) {
        this.scope = scope;
        this.dir = dir;
    }

    public String uuid() {
        return UUID.randomUUID().toString();
    }

    public void include(String filename) throws IOException {
        File script = new File(dir, filename);
        Context.getCurrentContext().evaluateReader(scope, new InputStreamReader(new FileInputStream(script), Charset.forName("UTF-8")), filename, 0, null);
    }

    public void exit() {
        System.exit(0);
    }

    private TimerTask newTimerTask(final long delay, final RunnableJS runnable) {
        return new TimerTask() {
            @Override
            public void run() {
                Boolean result = runnable.run();
                //System.out.println("result: " + result);
                if (result != null && result) {
                    timer.schedule(newTimerTask(delay, runnable), delay);
                }
            }
        };
    }

    public void setTimeout(final long delay, final RunnableJS runnable) {
        timer.schedule(newTimerTask(delay, runnable), delay);
    }

    interface RunnableJS {
        public Boolean run();
    }
    
    public String doHMAC(String dataToSign, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException
    {
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec secret = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
        mac.init(secret);
        byte[] digest = mac.doFinal(dataToSign.getBytes(Charset.forName("ASCII")));
        
        String calcSignature = new String(Base64.encodeBase64(digest), Charset.forName("ASCII"));
        return calcSignature;
    }
}
