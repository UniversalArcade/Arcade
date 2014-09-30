
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>







<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ajax Linkdemo</title>
        <script src="Scripts/jquery-2.1.1.js"></script>
        <script>
            $(document).ready(function() {                        
                
                $("a").click(function (event) {
                    event.preventDefault(); // Verhindert das Link aufgerufen wird          
                    var startGame = $(this).attr('href'); // vom aktuellen aufrufer (this) das href attribut
                    $.get('GameController',{gameID:startGame},function(responseText) { 
                            $('#response').text(responseText);         
                    });
                });
                
            });
        </script>
        
        
        
</head>
<body>

    <a href = "1"> starte Spiel 1 </a>
    <br/>
    <a href = "2"> starte Spiel 2 </a>
    
    <div id="response"/>
</body>
</html>

