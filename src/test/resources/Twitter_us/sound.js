txtTitleSound = {'fr':"Son de Karotz",
            'en':"Sound from Karotz"};

txtTitleSound_done = {'fr':"Son envoyée",
            'en':"Sound created"};

txtTitleSound_error ={'fr':"le son n'a pas été envoyée",
                                 'en':"sound has not been sent"};

var photoTakeSoundAndPhoto = undefined;

var takeSound = function(){
  log("takeSound");
  returnAndSave = returnAndSavePostMessage;
  workingColor();
  photoTakeSoundAndPhoto = undefined;
  simpleClicEvent = stoprec;
  karotz.multimedia.record(soundcb);
}

var takeSoundAndPhoto = function(){
  log("takeSoundAndPhoto");
  returnAndSave = returnAndSavePostMessage;
  workingColor();
  photoTakeSoundAndPhoto = undefined;
  
  karotz.webcam.photo("file", function(event){
        if (event.type == "OK") return true;

        photoTakeSoundAndPhoto = event.data;
        setTimeout(1000,function(event){log("##############FUNCTION SOUND"); simpleClicEvent = stoprec; karotz.multimedia.record(soundcb); return false;});

        return false;
    }); 
}

var soundcb = function(event){
  if (event.type != 'TERMINATED') return;
  log("SEND AUDIO")
  downloadingColor();
  audio = event.data;
  rtnSocialPost = social.soundcloud.postSound(audio, transTxt(txtTitleSound), photoTakeSoundAndPhoto);

  var ret = social.soundcloud.shareForce(rtnSocialPost.id, false, true, transTxt(txtTitleSound), soundcb2);
  return false;
}

var soundcb2 = function(rtn){
  log("############### soundcb2");
  workingColor();
  if (rtn < 0) {
        tts(txtTitleSound_error, function(event){log("tts EVENT " + event); if(event == 'OK') {return true;} returnAndSave(); return false;});
  }
  else {
        tts(txtTitleSound_done, function(event){log("tts EVENT " + event); if(event == 'OK') {return true;} returnAndSave(); return false;} );
  }
}

var stoprec=function(){                             
  karotz.multimedia.stop();
  simpleClicEvent = nothingFunc;
}

var returnAndSaveSound = function(){
  log("returnAndSavePostMessage")
  simpleClicEvent = nothingFunc;
  returnAndSave = nothingFunc;
  longClicEvent = global_dblClic;
  mainColor();
}
