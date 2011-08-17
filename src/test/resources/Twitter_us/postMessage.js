txtPostMessage_understood = {'fr':"J'ai compris : ",
            'en':"I understood : "};

txtPostMessage_done = {'fr':"Message envoyée",
            'en':"Message created"};

txtPostMessage_error = {'fr':"Erreur d'envoi",
            'en':"Error of creation"};

var isPostingMessage = false;
var phrasePostMessage = "";
var photoTakeSoundAndPhoto = undefined;

var initPostMessage = function(){
  isPostingMessage = false;
  workingColor();
  phrasePostMessage = "";
  photoTakeSoundAndPhoto = undefined;
  returnAndSave = returnAndSavePostMessage;
  longClicEvent = longClicEvent_postMessage ;
}

var startRec = function (){
  simpleClicEvent = stoprec;
  karotz.multimedia.record(postMessagecb);
}

var postMessage = function(){
  log("postMessage");
  initPostMessage();

  startRec();
}

var postMessageAndPhoto = function(){
  log("postMessageAndPhoto");

  initPostMessage();

  karotz.webcam.photo("file", function(event){
        if (event.type == "OK") return true;

        photoTakeSoundAndPhoto = event.data;
        setTimeout(1000,function(event){log("##### TIMEOUT postMessageAndPhoto"); startRec(); return false;});

        return false;
    });
}

var postMessagecb = function(event){
  if (event.type == 'OK') return true;
  log("googleAsr");
  downloadingColor();
  audio = event.data;
  phrasePostMessage = googleAsr(audio, lang);
  if(phrasePostMessage == undefined){ returnAndSavePostMessage(); return false;}
  workingColor();
  karotz.tts.start(transTxt(txtPostMessage_understood) + phrasePostMessage,lang, postMessagecb2);
  return false;
}

var postMessagecb2 = function(event){
  if(event == "OK") return true;
  simpleClicEvent = acceptRecord;
  isPostingMessage = false;
  setTimeout(4000,function(){log("TOOLATE"); if (isPostingMessage) {return false;} returnAndSavePostMessage(); return false});
  return false;
}

var acceptRecord=function(){   
  log("acceptRecord");
  downloadingColor();
  isPostingMessage = true;
  simpleClicEvent = function(){};

  if(photoTakeSoundAndPhoto != undefined)
  {
    log("photoTakeSoundAndPhoto != {}");
    var pictUrl = social.twitter.uploadTwitpic(photoTakeSoundAndPhoto, phrasePostMessage);
    log("photoTakeSoundAndPhoto pictUrl : " + pictUrl);
    phrasePostMessage += " " + pictUrl;
  }

  log("phrasePostMessage : " + phrasePostMessage);

  if (social.twitter.sendMsg(phrasePostMessage)) {
        tts(txtPostMessage_done, function(event){log("tts EVENT " + event); if (event == 'OK') {return true;} returnAndSave(); return false;} );
  }
  else {
        tts(txtPostMessage_error, function(event){log("tts EVENT " + event); if (event == 'OK') {return true;} returnAndSave(); return false;} );
  }
}

var stoprec=function(){                             
  karotz.multimedia.stop();
  simpleClicEvent = nothingFunc;
}

var longClicEvent_postMessage = function(){
  log("##### longClicEvent_postMessage");
  isPostingMessage = true;
  phrasePostMessage = "";
  startRec();
}

var returnAndSavePostMessage = function(){
  log("returnAndSavePostMessage")
  simpleClicEvent = nothingFunc;
  returnAndSave = nothingFunc;
  longClicEvent = global_dblClic;
  mainColor();
}
