package app.controller.game;

import app.beans.Message;
import app.helper.Permission;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class StatisticsController extends HttpServlet
{
    public void processRequest(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
            req.getRequestDispatcher("/WEB-INF/Pages/Game/statistics.jsp").forward(req, res);
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
            req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Kein Zugriff" ));
            req.getRequestDispatcher("/login").forward(req, res);
        }  
    }
}