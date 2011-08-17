include("src/test/resources/Twitter_us/txtTTS.js");
include("src/test/resources/Twitter_us/lib_twitter.js");
include("src/test/resources/Twitter_us/lib_googleasr.js");

//current data. refer to twitterDataName
var currentDataId = 0;
var twitterCurrentResults;
var isReading = false;

//max message read
var nbCountMax = 20;
var twitterDataName = new Array("directMessages_url", "mentions_url", "homeTimeline_url" );

var twitter_url = new Array();
twitter_url[twitterDataName[0]] = "http://api.twitter.com/1/direct_messages.json";
twitter_url[twitterDataName[1]] = "http://api.twitter.com/1/statuses/mentions.json";
twitter_url[twitterDataName[2]] = "http://api.twitter.com/1/statuses/home_timeline.json";

var twitterDatatxt = new Array();
twitterDatatxt[0] = { 'fr':" nouveaux messages",'en':" new messages"}
twitterDatatxt[1] = { 'fr':" nouvelles mentions",'en':" new mentions"}
twitterDatatxt[2] = { 'fr':" nouveaux tweet",'en':" new tweets"}

txtMessage_end = {'fr':"Fin des messages",
            'en':"End of messages"};

var twitter_readFunc = new Array();
var readFunc_homeTimeline = function(tweet){
	log("readFunc_homeTimeline");
	var rtnArray = new Array();
    var langTwit = getLang(tweet.text);

	rtnArray["lang"] = langTwit;

    txt = social.twitter.cleanText(tweet.text);
	txt = social.twitter.replaceLink(txt, langTwit);
	rtnArray["txt"] = homeTimelineTTS[langTwit] + tweet.user.screen_name + ", " + txt;
	return rtnArray;
}

var readFunc_directMessages = function(tweet){
	log("readFunc_directMessages");
	var rtnArray = new Array();
    var langTwit = getLang(tweet.text);

	rtnArray["lang"] = langTwit;

	txt = social.twitter.replaceLink(tweet.text, langTwit);

	rtnArray["txt"] = directMessagesTTS[langTwit] + tweet.sender.screen_name + ", " + txt;
	return rtnArray;
}

var readFunc_mentions = function(tweet){
	log("readFunc_mentions");
	var rtnArray = new Array();
    var langTwit = getLang(tweet.text);

	rtnArray["lang"] = langTwit;

	txt = social.twitter.replaceLink(tweet.text, langTwit);

	rtnArray["txt"] = mentionsTTS[langTwit] + tweet.user.screen_name + ", " + txt;
	return rtnArray;
}
twitter_readFunc[twitterDataName[0]] = readFunc_directMessages;
twitter_readFunc[twitterDataName[1]] = readFunc_mentions;
twitter_readFunc[twitterDataName[2]] = readFunc_homeTimeline;

var returnAndSaveTwitter = function(){
  launchType.name = "NOPE";
  isReading = false;
  karotz.tts.stop();
  returnAndSave = nothingFunc;
  simpleClicEvent = nothingFunc;
  longClicEvent = global_dblClic;
  mainColor();
}

var startAutoRead = function(){
    log("############### startAutoRead");
	currentDataId ++;
    returnAndSave = returnAndSaveTwitter; //function(){ isReading = false; karotz.tts.stop(); startAutoRead();};

	log("startAutoRead currentDataId : " + currentDataId);
    log("startAutoRead name of data : " + twitterDataName[currentDataId]);
	if((currentDataId <= twitterDataName.length) && (currentDataId <= contentOption))
	{
		if (checkTweets() > 0)
        {
            log("checkTweets results");
            isReading = false;
            checkConnected(runReadTweets);
        }
        else
        {
            log("checkTweets no results");
            startAutoRead();
        }
		return;
	}
	log("exit TWITTER");
    exit();
}

var startRead = function(){
    log("############### startRead");
    returnAndSave = returnAndSaveTwitter;

	if (checkTweets() > 0)
    {
        log("checkTweets results");

        isReading = false;
        runReadTweets();
    }
    else
    {
        log("checkTweets no results");
        tts({'fr':"pas de" + transTxt(twitterDatatxt[currentDataId]),'en':"no" + transTxt(twitterDatatxt[currentDataId])}, function(event){if(event != 'OK') returnAndSaveTwitter(); })
    }
}

var twitterTooLate = function(){
    log("TOOLATE");
    if (isReading) return false;

    if(launchType.name == "SCHEDULER") startAutoRead();
    else returnAndSaveTwitter();

    return false; 
}

var runReadTweets = function(){
    log("runReadTweets");
    workingColor();
    //5 sec to validate and say message
    tts( { 'fr':twitterCurrentResults.length + transTxt(twitterDatatxt[currentDataId]),'en':twitterCurrentResults.length + transTxt(twitterDatatxt[currentDataId])}, function(event){if(event != 'OK') setTimeout(4000, twitterTooLate); });
    
    simpleClicEvent = function(){simpleClicEvent = karotz.tts.stop; isReading = true; readTweets(twitterCurrentResults, twitterCurrentResults.length -1); };
}

var checkTweets = function(){
	log("checkTweets");
    if(connected) downloadingColor();
	var currentUrl = twitter_url[twitterDataName[currentDataId]];
    var sinceId;

	//params
	var params = {};
	try {
	  sinceId = lastTwitterId[twitterDataName[currentDataId]];
	  if((sinceId != undefined) && (sinceId != -1)) params["since_id"] = sinceId;
	  log("sinceid : " + params["since_id"]);
	  params["count"] = nbCountMax;
	}
	catch(e)
	{
	  log("no sinceid");
        sinceId = undefined;
	}

	var data = social.twitter.sendSign("GET", currentUrl,  params);
    for (var i =0; i< data.length; i+=150)
	{
        log("data:" + data.substr(i, i+150));
                if(i> 300) break;
    }
    log("JSON.parse(data) before");
    var twitterJson = JSON.parse(data);
    log("JSON.parse(data) done");

    if(twitterJson.length != undefined)
    {
        log("Nb of message : " + twitterJson.length);
        twitterCurrentResults = twitterJson;
        return twitterJson.length;
    }
    else
    {
        return -1;
    }
}

var readTweets = function(twitterResults, tweetIdx){
    log("readTweets : " + (tweetIdx + 1) + "/" + twitterResults.length);

    if (tweetIdx >= 0 && isReading){
	    var currentTweetId = "" + twitterResults[tweetIdx].id_str;

        log("tweet id: " + currentTweetId);

	    var currentTweet = twitterResults[tweetIdx];
           
	    var rtnArray = new Array();
	    var rtnArray = (twitter_readFunc[twitterDataName[currentDataId]])(currentTweet);

	    log("txt : "  + rtnArray["txt"]);
	    log("lang : "  + rtnArray["lang"]);
	    if(rtnArray["txt"]){
		    log("currentTweet txt OK");

		    karotz.tts.start(rtnArray["txt"], rtnArray["lang"],
			    function(event){
				    if(event != "OK"){
					    saveTwitterId(twitterDataName[currentDataId], currentTweetId);
					    readTweets(twitterResults, tweetIdx -1);
					    return false;
				    }
                    return true;
			    }
		    );
		    return;
        }
        else{
            log("currentTweet txt NOT OK");
            readTweets(twitterResults, tweetIdx-1);
        }
    }
    else{
        //ADD TTS fin des message
        if(isReading) tts(txtMessage_end, function(event){log("tts EVENT " + event); if(event == 'OK') {return true;} if(launchType.name == "SCHEDULER"){startAutoRead();}else{returnAndSave()} return false;} );
    }
}

