//03/08/2011

if (!social.twitter)
    social.twitter = {}

//********* Regexp
var httpLinkTxt = new Array();
httpLinkTxt["fr"] = ". lien.";
httpLinkTxt["en"] = ". link.";
httpLinkTxt["de"] = ". link.";
httpLinkTxt["es"] = " enlace.";
httpLinkTxt["nl"] = ". link.";

var httpLink=new RegExp("http://[^ ]*", "g");
var triplePoint=new RegExp("\\.\\.\\.", "g");
social.twitter.charToRemove = new RegExp("RT|@|#", "g");
//*********
    
social.twitter.sendSign = function(method, url, params){
    log("social.twitter.sendSign START");
	var oauth_nonce = uuid();
	var oauth_timestamp = timestamp();
    var paramsTmp = {};

    var keys = [];
	for (key in params){ keys.push(key); }
	keys = keys.sort();

    if( method=="POST" ){
	    for (var i =0; i< keys.length; i++){ 
            log("keys[i]:" + keys[i] + "   params[keys[i]]:" + params[keys[i]]); 
            paramsTmp[keys[i]] = params[keys[i]];
            params[keys[i]] = encodeURIComponent(params[keys[i]]);
        }
        log("############ " + paramsTmp.status);
    }

	var data = method + "&";
	data += encodeURIComponent(url) + "&";

	params["oauth_nonce"] = oauth_nonce;
	params["oauth_signature_method"] = "HMAC-SHA1";
	params["oauth_timestamp"] = oauth_timestamp;
    params["oauth_consumer_key"] = social.twitter.oauth_consumer_key;
	params["oauth_token"] = social.twitter.oauth_token;
	params["oauth_version"] = "1.0";

    keys = [];
	for (key in params){ keys.push(key); }
	keys = keys.sort();
	
	for (var i =0; i< keys.length; i++){ 
        log("keys[i]:" + keys[i] + "   params[keys[i]]:" + params[keys[i]]); 
        data +=  encodeURIComponent(keys[i]) + "%3D" + encodeURIComponent(params[keys[i]]) + "%26";
    }
	data = data.substr(0, (data.length -3));

    for (var i =0; i< data.length; i+=150)
	{
        log("dataURL:" + data.substr(i, i+150));
    }
	
    var sign = social.twitter.sign(data);
    log("social.twitter.sendSign sign: " + sign);

	if( method=="GET" ){
	    var data2 = "";
	    for (var i =0; i< keys.length; i++)
	    {
	       	data2 +=  encodeURIComponent(keys[i]) + "=" + encodeURIComponent(params[keys[i]]) + "&";
	    }
	    data2 = data2.substr(0, (data2.length -1));
	
	    log("social.twitter.sendSign DONE");
	
		return http.get(url + "?" + data2 + "&oauth_signature=" + encodeURIComponent(sign), {});
	}

    var header = {};
    var headerTxt = "OAuth ";

    for (var i =0; i< keys.length; i++)
	{
        if("status" != keys[i])
        {
	        headerTxt += keys[i] + '="' + encodeURIComponent(params[keys[i]]) + '", ';
        }
	}
    headerTxt += 'oauth_signature="' + encodeURIComponent(sign) + '", ';

    log("headerTxt:" + headerTxt.substr(0, 150));
    log("headerTxt:" + headerTxt.substr(150, 300));

    header[encodeURIComponent("Authorization")] = headerTxt;

    var rtnPost = http.post(url , paramsTmp, header, false);
    log("rtnPost:" + rtnPost);
    for (var i =0; i< rtnPost.length; i+=150)
	{
        log("rtnPost:" + rtnPost.substr(i, i+150));
    }
	return rtnPost;
}

social.twitter.GetHomeTimeline = function(nbMaxMessage, sinceId){
    var params = {};
    params.count = nbMaxMessage;
    if((sinceId != undefined) && (sinceId > 0))
    {
        params.since_id = sinceId;
    }
    var result = social.twitter.sendSign("GET", "http://api.twitter.com/1/statuses/home_timeline.json", params);
    log("result:" + result);
    if(result)
        return JSON.parse(result);
    return false;
}

social.twitter.GetMentions = function(nbMaxMessage, sinceId){
    var params = {};
    params.count = nbMaxMessage;
    if((sinceId != undefined) && (sinceId > 0))
    {
        params.since_id = sinceId;
    }
    var result = social.twitter.sendSign("GET", "http://api.twitter.com/1/statuses/mentions.json", params);
    log("result:" + result);
    if(result)
        return JSON.parse(result);
    return false;
}

social.twitter.GetDirectMessages = function(nbMaxMessage, sinceId){
    var params = {};
    params.count = nbMaxMessage;
    if((sinceId != undefined) && (sinceId > 0))
    {
        params.since_id = sinceId;
    }
    var result = social.twitter.sendSign("GET", "http://api.twitter.com/1/direct_messages.json", params);
    log("result:" + result);
    if(result)
        return JSON.parse(result);
    return false;
}

social.twitter.GetSearch = function(query, sinceId){
    var params = {};
    params.q = query;
    if((sinceId != undefined) && (sinceId > 0))
    {
        params.since_id = sinceId;
    }
    var result = social.twitter.sendSign("GET", "http://search.twitter.com/search.json", params);
    log("result:" + result);
    if(result)
        return JSON.parse(result);
    return false;
}

social.twitter.sendMsg = function(text){
    var params = {};
    params.status = text;
    var result = social.twitter.sendSign("POST", "https://api.twitter.com/1/statuses/update.json", params);
//var result = social.twitter.sendSign("POST", "http://212.81.112.23:1234/1/statuses/update.json", params);
    if(result)
        return true;
    return false;
}

social.twitter.sendDirectMsg = function(screen_name, user_id, text){
    var params = {};
    params.screen_name = screen_name;
    params.user_id = user_id;
    params.text = text;
    var result = social.twitter.sendSign("POST", "http://api.twitter.com/1/direct_messages/new.json", params);
    if(result)
        return true;
    return false;
}

social.twitter.cleanText = function(text){
    text.replace(social.twitter.charToRemove, "");
    text = text.replace(triplePoint, ".");
    text = text.toLowerCase();

    return text;
}

social.twitter.replaceLink = function(text, lang){
    if(lang != undefined)
    {
        text = text.replace(httpLink, httpLinkTxt[lang]);
    }
    return text;
}
