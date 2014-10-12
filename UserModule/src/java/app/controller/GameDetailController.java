
package app.controller;

import app.beans.Game;
import app.beans.GamesDetail;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import app.beans.User;
import app.beans.GamesList;
import app.model.GameDetailModel;
import app.model.GameListModel;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author KM
 */
public class GameDetailController extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
            
        res.setContentType("text/html");
                RequestDispatcher view;    
        
              
                
        Game g = (Game)req.getSession().getAttribute("Game");
            
            GamesDetail bgdl;
            if(g.getGameID() > 0){
                GameDetailModel gdl = new GameDetailModel();
                bgdl = gdl.listGames(g);
                if(!bgdl.getDetails().isEmpty()){
                    view = req.getRequestDispatcher("details.jsp");
                    req.setAttribute("gamesDetails", bgdl);
                }
                else{
                    view = req.getRequestDispatcher("games.jsp");
                    
                }
            }
            else{
                view = req.getRequestDispatcher("index.jsp");
            }
            
            
            
            
            view.forward(req, res);
            
        
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

  
    
}
