uuid = function(){ return ___UTILS__.uuid() };

include = function(filename){ return ___UTILS__.include(filename) };

ping = function(){};

setTimeout = function(delay, f){ return ___UTILS__.setTimeout(delay, f) };

log = function(msg){ out.println(msg)};

exit = function(){ return ___UTILS__.exit() };

http = {}
http.post = function(url, params, headers, isMultipart) {
    return http.post2(url, params, headers, isMultipart).content;
};

http.get = function(url) {
    return http.get2(url).content;
}

http.get2 = function(url, headers) {
    var headersMap = new java.util.HashMap();
    for( key in headers ) {
        headersMap.put(key, headers[key]);
    }

    var temp = __http.get2(url, headersMap);
    var res = {};
    res.header = "" + temp.get('header');
    res.content = "" + temp.get('content');
    return res;
}

http.post2 = function(url, params, headers, isMultipart) {
    var javaparams = new java.util.HashMap();
    for( key in params ) {
        javaparams.put(key, params[key]);
    }

    var headersMap = new java.util.HashMap();
    for( key in headers ) {
        headersMap.put(key, headers[key]);
    }

    var temp = __http.post2(url, javaparams, headersMap, !!isMultipart);
    var res = {}
    res.header = "" + temp.get('header');
    res.content = "" + temp.get('content');
    return res;
}

timestamp = function() {
    return Math.floor(new Date().getTime()/1000);
}