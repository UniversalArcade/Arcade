package app.controller.game;


import app.beans.User;
import app.beans.Game;
import app.beans.Message;
import app.helper.Permission;
import app.model.game.DetailsModel;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;

public class DetailsController extends HttpServlet
{
    public void processRequest(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
    
        res.setContentType("text/html");

        String action = req.getParameter("action");

        if(action != null && action.equals("update")){

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
                
                boolean success = false;
                try{
                    model.updateDetails(game);                                
                    req.getSession().setAttribute("message", new Message("Details erfolgreich bearbeitet"));
                    success = true;
                }
                catch(SQLException e){
                    req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Datenbankfehler " + e.getMessage()));
                }
                finally{
                    if(success) res.sendRedirect("/UserModule/gameManager?component=details"); 
                    else req.getRequestDispatcher("/WEB-INF/Pages/Game/details.jsp").forward(req, res);
                }
            }
            else{
                req.getRequestDispatcher("/WEB-INF/Pages/Game/details.jsp").forward(req, res);
            }
        }
        else{
            req.getRequestDispatcher("/WEB-INF/Pages/Game/details.jsp").forward(req, res);
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