package net.violet.karotz.vm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * Unit test for simple App.
 */
public class AppTest

{
    ScriptEngine engine;

    @Before
    public void init() throws NoSuchMethodException, ScriptException {
        engine = App.getVM();
        System.out.println("new test: ");
    }

    @After
    public void after() {
        System.out.println("\n==============\n\n");
    }

    @Test
    public void httpGet() throws ScriptException {
        engine.eval("print(http.get('http://www.google.fr'));");
    }

    @Test
    public void uuid() throws ScriptException {
        engine.eval("print(uuid());");
    }

    @Test
    public void log() throws ScriptException {
        engine.eval("log('log test');");
    }

    @Test
    public void setTimeout() throws ScriptException, InterruptedException {
        engine.eval("setTimeout(100, function(){log('I am displayed after 100ms!'); return true})");
        Thread.sleep(500);
    }

    @Test
    public void include() throws ScriptException {
        engine.eval("include('src/test/resources/test1.js');");
    }

    @Test
    public void fileRead() throws ScriptException {
        engine.eval("data = file.read('src/test/resources/test1.js'); log(data.text)");
    }

    @Test
    public void ping() throws ScriptException {
        engine.eval("ping();");
    }
}
