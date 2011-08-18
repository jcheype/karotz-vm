package net.violet.karotz.vm;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.script.ScriptException;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    ScriptableObject engine;

    @Before
    public void init() throws NoSuchMethodException, ScriptException, IOException {
        engine = App.getVMRhino(false);
        System.out.println("new test: ");
    }

    @After
    public void after() {
        System.out.println("\n==============\n\n");
    }

//    @Test
//    public void httpGet() throws ScriptException {
//        Context.getCurrentContext().evaluateString(engine, "log(http.get('http://www.google.fr'));", "httpGet.js", 0, null);
//    }
//    
//    @Test
//    public void httpPost() throws ScriptException {
//        System.out.println("httpPost");
//        Context.getCurrentContext().evaluateString(engine, "var mavar = http.post('http://10.104.2.38/cookies/index.php', {'toto':'tata'});"
//                + "if ( mavar.indexOf('[toto] => tata') == -1 ) { throw 'cannot find result in post'; }", "httpPost.js", 0, null);
//    }
//
//    @Test
//    public void httpGet2() throws ScriptException {
//        System.out.println("httpGet2");
//        Context.getCurrentContext().evaluateString(engine, "var mavar = http.get2('http://www.google.fr');"
//                + "if( mavar['header'] == undefined ) { throw 'Header undefined'; }"
//                + "if( mavar['content'] == undefined ) { throw 'Content undefined'; }", "httpGet2.js", 0, null);
//    }
//    
//    @Test
//    public void httpPost2() throws ScriptException {
//        System.out.println("httpPost2");
//        Context.getCurrentContext().evaluateString(engine, "var mavar = http.post2('http://10.104.2.38/cookies/index.php', {'toto':'tata'});"
//                + "if( mavar['header'] == undefined ) { throw 'Header undefined'; }"
//                + "if( mavar['content'].indexOf('[toto] => tata') == -1 ) { throw 'cannot find result in post2'; }", "httpPost2.js", 0, null);
//    }
//
//    @Test
//    public void uuid() throws ScriptException {
//        Context.getCurrentContext().evaluateString(engine, "log(uuid());", "uuid.js", 0, null);
//    }
//
//    @Test
//    public void log() throws ScriptException {
//        Context.getCurrentContext().evaluateString(engine, "log('log test');", "log.js", 0, null);
//    }
//
//    @Test
//    public void setTimeout() throws ScriptException, InterruptedException {
//        Context.getCurrentContext().evaluateString(engine, "setTimeout(100, function(){log('I am displayed after 100ms!'); return false})", "setTimeout.js", 0, null);
//        Thread.sleep(500);
//    }
//
//    @Test
//    public void include() throws ScriptException {
//        Context.getCurrentContext().evaluateString(engine, "include('src/test/resources/test1.js');", "include.js", 0, null);
//    }
//
//    @Test
//    public void fileRead() throws ScriptException {
//        Context.getCurrentContext().evaluateString(engine, "data = file.read('src/test/resources/test1.js'); log(data.text)", "fileRead.js", 0, null);
//    }
//    
//    @Test
//    public void fileWrite() throws ScriptException {
//        Context.getCurrentContext().evaluateString(engine, "file.write('target/test.data', 'taralabiscotte'); log('test write');", "fileWrite.js", 0, null);
//    }
//
//    @Test
//    public void ping() throws ScriptException {
//        Context.getCurrentContext().evaluateString(engine, "ping();", "ping.js", 0, null);
//    }
}
