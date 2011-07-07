package net.violet.karotz.vm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import junit.framework.Assert;

/**
 * Unit test for simple App.
 */
public class AppTest

{
    ScriptEngine engine;

    @Before
    public void init() throws NoSuchMethodException, ScriptException {
        engine = App.getVM(false);
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
    
    //@Test
    public void httpPost() throws ScriptException {
        System.out.println("httpPost");
        engine.eval("var mavar = http.post('http://10.104.2.38/cookies/index.php', {'toto':'tata'});"
                + "if ( mavar.indexOf('[toto] => tata') == -1 ) { throw 'cannot find result in post'; }");
    }

    //@Test
    public void httpGet2() throws ScriptException {
        System.out.println("httpGet2");
        engine.eval("var mavar = http.get2('http://www.google.fr');");
        engine.eval("if( mavar.get('header') == undefined ) { throw 'Header undefined'; }");
        engine.eval("if( mavar.get('content') == undefined ) { throw 'Content undefined'; }");
    }
    
    //@Test
    public void httpPost2() throws ScriptException {
        System.out.println("httpPost2");
        engine.eval("var mavar = http.post2('http://10.104.2.38/cookies/index.php', {'toto':'tata'});");
        engine.eval("if( mavar.get('header') == undefined ) { throw 'Header undefined'; }");
        engine.eval("if( mavar.get('content').indexOf('[toto] => tata') == -1 ) { throw 'cannot find result in post2'; }");
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
        engine.eval("setTimeout(100, function(){log('I am displayed after 100ms!'); return false})");
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
