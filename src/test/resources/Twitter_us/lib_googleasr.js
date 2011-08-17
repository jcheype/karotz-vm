//04/08/2011

var googleAsr = function(audio, lang){
    paramToPost = {
        "file":audio,
    };

    var header = {};
    header["Content-Type"] = "audio/x-flac; rate=16000";

    result = http.post("http://www.google.com/speech-api/v1/recognize?lang=" + lang + "-" + lang + "&client=chromium", paramToPost, header, false);
    log("result : " + result);
    result = JSON.parse(result);
    log("result : " + result.hypotheses[0].utterance);
    return result.hypotheses[0].utterance;
}

var getLang = function(txt){
    var dataLang = http.get("http://ajax.googleapis.com/ajax/services/language/detect?v=1.0&q=" + encodeURIComponent(txt));
    var dataLangJson = JSON.parse(dataLang);
    log("currentTweet dataLangJson : " + dataLangJson.responseData.language);
    return dataLangJson.responseData.language
}
