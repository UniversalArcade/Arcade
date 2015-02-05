package app.controller.game;

import app.beans.Game;
import app.beans.Message;
import app.helper.Permission;
import app.model.game.StatisticsModel;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class StatisticsController extends HttpServlet
{
    public void processRequest(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
           res.setContentType("text/html");

        String action = req.getParameter("toggle");

        if(action != null && action.equals("update")){
            
            StatisticsModel statModel = new StatisticsModel();
            String togglebox = req.getParameter("liveToggle");
            Game game = (Game)req.getSession().getAttribute("game");
            
             if(togglebox != null){
                if(togglebox.equals("on")){
                    game.setLife(1);
                   
                }
            }
            else {
                    game.setLife(0);                
            }
             statModel.toggleLife(req, game);   
          
        }  
            req.getRequestDispatcher("/WEB-INF/Pages/Game/statistics.jsp").forward(req, res);
            
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
            req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Kein Zugriff" ));
            req.getRequestDispatcher("/login").forward(req, res);
        }  
    }
}