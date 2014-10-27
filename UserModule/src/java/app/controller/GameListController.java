
package app.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import app.beans.User;
import app.beans.GamesList;
import app.beans.Message;
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
            //view = req.getRequestDispatcher("/WEB-INF/Pages/games.jsp");
            
            req.setAttribute("gamesList", bgl);
            req.getRequestDispatcher("/WEB-INF/Pages/games.jsp").forward(req, res);
        }
        else{
            //view = req.getRequestDispatcher("/WEB-INF/Pages/games.jsp");
            res.sendRedirect("/UserModule/gameManager?action=new");
            
        }

        //view.forward(req, res); 
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
