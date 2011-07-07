package net.violet.karotz.vm;

import com.google.common.io.Closeables;
import net.violet.karotz.client.Client;
import org.apache.commons.cli.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;

/**
 * Hello world!
 */
public class App {
    private static final ScriptEngineManager factory = new ScriptEngineManager();
    private static File dir = new File(".");

    public static ScriptEngine getVM(boolean isConnected) throws NoSuchMethodException, ScriptException {
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        HttpJS httpJS = new HttpJS();
        engine.put("__http", httpJS);
        engine.put("file", new FileJs());
        engine.put("___UTILS__", new UtilJs(engine, dir));

        engine.eval(new InputStreamReader(App.class.getResourceAsStream("/json2.js")));
        engine.eval(new InputStreamReader(App.class.getResourceAsStream("/init.js")));
        if(!isConnected)
            engine.eval(new InputStreamReader(App.class.getResourceAsStream("/simu.js")));
        else{
            engine.put("__CLIENT__", new Client());
            engine.eval(new InputStreamReader(App.class.getResourceAsStream("/karotz.js")));
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
        options.addOption("h", "help", false, "print this help");

        CommandLineParser parser = new PosixParser();

        CommandLine cmd = parser.parse(options, args);
        if (cmd.hasOption("help")) {
            showHelp(options);
            System.exit(0);
        }
        if (cmd.hasOption("karotz")) {
            throw new UnsupportedOperationException("not supported yet");
        }

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
        try{
            is = new InputStreamReader(new FileInputStream(new File(dir, "main.js")));
            getVM(cmd.hasOption("karotz")).eval(is);
        }
        finally {
            Closeables.closeQuietly(is);
        }

    }
}
