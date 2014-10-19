package app.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import app.model.GameModel;


public class GameController extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        response.setContentType("text/plain");  
        response.setCharacterEncoding("UTF-8"); 
        
        System.out.println("ENTER GameController");
        
        int gameID = -1; 
        String responseMsg = "";
        
        try{
            gameID = Integer.parseInt( request.getParameter( "gameID" ) );
            String action = request.getParameter("action");
            
            System.out.println("GameController GameID: " + gameID);
            System.out.println("GameController action: " + action);
            
            
            if(action.equals("getGameInfo")){
                GameModel model = new GameModel();
                String gameInfo =  model.getGameInfoByID(gameID);
                System.out.println("JSON gameInfo: " + gameInfo);
                response.getWriter().write(gameInfo);  
            }
            else if(action.equals("startGame")){
                GameModel gameModel = new GameModel();
                gameModel.executeGameByID( gameID );

                //responseMsg nur zu demozwecken hier, soll eigentlich nach Ausführung des Models die Erfolgsmeldung werfen.
                //Msg soll dann auch eher "success" oder "failure", also 1/0 sein falls Frontend dies benötigt
                responseMsg = "Game " + gameID + " erfolgreich gestartet";
                response.getWriter().write(responseMsg);
            }
            
            
            
            
        }
        catch(NumberFormatException e){
            responseMsg = "GameID ist keine Zahl";
            //LOGGING
        }
        
        // Antwort an Aufrufer
        
        response.getWriter().write(responseMsg);  
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}
