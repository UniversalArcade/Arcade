// Controller um Bestellung abzuschließen

package app.controller;


import app.beans.User;
import app.beans.Game;
import app.helper.Permission;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import app.model.GameManagerModel;
import java.util.ArrayList;
import java.util.List;

public class GameManagerController extends HttpServlet
{
    public void processRequest(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
    	  try{
                List<String> components = new ArrayList();
                components.add("details");
                components.add("coverupload");
                components.add("buttonlayout");
                components.add("gameupload");
                components.add("exechooser");
                
                String caller = req.getParameter("component");
                String action = req.getParameter("action");
                
                
                if(action != null){
                    if(action.equals("new")){
                        User user = (User)req.getSession().getAttribute("user");
                        
                        GameManagerModel model = new GameManagerModel();
                        int gameID = model.insertNewGame(user.getUserID());
                        Game game = new Game();
                        game.setGameID(gameID);
                        req.getSession().setAttribute("game", game);
                        
                        res.sendRedirect("/UserModule/" + components.get(0));
                    }
                }
                
                
                System.out.println("component: " + caller);
                if(components != null){
                    if(components.contains( caller )){
                        int index = components.indexOf( caller ) + 1;
                        if(index < components.size()){
                            String next = components.get( index );
                            res.sendRedirect("/UserModule/" + next);
                        }
                        else{
                            Game game = (Game)req.getSession().getAttribute("game");
                            
                            GameManagerModel model = new GameManagerModel();
                            model.toggleLive(1, game);
                            
                            req.getSession().setAttribute("game", null);
                            req.getRequestDispatcher("/WEB-INF/Pages/newGame.jsp").forward(req, res);
                        }
                    }
                }
                
                
                
  
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