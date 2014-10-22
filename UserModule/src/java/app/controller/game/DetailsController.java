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
                RequestDispatcher view;
                
                String action = req.getParameter("action");
                System.out.println("Details action: " + action );
                
                
                if(action != null){
                
                    if( action.equals("update") ){

                        Game game = (Game)req.getSession().getAttribute("game");
                        game.deleteErrors();

                        game.setTitle(req.getParameter("title"));
                        game.setDescription(req.getParameter("description"));
                        game.setCredits(req.getParameter("credits"));

                        System.out.println("perma: " + req.getParameter("permanentStore"));
                        String perma = req.getParameter("permanentStore");

                        if(perma != null){
                            if(perma.equals("on")) game.setPermanentStore(0);
                        }
                        else{
                            game.setPermanentStore(0);
                        }

                        // Wenn keine fehlerhaften Eingaben vorhanden, Spiel in die Datenbank einfügen
                        if(game.getErrors().isEmpty()){
                            DetailsModel model = new DetailsModel();
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
            req.getSession().setAttribute("message", new Message("error","Kein Zugriff"));
            req.getRequestDispatcher("/login").forward(req, res);
        }  
    }
}