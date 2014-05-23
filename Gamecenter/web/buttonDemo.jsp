
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ajax Buttondemo</title>
        <script src="Scripts/jquery-2.1.1.js"></script>
        <script>
            $(document).ready(function() {                        
                
            $('#submit').click(function(event) {  
                var startGame = $('#gameID').val();
                $.get('GameController',{gameID:startGame},function(responseText) { 
                        $('#response').text(responseText);         
                    });
                });
            });
        </script>
</head>
<body>
    
    <form id="StartForm">
        <input type="hidden" id="gameID" value="1"/>
        <input type="button" id="submit" value="Starte Spiel"/>
    </form>    
    
    <div id="response"/>
</body>
</html>

