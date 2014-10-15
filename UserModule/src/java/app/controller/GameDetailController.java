
package app.controller;

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
        
                
                
        GamesDetail game = new GamesDetail();     
        game.setGameID( Integer.parseInt( (String)req.getParameter("gameID") ) );
        System.out.println("nach parse: " + game.getGameID());
        
            
            if(game.getGameID() > 0){
                GameDetailModel model = new GameDetailModel();
                game = model.getGameDetails(game);
                if(game != null){
                   User u = (User)req.getSession().getAttribute("user");
                
                    if(game.getUserID() == u.getUserID()){
                        view = req.getRequestDispatcher("/WEB-INF/Pages/details.jsp");
                        req.setAttribute("gameDetails", game);
                    }
                    else{
                        view = req.getRequestDispatcher("/WEB-INF/Pages/login.jsp");
                    }
                
                }
                else{
                    view = req.getRequestDispatcher("/WEB-INF/Pages/login.jsp");
                }
                
            }
            else{
                view = req.getRequestDispatcher("/WEB-INF/Pages/games.jsp");
            }
            
            
            
            
            view.forward(req, res);
            
        
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

  
    
}
