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
            if(action.equals("new")){


                GameManagerModel model = new GameManagerModel();
                Game game = model.insertNewGame(user.getUserID());
                req.getSession().setAttribute("game", game);

                res.sendRedirect("/UserModule/" + components.get(0));
            }

            else if(action.equals("edit")){
               int gameID = Integer.parseInt( (String)req.getParameter("gameID") );
               GameManagerModel model = new GameManagerModel();
               Game game = model.getGameByID(gameID, user.getUserID());
               if(game != null){
                   req.getSession().setAttribute("game", game);

                   if(game.isInEditMode()){
                       res.sendRedirect("/UserModule/statistics");  
                   }
                   else{
                       HashMap gameState = game.getStates();
                       for(String item : gameComponents.getComponents().keySet()){
                           if(gameState.get(item).equals("incomplete")){
                               res.sendRedirect("/UserModule/" + item);  
                           }
                       }
                       res.sendRedirect("/UserModule/details");
                   }
               }
            }
        }

        System.out.println("component: " + caller);
        if(components != null){
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
                    //Game game = (Game)req.getSession().getAttribute("game");

                    GameManagerModel model = new GameManagerModel();
                    model.toggleLive(1, game);
                    model.toggleEditMode(1, game);

                    req.getSession().setAttribute("game", null);
                    //req.getRequestDispatcher("/WEB-INF/Pages/newGame.jsp").forward(req, res);
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