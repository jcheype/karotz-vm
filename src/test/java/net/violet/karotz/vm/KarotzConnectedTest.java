package net.violet.karotz.vm;

import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.After;
import org.junit.Before;

import javax.script.ScriptException;
import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

/**
 * Created by IntelliJ IDEA.
 * User: mush
 * Date: 7/6/11
 * Time: 10:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class KarotzConnectedTest {
    
    ScriptableObject engine;

    @Before
    public void init() throws NoSuchMethodException, ScriptException, IOException {
        engine = App.getVMRhino(true);
        System.out.println("new test: ");
    }

    @After
    public void after() {
        System.out.println("\n==============\n\n");
    }

    @Test
    public void script() throws ScriptException, InterruptedException, IOException {
        InputStreamReader is = new InputStreamReader(getClass().getResourceAsStream("/testKarotz.js"));
        Context.getCurrentContext().evaluateReader(engine, is, "testKarotz.js", 0, null);
        
        Thread.sleep(15000);
    }
    
    
//    @Test
    public void scriptSocial() throws ScriptException, InterruptedException, IOException {
        InputStreamReader is = new InputStreamReader(getClass().getResourceAsStream("/testSocial.js"));
        Context.getCurrentContext().evaluateReader(engine, is, "testSocial.js", 0, null);
        
        Thread.sleep(5000);
    }

//    @Test
    public void testTwitter() throws ScriptException, InterruptedException, IOException {
        InputStreamReader is = new InputStreamReader(getClass().getResourceAsStream("/testTwitter.js"));
        Context.getCurrentContext().evaluateReader(engine, is, "testTwitter.js", 0, null);

        Thread.sleep(10000);
    }

}
