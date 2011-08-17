if (!social.twitter)
    social.twitter = {}

//Param : photo, text
//return : photo url
social.twitter.uploadTwitpic = function(photo, text){
    log("social.twitter.uploadTwitpic");
    
    var oauth_nonce = uuid();
    var oauth_timestamp = timestamp();
	var data = "GET&" + encodeURIComponent("https://api.twitter.com/1/account/verify_credentials.json") + "&";
    var params = {};
	params["oauth_nonce"] = oauth_nonce;
	params["oauth_signature_method"] = "HMAC-SHA1";
	params["oauth_timestamp"] = oauth_timestamp;
    params["oauth_consumer_key"] = social.twitter.oauth_consumer_key;
	params["oauth_token"] = social.twitter.oauth_token;
	params["oauth_version"] = "1.0";

	var keys = [];
	for (key in params){ keys.push(key); }
	keys = keys.sort();
	
	for (var i =0; i< keys.length; i++){data +=  encodeURIComponent(keys[i]) + "%3D" + encodeURIComponent(params[keys[i]]) + "%26";}
	
	data = data.substr(0, (data.length -3));
    var sign = social.twitter.sign(data);

    params["OAuth realm"] = "http://api.twitter.com/";

    var header = {};
    header["X-Auth-Service-Provider"] = "https://api.twitter.com/1/account/verify_credentials.json";
    header["X-Verify-Credentials-Authorization"] = 'OAuth realm="http://api.twitter.com/", oauth_consumer_key="' + encodeURIComponent(social.twitter.oauth_consumer_key) + '", oauth_signature_method="HMAC-SHA1", oauth_token="' + encodeURIComponent(social.twitter.oauth_token) + '", oauth_timestamp="' + encodeURIComponent(oauth_timestamp) + '", oauth_nonce="' + encodeURIComponent(oauth_nonce) + '", oauth_version="1.0", oauth_signature="' + encodeURIComponent(sign) + '"';

    var paramTwipic = {};
    paramTwipic["key"] = "8839c44567c5de3faf0bfe35da30a361";
    paramTwipic["message"] = text;
	paramTwipic["media"] = photo;
	
    result = http.post("http://api.twitpic.com/2/upload.json", paramTwipic, header, true);
    log("result : " + result);
    result = JSON.parse(result);
    log("result id: " + result.id);
    log("result URL: " + result.url);
    log("result text: " + result.text);

    return result.url;
}
