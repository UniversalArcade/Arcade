package app.controller.game;

import app.beans.Game;
import app.beans.Message;
import app.helper.Permission;
import app.model.GameManagerModel;
import app.model.game.ExeChooserModel;


import java.io.*;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;


public class ExeChooserController extends HttpServlet
{
    public void processRequest(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
            res.setContentType("text/html");

            String action = req.getParameter("action");

            Game game = (Game) req.getSession().getAttribute("game");


            if( action != null && action.equals("update")){
                
                String path = req.getParameter("exePath");

                boolean success = false;
                try{
                    ExeChooserModel model = new ExeChooserModel();
                    model.updateExePath(path, game);
                    req.getSession().setAttribute("message", new Message("Exe erfolgreich ausgewählt"));
                    success = true;
                }
                catch(SQLException e){
                    req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Datenbankfehler " + e.getMessage()));
                }
                finally{
                    if(success) res.sendRedirect("/UserModule/gameManager?component=exechooser");
                    else req.getRequestDispatcher("/WEB-INF/Pages/Game/exeChooser.jsp").forward(req, res);
                }
            }
            else{
               if(game != null){

                   if(game.getEmulationGame() == 1){
                       res.sendRedirect("/UserModule/gameManager?component=exechooser");
                   }
                   else{
                        try{
                            ExeChooserModel model = new ExeChooserModel();
                            game = model.getFileStructureAsJSON(game);
                        }
                        catch(SQLException e){
                            req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Datenbankfehler " + e.getMessage()));
                        }
                        req.getRequestDispatcher("/WEB-INF/Pages/Game/exeChooser.jsp").forward(req, res);
                   }
               }
               else{
                   req.getRequestDispatcher("/WEB-INF/Pages/Game/exeChooser.jsp").forward(req, res);
               }
               
            }
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
           req.getSession().setAttribute("message", new Message(Message.Type.ERROR,"Kein Zugriff"));
           req.getRequestDispatcher("/login").forward(req, res);
        }  
    }
}