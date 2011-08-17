package net.violet.karotz.vm;

import com.google.common.io.Closeables;
import net.violet.karotz.client.Client;
import org.apache.commons.cli.*;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class App {
    private static final ScriptEngineManager factory = new ScriptEngineManager();
    private static File dir = new File(".");

    public static ScriptableObject getVMRhino(boolean isConnected) throws IOException {
        Context context = Context.enter();
        ScriptableObject scope = context.initStandardObjects();

        HttpJS http = new HttpJS();
        Object httpJS = Context.javaToJS(http, scope);
        ScriptableObject.putProperty(scope, "__http", httpJS);

        FileJs file = new FileJs(dir);
        Object fileJS = Context.javaToJS(file, scope);
        ScriptableObject.putProperty(scope, "file", fileJS);

        UtilJs2 util = new UtilJs2(scope, dir);
        Object utilJS = Context.javaToJS(util, scope);
        ScriptableObject.putProperty(scope, "___UTILS__", utilJS);

        Object wrappedOut = Context.javaToJS(System.out, scope);
        ScriptableObject.putProperty(scope, "out", wrappedOut);

        context.evaluateReader(scope, new InputStreamReader(App.class.getResourceAsStream("/init.js"), Charset.forName("UTF-8")), "init.js", 0, null);
        if (!isConnected)
            context.evaluateReader(scope, new InputStreamReader(App.class.getResourceAsStream("/simu.js"), Charset.forName("UTF-8")), "simu.js", 0, null);
        else {
            Client client = new Client();
            Object clientJS = Context.javaToJS(client, scope);
            ScriptableObject.putProperty(scope, "__CLIENT__", clientJS);
            context.evaluateReader(scope, new InputStreamReader(App.class.getResourceAsStream("/karotz.js"), Charset.forName("UTF-8")), "karotz.js", 0, null);
        }
        return scope;
    }

    public static ScriptEngine getVM(boolean isConnected) throws NoSuchMethodException, ScriptException {
        for (ScriptEngineFactory f : factory.getEngineFactories()) {
            System.out.println("name: " + f.getEngineName());
            System.out.println("language: " + f.getLanguageName());
        }
        ScriptEngine engine = factory.getEngineByName("rhino");
        HttpJS httpJS = new HttpJS();
        engine.put("__http", httpJS);
        engine.put("file", new FileJs(dir));
        engine.put("___UTILS__", new UtilJs(engine, dir));

        //engine.eval(new InputStreamReader(App.class.getResourceAsStream("/json2.js"), Charset.forName("UTF-8")));
        engine.eval(new InputStreamReader(App.class.getResourceAsStream("/init.js"), Charset.forName("UTF-8")));
        if (!isConnected)
            engine.eval(new InputStreamReader(App.class.getResourceAsStream("/simu.js"), Charset.forName("UTF-8")));
        else {
            engine.put("__CLIENT__", new Client());
            engine.eval(new InputStreamReader(App.class.getResourceAsStream("/karotz.js"), Charset.forName("UTF-8")));
        }
        return engine;
    }

    private static void showHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("karotz-vm.jar [OPTION]... APP_FOLDER", options);
    }

    public static void main(String[] args) throws ScriptException, NoSuchMethodException, IOException, ParseException {
        Options options = new Options();

        options.addOption("s", "simulation", false, "set simulation on (no Karotz needed)");
        options.addOption("k", "karotz", false, "Karotz connection, set simulation off");
        options.addOption("i", "include", true, "Javascript file included before main.js");
        options.addOption("h", "help", false, "print this help");

        CommandLineParser parser = new PosixParser();

        CommandLine cmd = parser.parse(options, args);
        if (cmd.hasOption("help")) {
            showHelp(options);
            System.exit(0);
        }
//        if (cmd.hasOption("karotz")) {
//            throw new UnsupportedOperationException("not supported yet");
//        }

        if (cmd.getArgs().length < 1) {
            System.out.println("Missing APP_FOLDER arg");
            showHelp(options);
            System.exit(1);
        }

        dir = new File(cmd.getArgs()[0]);
        if (!dir.isDirectory()) {
            System.out.println("APP_FOLDER arg must be an existing application folder");
            showHelp(options);
            System.exit(1);
        }
        InputStreamReader is = null;
        try {
            ScriptableObject scope = getVMRhino(cmd.hasOption("karotz"));
            if (cmd.hasOption("include")) {
                is = new InputStreamReader(new FileInputStream(new File(dir, cmd.getOptionValue("include"))), Charset.forName("UTF-8"));

                Context.getCurrentContext().evaluateReader(scope, is, cmd.getOptionValue("include"), 0, null);

                Closeables.closeQuietly(is);
            }

            is = new InputStreamReader(new FileInputStream(new File(dir, "main.js")), Charset.forName("UTF-8"));
            Context.getCurrentContext().evaluateReader(scope, is, "main.js", 0, null);
        } finally {
            Closeables.closeQuietly(is);
        }

    }
}
