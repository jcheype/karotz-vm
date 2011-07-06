package net.violet.karotz.vm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * Created by IntelliJ IDEA.
 * User: Julien Cheype
 * Date: 7/5/11
 * Time: 7:50 PM
 */
public class KarotzTest {
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
    public void ttsStart() throws ScriptException, InterruptedException {
        engine.eval("karotz.tts.start('hello', 'fr', function(event){ log(event);});");
        Thread.sleep(500);
    }

    @Test
    public void earsMove() throws ScriptException, InterruptedException {
        engine.eval("karotz.ears.move(5, 3, function(event){ log(event);});");
        Thread.sleep(500);
    }

    @Test
    public void earsMoveRelative() throws ScriptException, InterruptedException {
        engine.eval("karotz.ears.moveRelative(5, 3, function(event){ log(event);});");
        Thread.sleep(500);
    }

    @Test
    public void earsReset() throws ScriptException, InterruptedException {
        engine.eval("karotz.ears.reset(function(event){ log(event);});");
        Thread.sleep(500);
    }

    @Test
    public void ledLight() throws ScriptException, InterruptedException {
        engine.eval("karotz.led.light('FFFFFF');");
    }

    @Test
    public void ledFade() throws ScriptException, InterruptedException {
        engine.eval("karotz.led.fade('FFFFFF', 500,function(event){ log(event);});");
        Thread.sleep(700);
    }

    @Test
    public void ledPulse() throws ScriptException, InterruptedException {
        engine.eval("karotz.led.pulse('FFFFFF', 100, 500,function(event){ log(event);});");
        Thread.sleep(700);
    }
}
