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

karotz.connect("192.168.90.5", 9123);
karotz.start(callback, "test");


karotz.button.addListener(function(event){ log(event) });

//
karotz.ears.moveRelative(null, 10, function(event){ log(event) });

karotz.tts.start("hello tout le monde", "fr", function(event){
    if(event=="TERMINATED"){
        karotz.multimedia.artist(function(event){
         log(event)
         karotz.multimedia.play("http://www.universal-soundbank.com/mp3/sounds/829.mp3", function(event){
             log(event);
             if(event == "TERMINATED")
                 karotz.led.pulse("0000FF", 100, 3000, cbPulse);
         })
        })
    }
});
karotz.led.light("FF0000");