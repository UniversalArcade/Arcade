package app.controller.game;


import app.beans.User;
import app.beans.Game;
import app.helper.Permission;
import app.model.game.PosterUploadModel;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import app.model.GameManagerModel;

public class CoverUploadController extends HttpServlet
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
                        if ( (contentType.indexOf("multipart/form-data") >= 0) ) {
                             PosterUploadModel model = new PosterUploadModel();
                             model.uploadImage(req, game);
                             game.setNewGameStep(3);
                             res.sendRedirect("/UserModule/buttonlayout");
                        }else{
                            // Fehler beim hochladen
                        } 
                    }
                }
                req.getRequestDispatcher("/WEB-INF/Pages/Game/coverUpload.jsp").forward(req, res);
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
        
        if(permission.isValid(req, "user")){
            this.processRequest(req,res);
        }
        else{
            req.getRequestDispatcher("/WEB-INF/Pages/login.jsp").forward(req, res);
        }  
    }
}