karotz = {}

karotz.connect = function(host, port){
    log("host: " + host);
    log("port: " + port);
    if(__CLIENT__.connect(host, port, 5000)){
        log("karotz connected");
    }
    else{
        log("karotz NOT CONNECTED");
    }
}

karotz.start = function(callback, data){
    if(__CLIENT__.startInteractiveMode()){
        log("karotz interactive mode");
        callback(data);
    }
}

//////////////////////
__GEN_VOOS_CALLBACK = function(callback){
    var voosCallback = null;
    if(callback){
        voosCallback = new net.violet.karotz.client.VoosCallBack() {
            onEvent: function(voosMsg){callback(voosMsg.getEvent().getCode().toString())}
        };
    }
    return voosCallback;
}

karotz.tts = {}
karotz.tts.start = function(text, lang, callback){
    log("karotz should say ["+ lang +"]: " + text);
    __CLIENT__.sendTts(text, lang, __GEN_VOOS_CALLBACK(callback));
}

karotz.tts.stop = function(callback){
    throw "NOT IMPLEMENTED"
}

karotz.led = {}
karotz.led.light = function(color){
    log("karotz led is: " + color);
    __CLIENT__.sendLed(color);
}