//COLOR
var WHITE       ="4FFF78";
var mainColor=function(){
  karotz.led.fade(WHITE,500,function(event){
  if (event == 'TERMINATED' ) karotz.led.pulse("1444FF",500,-1);
  }
  )
}

var workingColor=function(){
  karotz.led.fade("0000FF",500,function(event){
  if (event == 'TERMINATED' ) karotz.led.pulse("",2300,-1);
  }
  )
}

var downloadingColor=function(){
  karotz.led.fade("0000FF",500)
}
