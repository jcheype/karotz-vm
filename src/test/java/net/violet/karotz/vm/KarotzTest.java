package net.violet.karotz.vm;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;

import javax.script.ScriptException;
import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

/**
 * Created by IntelliJ IDEA.
 * User: Julien Cheype
 * Date: 7/5/11
 * Time: 7:50 PM
 */
public class KarotzTest {
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
    
    @Test
    public void ttsStart() throws ScriptException, InterruptedException {
        Context.getCurrentContext().evaluateString(engine, "karotz.tts.start('hello', 'fr', function(event){ log(event);});", "ttsStart.js", 0, null);
        Thread.sleep(500);
    }

    @Test
    public void ttsStop() throws ScriptException, InterruptedException {
        
        Context.getCurrentContext().evaluateString(engine, "karotz.tts.start('hello', 'fr', function(event){ log(event);});"
                + "setTimeout(1000, function(){log('wait for tts to start before stopping');return false});"
                + "karotz.tts.stop(function(event){ log(event);});", "ttsStop.js", 0, null);
        Thread.sleep(500);
    }
    
    @Test
    public void earsMove() throws ScriptException, InterruptedException {
        Context.getCurrentContext().evaluateString(engine, "karotz.ears.move(5, 3, function(event){ log(event);} );", "earsMove.js", 0, null);
        Thread.sleep(500);
    }

    @Test
    public void earsMoveRelative() throws ScriptException, InterruptedException {
        Context.getCurrentContext().evaluateString(engine, "karotz.ears.moveRelative(5, 3, function(event){ log(event);});", "earsMoveRelative.js", 0, null);
        Thread.sleep(500);
    }

    @Test
    public void earsReset() throws ScriptException, InterruptedException {
        Context.getCurrentContext().evaluateString(engine, "karotz.ears.reset(function(event){ log(event);});", "earsReset.js", 0, null);
        Thread.sleep(500);
    }

    @Test
    public void ledLight() throws ScriptException, InterruptedException {
        Context.getCurrentContext().evaluateString(engine, "karotz.led.light('FFFFFF');", "ledLight.js", 0, null);
    }

    @Test
    public void ledFade() throws ScriptException, InterruptedException {
        Context.getCurrentContext().evaluateString(engine, "karotz.led.fade('FFFFFF', 500,function(event){ log(event);});", "ledFade.js", 0, null);
        Thread.sleep(700);
    }

    @Test
    public void ledPulse() throws ScriptException, InterruptedException {
        Context.getCurrentContext().evaluateString(engine, "karotz.led.pulse('FFFFFF', 100, 500,function(event){ log(event);});", "ledPulse.js", 0, null);
        Thread.sleep(700);
    }

    @Test
    public void multimediaPlay() throws ScriptException, InterruptedException {
        Context.getCurrentContext().evaluateString(engine, "karotz.multimedia.play('http://www.google.fr/toto.mp3',function(event){ log(event);});", "multimediaPlay.js", 0, null);
        Thread.sleep(1200);
    }

    @Test
    public void multimediaPause() throws ScriptException, InterruptedException {
        Context.getCurrentContext().evaluateString(engine, "karotz.multimedia.pause(function(event){ log(event);});", "multimediaPause.js", 0, null);
        Thread.sleep(100);
    }

    @Test
    public void multimediaResume() throws ScriptException, InterruptedException {
        Context.getCurrentContext().evaluateString(engine, "karotz.multimedia.resume(function(event){ log(event);});", "multimediaResume.js", 0, null);
        Thread.sleep(100);
    }

    @Test
    public void multimediaStop() throws ScriptException, InterruptedException {
        Context.getCurrentContext().evaluateString(engine, "karotz.multimedia.stop(function(event){ log(event);});", "multimediaStop.js", 0, null);
        Thread.sleep(100);
    }

    @Test
    public void multimediaNext() throws ScriptException, InterruptedException {
        Context.getCurrentContext().evaluateString(engine, "karotz.multimedia.next(function(event){ log(event);});", "multimediaNext.js", 0, null);
        Thread.sleep(100);
    }

    @Test
    public void multimediaPrevious() throws ScriptException, InterruptedException {
        Context.getCurrentContext().evaluateString(engine, "karotz.multimedia.previous(function(event){ log(event);});", "multimediaPrevious.js", 0, null);
        Thread.sleep(100);
    }

    @Test
    public void multimediaArtist() throws ScriptException, InterruptedException {
        Context.getCurrentContext().evaluateString(engine, "karotz.multimedia.artist(function(event){ log(event);});", "multimediaArtist.js", 0, null);
        Thread.sleep(100);
    }

    @Test
    public void multimediaFolder() throws ScriptException, InterruptedException {
        Context.getCurrentContext().evaluateString(engine, "karotz.multimedia.folder(function(event){ log(event);});", "multimediaFolder.js", 0, null);
        Thread.sleep(100);
    }

    @Test
    public void multimediaPlaylist() throws ScriptException, InterruptedException {
        Context.getCurrentContext().evaluateString(engine, "karotz.multimedia.playlist(function(event){ log(event);});", "multimediaPlaylist.js", 0, null);
        Thread.sleep(100);
    }

    @Test
    public void multimediaGenre() throws ScriptException, InterruptedException {
        Context.getCurrentContext().evaluateString(engine, "karotz.multimedia.genre(function(event){ log(event);});", "multimediaGenre.js", 0, null);
        Thread.sleep(100);
    }

    @Test
    public void multimediaSong() throws ScriptException, InterruptedException {
        Context.getCurrentContext().evaluateString(engine, "karotz.multimedia.song(function(event){ log(event);});", "multimediaSong.js", 0, null);
        Thread.sleep(100);
    }
    
    @Test
    public void socialInit() throws ScriptException, InterruptedException {
        Context.getCurrentContext().evaluateString(engine, "var data = karotz.social.init(function(event){ log(event); });"
                + "if( data == undefined ) { throw 'data returned from karotz.social.init is undefined'; }"
                + " else { log(data.type) }", "socialInit.js", 0, null);
        Thread.sleep(1000);
    }
}
