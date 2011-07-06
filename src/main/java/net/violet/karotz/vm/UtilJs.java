package net.violet.karotz.vm;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: Julien Cheype
 * Date: 7/5/11
 * Time: 6:36 PM
 */
public class UtilJs {
    private final File dir;
    private final ScriptEngine engine;
    private final Timer timer = new Timer();

    public UtilJs(ScriptEngine engine, File dir) {
        this.engine = engine;
        this.dir = dir;
    }

    public String uuid(){
        return UUID.randomUUID().toString();
    }

    public void include(String filename) throws FileNotFoundException, ScriptException {
        File script = new File(dir,filename);
        //System.out.println(script.getAbsolutePath());
        engine.eval(new InputStreamReader(new FileInputStream(script)));
    }

    public void exit(){
        System.exit(0);
    }

    private TimerTask newTimerTask(final long delay, final RunnableJS runnable){
        return new TimerTask() {
            @Override
            public void run() {
                Boolean result = runnable.run();
                //System.out.println("result: " + result);
                if(result != null && result){
                    timer.schedule(newTimerTask(delay, runnable), delay);
                }
            }
        };
    }

    public void setTimeout(final long delay, final RunnableJS runnable){
        timer.schedule(newTimerTask(delay, runnable), delay);
    }

    interface RunnableJS{
        public Boolean run();
    }
}
