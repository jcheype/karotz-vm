include("src/test/resources/lib_twitter.js");

log("testTwitter.js");

var callback = function(data){
    log("connected: "+data);
    var timeline  = social.twitter.GetHomeTimeline(10);
log(JSON.stringify(timeline));
//    exit();
}


var socialok = function(data){
    log("connected to Karotz");
    setTimeout(100, function(){karotz.start(callback, {}); return false;});
}

karotz.connect("192.168.90.5", 9123);
social.init(socialok, {});