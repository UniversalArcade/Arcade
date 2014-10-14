
package app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import app.beans.User;
import app.beans.GamesList;
import app.model.GameListModel;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author KM
 */
public class GameListController extends HttpServlet {

    
    protected void processRequest(HttpServletRequest req, HttpServletResponse res) 
            throws ServletException, IOException{
    
        res.setContentType("text/html");
                RequestDispatcher view;    
             
        User u = (User)req.getSession().getAttribute("user");
            
            GamesList bgl;
            if(u.getUserID() > 0){
                GameListModel gl = new GameListModel();
                bgl = gl.listGames(u);
                if(!bgl.getGames().isEmpty()){
                    view = req.getRequestDispatcher("games.jsp");
                    req.setAttribute("gamesList", bgl);
                }
                else{
                    view = req.getRequestDispatcher("newGame.jsp");
                    
                }
            }
            else{
                view = req.getRequestDispatcher("index.jsp");
            }
            
            view.forward(req, res); 
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
            
        processRequest(req,res);
    }

   
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
            
        processRequest(req,res);
    }

  
    
}
