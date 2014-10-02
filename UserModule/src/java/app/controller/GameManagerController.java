// Controller um Bestellung abzuschließen

package app.controller;



import app.beans.Game;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import app.model.GameManagerModel;

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
                newGame.deleteErrors();
                
                view = req.getRequestDispatcher("newGame.jsp");
                String contentType;
                
                
                switch (newGame.getNewGameStep()) {
                    // Game Upload
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
                    // Bild upload    
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
                    // Description
                    case 3:
                        //Game game = new Game();
                        newGame.setTitle(req.getParameter("title"));
                        newGame.setDescription(req.getParameter("description"));
                        newGame.setCredits(req.getParameter("credits"));

                        // Wenn keine fehlerhaften Eingaben vorhanden, Spiel in die Datenbank einfügen
                        if(newGame.getErrors().isEmpty()){
                            GameManagerModel model = new GameManagerModel();
                            if( model.updateDetails(newGame) ){
                                newGame.setNewGameStep(4);
                            }
                        }
                        
                    break;
                    // Button Config    
                    case 4:
                       
                       int buttonAmount = 10; 
                      
                       String buttons = "";
        
                       for(int i = 1; i <= buttonAmount; i++){
                           buttons += req.getParameter("button" + i);
                           if(i < buttonAmount){
                               buttons += ";"; 
                           }
                       }
                        
                       //String button1 = req.getParameter("button1");
                       
                       GameManagerModel model = new GameManagerModel();
                       
                       if (model.updateButtonLayout(buttons, newGame) ){
                           newGame.setNewGameStep(5);
                       }
                        
                        
                        
                    break;
                        
                        
                    default: 
                    break;
                }
                
                
               
                view.forward(req, res);
                      
          }
          catch(Exception e){}  
    }
}