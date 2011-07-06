package net.violet.karotz.vm;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * Hello world!
 */
public class App {
    private static final ScriptEngineManager factory = new ScriptEngineManager();
    private static File dir = new File(".");

    public static ScriptEngine getVM() throws NoSuchMethodException, ScriptException {
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        HttpJS httpJS = new HttpJS();
        engine.put("http", httpJS);
        engine.put("file", new FileJs());
        engine.put("___UTILS__", new UtilJs(engine, dir) );

        engine.eval(new InputStreamReader(App.class.getResourceAsStream("/json2.js")));
        engine.eval(new InputStreamReader(App.class.getResourceAsStream("/init.js")));
        engine.eval(new InputStreamReader(App.class.getResourceAsStream("/simu.js")));
        return engine;
    }

    public static void main(String[] args) throws ScriptException, NoSuchMethodException, FileNotFoundException {
        File f = new File(args[0]);
        dir = f.getParentFile();
        getVM().eval(new InputStreamReader(new FileInputStream(f)));
    }
}
