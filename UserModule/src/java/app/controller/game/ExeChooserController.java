package app.controller.game;

import app.beans.Game;
import app.helper.Permission;
import app.model.GameManagerModel;
import app.model.game.ExeChooserModel;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class ExeChooserController extends HttpServlet
{
    public void processRequest(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
    	  try{
                res.setContentType("text/html");

                String action = req.getParameter("action");
                System.out.println("action: " + action);
                
                Game game = (Game) req.getSession().getAttribute("game");
                
                if( action != null ){
                    if( action.equals("update") ){
                        String path = req.getParameter("exePath");

                        ExeChooserModel model = new ExeChooserModel();

                        if (model.updateExePath(path, game) ){
                            //model.toggleLive(1, game);
                            req.getSession().setAttribute("game", null);
                        }
                        
                    }
                }
                else{
                   if(game != null){
                       ExeChooserModel model = new ExeChooserModel();
                       game = model.getFileStructureAsJSON(game);
                   }     
                }
                req.getRequestDispatcher("/WEB-INF/Pages/Game/exeChooser.jsp").forward(req, res);
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