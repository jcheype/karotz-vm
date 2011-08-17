var guitar = 
[
"24279__NoiseCollector__yamaha_B.mp3",
"24280__NoiseCollector__yamaha_B6.mp3",
"24281__NoiseCollector__yamaha_B7.mp3",
"24282__NoiseCollector__yamaha_B72.mp3",
"24283__NoiseCollector__yamaha_B9.mp3"
];

var rdmGuitar = function(){
  log("rdm guitar")
  karotz.multimedia.play("/usr/out/"+guitar[Math.floor(Math.random()*guitar.length)])
}
