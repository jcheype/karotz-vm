JSON.save = function (key, value) {
  var jsonText = JSON.stringify(value);
  file.write(key + ".js", jsonText);
}

JSON.load = function (key) {
  var filename = key + ".js";
  
  log("loading resource: " + filename);
  var rs = file.read(key + ".js");
  
  log("resource content: " + rs.text);
  return eval('(' + rs.text + ')');
}

karotz.connectAndStart = function(host, port, callback, data){	
    try{
        karotz.connect(host, port);
    	log("connected");
    	karotz.start(callback, data);
    }catch(err){
    	log(err);
    }
};
