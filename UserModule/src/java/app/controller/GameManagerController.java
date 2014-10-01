// Controller um Bestellung abzuschließen

package app.controller;



import app.beans.Game;
import java.io.*;
import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.http.*;
import app.model.GameManagerModel;
import app.model.GameUploadModel;
//import Helper.SecurityHelper;


public class GameManagerController extends HttpServlet
{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
    	  try{
              
                res.setContentType("text/html");
                RequestDispatcher view;
                
                int userID = (int)req.getSession().getAttribute("userID");
                Game newGame = (Game)req.getSession().getAttribute("game");
                view = req.getRequestDispatcher("newGame.jsp");
                String contentType;
                
                
                switch (newGame.getNewGameStep()) {
                    case 1:  
                            
                        
                            System.out.println("step = 0");
                            contentType = req.getContentType();
                            if ((contentType.indexOf("multipart/form-data") >= 0)) {
                                 GameManagerModel model = new GameManagerModel();
                                 int gameID = model.insertNewGame(userID);
                                 newGame.setGameID(gameID);   
                                 
                                 if(newGame.getErrors().isEmpty()){
                                     model.uploadGame(req);
                                     newGame.setNewGameStep(2);    
                                 }
                                 
                                 
                                 
                                 
                                 
                            }else{
                                // Fehler beim hochladen
                            }  
                    break;
                    case 2:  
                            System.out.println("step = 0");
                            contentType = req.getContentType();
                            if ((contentType.indexOf("multipart/form-data") >= 0)) {
                                 GameManagerModel model = new GameManagerModel();
                                 model.uploadPicture(req);
                                 newGame.setNewGameStep(3);
                            }else{
                                // Fehler beim hochladen
                            } 
                        
                    break;
                    case 3:
                        //Game game = new Game();
                        newGame.setTitle(req.getParameter("title"));
                        newGame.setDescription(req.getParameter("description"));
                        newGame.setCredits(req.getParameter("credits"));

                        // Wenn keine fehlerhaften Eingaben vorhanden, Spiel in die Datenbank einfügen
                        if(newGame.getErrors().isEmpty()){

                            GameManagerModel model = new GameManagerModel();
                            
                            //model.insertNewGame(newGame,userID);
                            
                            newGame.setNewGameStep(3);
                            
                            //view = req.getRequestDispatcher("exePath.jsp");  
                        }
                        // ansonsten rücksprung zum Formular mit Fehlermeldung
                        else{
                            System.out.println("FEHLER");
                            //view = req.getRequestDispatcher("newGame.jsp");
                            //req.setAttribute("game", game);
                        }
                    break;
                    case 4:
                    
                    break;
                        
                        
                    default: ;
                    break;
                }
                
                
               
                view.forward(req, res);
                      
          }
          catch(Exception e){}  
    }
}