var callback = function(data){
    log("connected: "+data);
}

karotz.connect("192.168.0.22", 9123);
karotz.start(callback, "test");
karotz.tts.start("hello wendy", "fr", function(event){ log(event); });
karotz.led.light("FF0000");