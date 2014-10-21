package app.controller.game;

import app.beans.Game;
import app.beans.Message;
import app.helper.Permission;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import app.model.game.GameUploadModel;

public class GameUploadController extends HttpServlet
{
    public void processRequest(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
    	  try{
                res.setContentType("text/html");
              
                String action = req.getParameter("action");
                System.out.println("action: " + action);
                
                if( action != null ){
                    
                   Game game = (Game) req.getSession().getAttribute("game");
                    
                   if( action.equals("update") ){
                        String contentType = req.getContentType();
                        if ((contentType.indexOf("multipart/form-data") >= 0)) {
                            GameUploadModel model = new GameUploadModel();
                            model.uploadGame(req, game);
                            req.getSession().setAttribute("message", new Message("Spiel erfolgreich hochgeladen"));
                            res.sendRedirect("/UserModule/gameManager?component=gameupload");
                        }else{
                            // Fehler beim hochladen
                        }  
                   } 
                }
                req.getRequestDispatcher("/WEB-INF/Pages/Game/gameUpload.jsp").forward(req, res);
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