package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GameController extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        int gameID = 0; 
        String responseMsg = "";
    
        try{
            gameID = Integer.parseInt(request.getParameter("gameID"));
            //responseMsg nur zu demozwecken hier, soll eigentlich nach Ausführung des Models die Erfolgsmeldung werfen.
            //Msg soll dann auch eher "success" oder "failure", also 1/0 sein falls Frontend dies benötigt
            responseMsg = "Game " + gameID + " erfolgreich gestartet";
        }
        catch(NumberFormatException e){
            responseMsg = "GameID ist keine Zahl";
            //LOGGING
        }
         
        //rufe Model.java auf und starte Game...
        
        response.setContentType("text/plain");  
        response.setCharacterEncoding("UTF-8"); 
        response.getWriter().write(responseMsg);  
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}
