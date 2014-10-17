package app.controller.game;


import app.beans.User;
import app.beans.Game;
import app.helper.Permission;
import app.model.game.GameDetailModel;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import app.model.GameManagerModel;

public class GameDetailController extends HttpServlet
{
    public void processRequest(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
    	  try{
                
                User user = (User)req.getSession().getAttribute("user");
                
              
                System.out.println("GameDetailController");
              
                res.setContentType("text/html");
                RequestDispatcher view;
                
                String action = req.getParameter("action");
                System.out.println("action: " + action);
                
                if(action != null){
                
                    if( action.equals("new") ){
                        System.out.println("Neues Game anlegen");
                        GameManagerModel model = new GameManagerModel();
                        int gameID = model.insertNewGame(user.getUserID());
                        Game game = new Game();
                        game.setGameID(gameID);
                        req.getSession().setAttribute("game", game);

                        req.getRequestDispatcher("/WEB-INF/Pages/Game/gameDetails.jsp").forward(req, res);
                    }
                    
                    else if( action.equals("update") ){

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
                            GameDetailModel model = new GameDetailModel();
                            if( model.updateDetails(game) ){
                                game.setNewGameStep(4);

                                res.sendRedirect("/UserModule/GamePosterController");
                            }
                        }
                    }
                }
                req.getRequestDispatcher("/WEB-INF/Pages/Game/gameDetails.jsp").forward(req, res);
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