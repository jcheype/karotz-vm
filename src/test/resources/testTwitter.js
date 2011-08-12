include("src/test/resources/Twitter_us/lib_twitter.js");
include("src/test/resources/Twitter_us/twitter.js");

log("testTwitter.js");

var callback = function(data){
    log("connected: "+data);
//    exit();
}

getNbLists = function(){
    var params = {};
//    params.user_id = "";
    params.screen_name = "Neirdoo";
    var result = social.twitter.sendSign("GET", "http://api.twitter.com/1/lists/all.json", params);
    setTimeout(2000, function(){karotz.start(callback, "getNbLists");});
    log("result:" + result);
    if(result)
        return JSON.parse(result);
    return false;
}


var socialok = function(data){
    log("connected to Karotz");
    setTimeout(2000, function(){karotz.start(callback, {});});
//    var sign = social.twitter.sign('blabla');
//    log("social.twitter.sendSign sign: " + sign.toString());
//    getNbLists();
    social.twitter.GetHomeTimeline(10);
//startRead();

}

karotz.connect("192.168.90.5", 9123);
social.init(socialok, {});