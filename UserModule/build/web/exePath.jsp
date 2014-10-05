<!DOCTYPE html>

<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        
        <script src="Scripts/d3.js" charset="utf-8"></script>
        <script src="Scripts/exeChooser.js"></script>
        
        <style>            
            .axis text {
                font: 10px sans-serif;
            }

            .axis path,
            .axis line {
              fill: none;
              stroke: #000;
              shape-rendering: crispEdges;
            }
            
            .x path {
                 display: none;
            }
        </style>
        
    </head>
    <body onload="init()">
        
        <form action="bla.jsp">
            <input type="hidden" name="exePath" class="exePath" value=""> 
        </form>
    </body>
</html>
