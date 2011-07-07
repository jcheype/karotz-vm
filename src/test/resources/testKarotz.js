var callback = function(data){
    log("connected: "+data);
}

cbFade = function(event){
    if(event == "TERMINATED"){
        karotz.webcam.photo("http://www.karotz.com/photo/testKVM.jpg");
    }
}

cbPulse = function(event){
    if(event == "TERMINATED"){
        karotz.led.fade("00FF00", 1000, cbFade );
    }
}

karotz.connect("10.104.2.41", 9123);
karotz.start(callback, "test");

karotz.asr.string("ééé", "fr-FR", cbPulse)
//
//karotz.tts.start("hello wendy", "fr", function(event){ log(event); });
//karotz.led.light("FF0000");
//setTimeout(1000, function(){
//    karotz.led.pulse("0000FF", 100, 3000, cbPulse);
//});