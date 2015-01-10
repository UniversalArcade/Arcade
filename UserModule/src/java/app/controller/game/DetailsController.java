package app.controller.game;


import app.beans.User;
import app.beans.Game;
import app.beans.Message;
import app.helper.Permission;
import app.model.game.DetailsModel;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DetailsController extends HttpServlet
{
    public void processRequest(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
    	  try{
                User user = (User)req.getSession().getAttribute("user");
                
                Game blubb = (Game)req.getSession().getAttribute("game");
                System.out.println("details id: " + blubb.getGameID());
                
                res.setContentType("text/html");
                
                String action = req.getParameter("action");
                System.out.println("Details action: " + action );
                
                
                if(action != null){
                
                    if( action.equals("update") ){
                        
                        
                        DetailsModel model = new DetailsModel();
                        Game game = (Game)req.getSession().getAttribute("game");
                        game.deleteErrors();

                        String perma = req.getParameter("permanentStore");
                        System.out.println("Perma: " + perma);
                        if(perma != null){
                            if(perma.equals("on")) game.setPermanentStore(1);
                        }
                        else{
                            game.setPermanentStore(0);
                        }
                        
                        String emulationGame = req.getParameter("emulationGame");
                        if(emulationGame != null){
                            if(emulationGame.equals("on")) game.setEmulationGame(1);
                        }
                        else{
                            game.setEmulationGame(0);
                        }
                                                
                        game.setTitle(req.getParameter("title"));
                        game.setDescription(req.getParameter("description"));
                        game.setCredits(req.getParameter("credits"));
                       
                        // Wenn keine fehlerhaften Eingaben vorhanden, Spiel in die Datenbank einfügen
                        if(game.getErrors().isEmpty()){
                            if( model.updateDetails(game) ){                                
                                req.getSession().setAttribute("message", new Message("Details erfolgreich bearbeitet"));
                                
                                res.sendRedirect("/UserModule/gameManager?component=details");
                            }
                        }
                    }
                }
                req.getRequestDispatcher("/WEB-INF/Pages/Game/details.jsp").forward(req, res);
          }
          catch(Exception e){}  
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        doPost(req,res); 
    }

   
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
            
        Permission permission = new Permission();
        
        if(permission.isValid(req, "user") >= 0){
            this.processRequest(req,res);
        }
        else{
            //req.getSession().setAttribute("message", new Message("error","Kein Zugriff"));
            req.getSession().setAttribute("message", new Message(Message.Type.ERROR,"Kein Zugriff"));
            req.getRequestDispatcher("/login").forward(req, res);
        }  
    }
}