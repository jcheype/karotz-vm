karotz = {}

karotz.connect = function(host, port){}
karotz.start = function(callback, data){ callback(data) }


//////////////////////////
karotz.tts = {}
karotz.tts.start = function(text, lang, callback){
    log("karotz should say ["+ lang +"]: " + text);
    if(callback)
        setTimeout((text.length*50), function(){ callback("TERMINATED"); return false;})
}

karotz.tts.stop = function(callback){
    log("karotz should stop saying");
    if(callback)
        setTimeout(100, function(){ callback("TERMINATED"); return false;})
}

//////////////////////////
karotz.ears = {}
karotz.ears.move = function(left, right, callback){
    log("karotz should move ear : [" + left + ", " + right + "]");
    if(callback)
        setTimeout(((left+right)*10), function(){ callback("TERMINATED"); return false;})
};

karotz.ears.moveRelative = function(left, right, callback){
    log("karotz should moveRelative ear : [" + left + ", " + right + "]");
    if(callback)
        setTimeout(((left+right)*10), function(){ callback("TERMINATED"); return false;})
};

karotz.ears.reset = function(callback){
    log("karotz should reset ear");
    if(callback)
        setTimeout(100, function(){ callback("TERMINATED"); return false;})
};


//////////////////////////
karotz.led = {}
karotz.led.light = function(color){
    log("karotz led is: " + color);
}

karotz.led.fade = function(color, time, callback){
    log("karotz led is fading to: " + color);
    if(callback)
        setTimeout(time, function(){ callback("TERMINATED"); return false;});
}

karotz.led.pulse = function(color, pulsePeriod, time, callback){
    log("karotz led pulse to: " + color);
    if(callback)
        setTimeout(time, function(){ callback("TERMINATED"); return false;});
}


//////////////////////////
karotz.multimedia = {}
karotz.multimedia.play = function( path, callback){
    log("karotz should play sound: " + path);
    if(callback)
        setTimeout(1000, function(){ callback("TERMINATED"); return false;})
};
karotz.multimedia.pause = function( callback){
    log("karotz should pause sound");
    if(callback)
        setTimeout(100, function(){ callback("TERMINATED"); return false;})
};
karotz.multimedia.resume = function( callback){
    log("karotz should resume sound");
    if(callback)
        setTimeout(100, function(){ callback("TERMINATED"); return false;})
};
karotz.multimedia.stop = function( callback){
    log("karotz should stop sound");
    if(callback)
        setTimeout(100, function(){ callback("TERMINATED"); return false;})
};
karotz.multimedia.next = function( callback){
    log("karotz should select next sound");
    if(callback)
        setTimeout(100, function(){ callback("TERMINATED"); return false;})
};
karotz.multimedia.previous = function( callback){
    log("karotz should select previous sound");
    if(callback)
        setTimeout(100, function(){ callback("TERMINATED"); return false;})
};
karotz.multimedia.artist = function( callback){
    log("karotz should retrieve usb key songs of specified artist");
    if(callback)
        setTimeout(100, function(){ callback("Bob Marley:LadyGaga"); return false;})
};
karotz.multimedia.folder = function( callback){
    log("karotz should retrieve usb key folders");
    if(callback)
        setTimeout(100, function(){ callback("/path1:/path2"); return false;})
};
karotz.multimedia.playlist = function( callback){
    log("karotz should retrieve usb key playlists");
    if(callback)
        setTimeout(100, function(){ callback("playlist1:playlist2"); return false;})
};
karotz.multimedia.genre = function( callback){
    log("karotz should retrieve usb key genres");
    if(callback)
        setTimeout(100, function(){ callback("genre1:genre2"); return false;})
};
karotz.multimedia.song = function( callback){
    log("karotz should retrieve usb key songs");
    if(callback)
        setTimeout(100, function(){ callback("song1:song2"); return false;})
};

///////////////////////////
karotz.social = {}
karotz.social.init = function( callback) {
    log("karotz should get social info for init");
    if( callback )
        setTimeout(100, function(){ callback("TERMINATED"); return false;})
    return {'type':'simulated'};
};
//karotz.social.twitter = {}
//karotz.social.twitter.sign = function(pseudo, pass){
//    log("karotz is signing on twitter with: " + pseudo + " / " + pass)
//};
//karotz.social.facebook = {}
//karotz.social.soundcloud = {}


///////////////////////////
karotz.button = { "addListener" : function(callback){} };
karotz.rfid = { "addListener" : function(callback){} };
karotz.ears.addListener = function(callback){} ;
karotz.multimedia.addListener = function(callback){};