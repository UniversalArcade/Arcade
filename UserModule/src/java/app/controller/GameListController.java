
package app.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import app.beans.User;
import app.beans.GamesList;
import app.helper.Permission;
import app.model.GameListModel;
import javax.servlet.RequestDispatcher;


public class GameListController extends HttpServlet{

    
    protected void processRequest(HttpServletRequest req, HttpServletResponse res) 
            throws ServletException, IOException{
    
        res.setContentType("text/html");
                RequestDispatcher view;    
             
        User u = (User)req.getSession().getAttribute("user");
            
        GamesList bgl;

        GameListModel gl = new GameListModel();
        bgl = gl.listGames(u);
        if(!bgl.getGames().isEmpty()){
            view = req.getRequestDispatcher("/WEB-INF/Pages/games.jsp");
            req.setAttribute("gamesList", bgl);
        }
        else{
            view = req.getRequestDispatcher("/WEB-INF/Pages/newGame.jsp");

        }

        view.forward(req, res); 
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
