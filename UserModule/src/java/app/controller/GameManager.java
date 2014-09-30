// Controller um Bestellung abzuschließen

package app.controller;



import app.beans.Game;
import java.io.*;
import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.http.*;
import app.model.GameManagerModel;
//import Helper.SecurityHelper;


public class GameManager extends HttpServlet
{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
    	  try{
              
                res.setContentType("text/html");
                RequestDispatcher view;
                
                Game game = new Game();
                game.setTitle(req.getParameter("title"));
                game.setDescription(req.getParameter("description"));
                game.setCredits(req.getParameter("credits"));
                        
                // Wenn keine fehlerhaften Eingaben vorhanden, Spiel in die Datenbank einfügen
                if(game.getErrors().isEmpty()){
            
                    GameManagerModel model = new GameManagerModel();
                    int userID = (int)req.getSession().getAttribute("userID");
                    model.insertNewGame(game,userID);
                    
                    view = req.getRequestDispatcher("exePath.jsp");  
                }
                // ansonsten rücksprung zum Formular mit Fehlermeldung
                else{
                    System.out.println("FEHLER");
                    view = req.getRequestDispatcher("newGame.jsp");
                    req.setAttribute("game", game);
                }
                view.forward(req, res);
                      
          }
          catch(Exception e){}  
    }
    
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
    }
}