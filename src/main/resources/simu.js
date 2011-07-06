karotz = {}

karotz.connect = function(host, port){}
karotz.start = function(callback, data){ callback(data) }


//////////////////////////
karotz.tts = {}
karotz.tts.start = function(text, lang, callback){
    log("karotz should say ["+ lang +"]: " + text);
    if(callback)
        setTimeout((text.length*50), function(){ callback("TERMINATED")})
}

karotz.tts.stop = function(callback){
    log("karotz should stop saying");
    if(callback)
        setTimeout(100, function(){ callback("TERMINATED")})
}


//////////////////////////
karotz.ears = {}
karotz.ears.move = function(left, right, callback){
    log("karotz should move ear : [" + left + ", " + right + "]");
    if(callback)
        setTimeout(((left+right)*10), function(){ callback("TERMINATED")})
};

karotz.ears.moveRelative = function(left, right, callback){
    log("karotz should moveRelative ear : [" + left + ", " + right + "]");
    if(callback)
        setTimeout(((left+right)*10), function(){ callback("TERMINATED")})
};

karotz.ears.reset = function(callback){
    log("karotz should reset ear");
    if(callback)
        setTimeout(100, function(){ callback("TERMINATED")})
};


//////////////////////////
karotz.led = {}
karotz.led.light = function(color){
    log("karotz led is: " + color);
}

karotz.led.fade = function(color, time, callback){
    log("karotz led is fading to: " + color);
    if(callback)
        setTimeout(time, function(){ callback("TERMINATED")});
}

karotz.led.pulse = function(color, pulsePeriod, time, callback){
    log("karotz led pulse to: " + color);
    if(callback)
        setTimeout(time, function(){ callback("TERMINATED")});
}