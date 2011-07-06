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


//////////////////////////
karotz.multimedia = {}
karotz.multimedia.play = function( path, callback){
    log("karotz should play sound: " + path);
    if(callback)
        setTimeout(1000, function(){ callback("TERMINATED")})
};
karotz.multimedia.pause = function( callback){
    log("karotz should pause sound");
    if(callback)
        setTimeout(100, function(){ callback("TERMINATED")})
};
karotz.multimedia.resume = function( callback){
    log("karotz should resume sound");
    if(callback)
        setTimeout(100, function(){ callback("TERMINATED")})
};
karotz.multimedia.stop = function( callback){
    log("karotz should stop sound");
    if(callback)
        setTimeout(100, function(){ callback("TERMINATED")})
};
karotz.multimedia.next = function( callback){
    log("karotz should select next sound");
    if(callback)
        setTimeout(100, function(){ callback("TERMINATED")})
};
karotz.multimedia.previous = function( callback){
    log("karotz should select previous sound");
    if(callback)
        setTimeout(100, function(){ callback("TERMINATED")})
};
karotz.multimedia.artist = function( callback){
    log("karotz should retrieve usb key songs of specified artist");
    if(callback)
        setTimeout(100, function(){ callback("Bob Marley:LadyGaga")})
};
karotz.multimedia.folder = function( callback){
    log("karotz should retrieve usb key folders");
    if(callback)
        setTimeout(100, function(){ callback("/path1:/path2")})
};
karotz.multimedia.playlist = function( callback){
    log("karotz should retrieve usb key playlists");
    if(callback)
        setTimeout(100, function(){ callback("playlist1:playlist2")})
};
karotz.multimedia.genre = function( callback){
    log("karotz should retrieve usb key genres");
    if(callback)
        setTimeout(100, function(){ callback("genre1:genre2")})
};
karotz.multimedia.song = function( callback){
    log("karotz should retrieve usb key songs");
    if(callback)
        setTimeout(100, function(){ callback("song1:song2")})
};

///////////////////////////
karotz.button = { "addListener" : function(callback){} };
karotz.rfid = { "addListener" : function(callback){} };
karotz.ears.addListener = function(callback){} ;
karotz.multimedia.addListener = function(callback){};