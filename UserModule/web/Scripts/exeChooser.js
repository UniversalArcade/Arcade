function init(fileArray){
   //bla = [{dir1:[{dir2:["fuenf.txt"]},"drei.txt","vier.txt"]},"eins.txt","zwei.txt"];
   //bla = [{"type":"folder","name":"dir1","child":[{"type":"folder","name":"dir2","child":[]},{"type":"file","name":"drei.txt"},{"type":"file","name":"vier.txt"}]},{"type":"file","name":"eins.txt"},{"type":"file","name":"zwei.txt"}]
   //bla = [{"type":"folder","name":"dir1","child":[{"type":"folder","name":"dir2","child":[{"type":"file","name":"blubb.bmp"}]},{"type":"file","name":"drei.txt"},{"type":"file","name":"vier.txt"}]},{"type":"file","name":"eins.txt"},{"type":"file","name":"zwei.txt"}]
   //bla =  [{"type":"folder","name":"dir1","child":[{"type":"folder","name":"dir2","child":[{"type":"file","name":"blubb.bmp"}]},{"type":"file","name":"drei.txt"},{"type":"file","name":"vier.txt"}]},{"type":"file","name":"eins.txt"},{"type":"file","name":"zwei.txt"}]
   bla = fileArray;

   console.log(bla); 

   stack = new Array();
   
   
   bread = d3.select(".fileChooser")
           .append("div")
           .attr("class","fileChooserBreadCrumb")
           .append("p");
           
    bread.append("a")
           .attr("href","#")
           .attr("class","bcBase")
           .append("img")
           .attr("src","./img/home152.png")
           .attr("width","28")
           .attr("height","28")
           .on("click",onBreadCrumbBase);
   
   div = d3.select(".fileChooser")
           .append("div")
           .attr("class","fileChooserExplorer");
    
   construct(bla);  
}

function setSelectedFilePath(filePathJSON){
    
    fp = filePathJSON;
    
    if(filePathJSON.file != null)
    {    
        split = filePathJSON.file.split("/");
        split.shift();

        tmp = bla;
        var foundindex = 0;

        for(var i = 0; i < split.length; i++)
        {
            for(var j = 0; j < tmp.length; j++)
            {  
                if(tmp[j].name == split[i])
                {
                    if(i === 0)
                    {
                        stack[i] = tmp[j];
                    }
                    else
                    {
                        stack[i] = tmp[j];
                    }

                    if(tmp[j].type === "file")
                    {
                        if(stack.length > 1)
                        {
                            construct(stack[stack.length -2].child);
                        }
                        else
                        {
                            construct(tmp);
                        }
                        foundindex = j;
                    }
                    tmp = tmp[j].child;
                    break;
                }
            }
        }
        d3.selectAll(".fileChooserExplorer p:nth-of-type(" + (foundindex + 1) + ") a")
              .attr("class","selected")

        updateFormField();      
    }
}

function updateFormField(){
    var concatPath = "";
    
    if(stack.length > 0 && stack[stack.length -1].type === "file"){
        stack.forEach(function(x){concatPath = concatPath + "/" + x.name;});    
        d3.select(".exePathSubmit").attr("disabled",null);
    }
    else{
        d3.select(".exePathSubmit").attr("disabled","");
    }

    d3.select(".exePath")
          .attr("value",concatPath);
    
    
    console.log(concatPath);
}


function onBreadCrumbBase(){
    d3.event.preventDefault();
    stack = new Array();
    updateFormField();
    construct(bla);
}


function onBreadCrumb(){
    d3.event.preventDefault();
    
    target = d3.event.target;
    destination = target.__data__;
    position = target.attributes.href.value;
    
    //delete all elements after the selected
    stack = stack.slice( 0, position + 1 );
    updateFormField();
    construct(destination.child);
}

function updateBreadCrumb(){
   
    bread.selectAll(".bcElement").remove();
    
    
    var elements = stack.slice();
    if(elements.length > 0){
        if(elements[elements.length -1].type == "file") elements.pop();
    
        bread.selectAll(".bcElement") 
             .data(elements)
             .enter()
             .append("a")
             .attr("href",function(d,i){return i;})
             .text(function(d){return d.name;}) 
             .attr("class","bcElement")
             .on("click", onBreadCrumb);
    }
}

function onClick(){
    
    console.log("Onclick Methode");
    
    d3.event.preventDefault();
   
    clicked = d3.event.target.__data__;
    
    console.log("clicked" + clicked);
    
    if(stack.length > 0)
    {
        //if last element is a file, delete that element and use incoming element instead
        if(stack[stack.length -1].type == "file")
        {
            // Hier selected element css setzten als normal
            stack.pop();
        }
    }
    
    d3.selectAll(".selected")
            .attr("class","normal");
    
    
    //stack.push( { type : clicked.type, name : clicked.name });
    stack.push( clicked );
    // if incoming element is a folder, jump into this folder
    if(clicked.type == "folder")
    { 
        construct(clicked.child);  
    }
    else
    {
        tes = d3.event.target.className= "selected";   
    }

    updateFormField();
    updateBreadCrumb();   
}

function construct(base){
   
   updateBreadCrumb();
   
   ib = base; 
    
   div.selectAll("p").remove();
   
   div.selectAll("p")
      .data(base)
      .enter()
      .append("p")
      .attr("class",function(d){return d.type})
      .append("a")
      .attr("class","normal")
      .attr("href","#")
      .text(function(d){return d.name})
      .on("click", onClick);
}