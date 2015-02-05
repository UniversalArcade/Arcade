// Controller um Bestellung abzuschließen

package app.controller;


import app.beans.User;
import app.beans.Game;
import app.beans.GameComponents;
import app.beans.Message;
import app.helper.Permission;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import app.model.GameManagerModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameManagerController extends HttpServlet
{
    public void processRequest(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
    	  
        GameComponents gameComponents = new GameComponents();


        List<String> components = new ArrayList();
        components.add("details");
        components.add("coverupload");
        components.add("buttonlayout");
        components.add("gameupload");
        components.add("exechooser");

        String caller = req.getParameter("component");
        String action = req.getParameter("action");


        if(action != null){

            User user = (User)req.getSession().getAttribute("user");
            switch (action) {
                case "new":
                    
                    boolean success = false;
                    try{
                        GameManagerModel model = new GameManagerModel();
                        Game game = model.insertNewGame(user.getUserID());
                        req.getSession().setAttribute("game", game);
                        success = true;
                    }
                    catch(SQLException e){
                        req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Datenbankfehler " + e.getMessage()));
                    }
                    finally{
                        if(success) res.sendRedirect("/UserModule/" + components.get(0));
                        else res.sendRedirect("/UserModule/GameListController");
                    }   
                    break;
                    
                case "edit":
                    
                    String redirect = "/UserModule/GameListController";
                    try{
                        int gameID = Integer.parseInt( (String)req.getParameter("gameID") );
                        GameManagerModel model = new GameManagerModel();
                        Game game = model.getGameByID(gameID, user.getUserID());
                        
                        req.getSession().setAttribute("game", game);

                        if(game.isInEditMode()){
                            redirect = "/UserModule/statistics";
                        }
                        else{
                            HashMap gameState = game.getStates();
                            for(String item : gameComponents.getComponents().keySet()){
                                if(gameState.get(item).equals("incomplete")){
                                    redirect = "/UserModule/" + item;
                                }
                            }
                            redirect = "/UserModule/details";
                        }
                    }
                    catch(SQLException e){
                        req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Datenbankfehler " + e.getMessage()));
                    }
                    catch(NumberFormatException e){
                        req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Fehler bei Typumwandlung" ));
                    }
                    finally{
                        res.sendRedirect(redirect);
                    }
                    break;  
                case"delete":
                    redirect = "/UserModule/GameListController";
                     
                    try{
                        int gameID = Integer.parseInt( (String)req.getParameter("gameID") );
                        GameManagerModel model = new GameManagerModel();
                        Game game = model.getGameByID(gameID, user.getUserID());
                        
                        req.getSession().setAttribute("game", game);
                        model.deleteGame(game);
                        
                    
                     }
                     catch(SQLException e){
                        req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Datenbankfehler " + e.getMessage()));
                    }
                     finally{
                        req.getSession().setAttribute("message", new Message(Message.Type.SUCCESS, "Spiel erfolgreich gelöscht."));
                        res.sendRedirect(redirect);
                    }
                break;
            }
        }

        if(components.contains( caller )){

            Game game = (Game) req.getSession().getAttribute("game");

            int index;
            if(game.isInEditMode()){
                index = components.indexOf( caller );
            }
            else{
                index = components.indexOf( caller ) + 1;
            }

            if(index < components.size()){
                String next = components.get( index );
                res.sendRedirect("/UserModule/" + next);
            }
            else{
                try{
                    GameManagerModel model = new GameManagerModel();
                    model.toggleLive(1, game);
                    model.toggleEditMode(1, game);
                    req.getSession().setAttribute("game", null);
                }
                catch(SQLException e){
                    req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Datenbankfehler " + e.getMessage()));
                }
                finally{
                    res.sendRedirect("/UserModule/GameListController");
                }
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