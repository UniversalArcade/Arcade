imgBasePath = "img/games/";
sizes = { svgWidth : 1250, svgHeight : 900, imgWidth : 400, imgHeight : 600, imgThresh : 20 };
imageIDs = [1, 2, 3, 5, 56, 732];
positions = new Array();
bla = null;
done = true;
transition = {type:"elastic", duration:500};
var socket;

function websock(){
 
    socket = new WebSocket("ws://localhost:56400", "protocolOne");
 
    // When the connection is open, send some data to the server
    socket.onopen = function (event) {
    };
 
    socket.onmessage = function (event) {
       receiveMessage( event.data );  
    };
 
    // Log errors
    socket.onerror = function (error) {
        console.log('WebSocket Error %0',  error);
    };
}

function sendMessage(msg){
    socket.send(msg);
    
    //debug
    d3.select(".socketsend").text("SENDING: " + msg);
}

function receiveMessage(msg){
    
    //debug
    d3.select(".socketreceive").text("RECEIVING: " + msg);
    
    //trim whitespace after message (.trim() doesn't work here...)
    while(msg.length > 0 && msg.charCodeAt(msg.length-1) === 0){    
        msg = msg.substring(0, msg.length-1);
    }

    var obj = JSON.parse(msg);
    eval(obj.action + "( obj )");
}

function displayGameInfo(gameInfo){
    console.log("DISPLAY GAME INFO");
    console.log(gameInfo);
}

function returnToSlider(){
}


// Diese Funktion ist nur dafür da um verschiedene Animationen zu testen. Kann danach gelöscht werden
function evaluation(){
    
    d3.select("body").append("div").attr("class","socketmsg");
    d3.select(".socketmsg").append("p").attr("class","socketsend");
    d3.select(".socketmsg").append("p").attr("class","socketreceive");
    
    var trans = ["elastic","poly","quad","cubic","sin","exp","circle","linear","back","bounce"];
    
    d3.select("body")
        .append("div")
        .append("select")
        .attr("id","transition")
        .selectAll("option")
        .data(trans)
        .enter()
        .append("option")
        .attr("value", function( d ) { return d; })
        .text(function(d){return d; });
                
           
    d3.select("#transition").on("change", function(){
        transition.type = trans[ d3.select("#transition").property('selectedIndex') ]; 
    });
    
    var dur = [0,100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000];
    
    d3.select("body")
        .append("div")
        .append("select")
        .attr("id","duration")
        .selectAll("option")
        .data(dur)
        .enter()
        .append("option")
        .attr("value", function( d ) { return d; })
        .text(function(d){return d; });
                
           
    d3.select("#duration").on("change", function(){
        transition.duration = dur[ d3.select("#duration").property('selectedIndex') ]; 
    });
}

function init(){    
    websock();
    evaluation();
    
    imageIDs.forEach(function(d,i){ positions.push( sizes.imgWidth * (i-1) + sizes.imgThresh * (i-1) ) });
    //(i-1) damit das erste Bild links außerhalb der svg ebene ist
    
    
    container = d3.select("body")
                  .append("svg")
                  .attr("width",sizes.svgWidth)
                  .attr("height",sizes.svgHeight)
                  .append("g");
    
    container.selectAll("image")
             .data(positions)
             .enter()
             .append("image")
             .attr("xlink:href",function(d,i){ return imgBasePath + imageIDs[i] + ".jpg"; })
             .attr("x",function(d){ return d; })
             .attr("y",200)
             .attr("width", sizes.imgWidth)
             .attr("height", sizes.imgHeight)
             .attr("opacity",function(d){
               if(d < 0 || d > sizes.svgWidth){
                   return "0";
               }
               else{
                   return "1";
               }
            });
    
    d3.select("body")
      .on("keydown", function(){
          keyCode = d3.event.keyCode;
          if(keyCode === 68){ //D
              console.log("right");
              if(done){
                  updatePosition(1);
              }  
              
          }
          else if(keyCode === 65){ //A
              console.log("left");
              if(done){  
                updatePosition(-1);
              }
          }
          else if(keyCode === 13){ //Enter
              selectGame();
          }
      }); 
}

function selectGame(){  
    sendMessage( JSON.stringify( { action:"getGameInfo", gameID: imageIDs[ positions.indexOf(420) ] } ) );
}

function updatePosition(direction){
    done = false;
    if(direction === 1){
        positions.push( positions.shift() );
    }
   
    else{ // direction = -1
       positions.unshift( positions.pop() );  
    }
   
    d3.selectAll("image")
            .data(positions)
            .transition()
            .ease(transition.type)
            .duration(transition.duration)
            .attr("x",function(d){ return d; })
            .attr("opacity",function(d){
               if(d < 0 || d >= sizes.svgWidth){
                   return "0";
               }
               else{
                   return "1";
               }
            })
            .call(endAll, function(){ done = true; });
}

// wird jeweils einmal pro animationselement aufgerufen und ruft selber callback() auf wenn gesamtanimation beendet
function endAll(transition, callback){
    var n = 0; 
    transition 
        .each(function() { ++n; }) 
        .each("end", function() { if (!--n) callback(); }); 
}


