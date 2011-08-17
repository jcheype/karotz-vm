package net.violet.karotz.vm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA.
 * User: mush
 * Date: 7/6/11
 * Time: 10:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class KarotzConnectedTest {
    ScriptEngine engine;

    @Before
    public void init() throws NoSuchMethodException, ScriptException {
        engine = App.getVM(true);
        System.out.println("new test: ");
    }

    @After
    public void after() {
        System.out.println("\n==============\n\n");
    }

//    @Test
    public void script() throws ScriptException, InterruptedException {
        engine.eval(new InputStreamReader(getClass().getResourceAsStream("/testKarotz.js")));
        
        Thread.sleep(15000);
    }
    
    
//    @Test
    public void scriptSocial() throws ScriptException, InterruptedException {
        engine.eval(new InputStreamReader(getClass().getResourceAsStream("/testSocial.js")));
        
        Thread.sleep(5000);
    }

    @Test
    public void testTwitter() throws ScriptException, InterruptedException {
        engine.eval(new InputStreamReader(getClass().getResourceAsStream("/testTwitter.js")));

        Thread.sleep(5000);
    }

}
