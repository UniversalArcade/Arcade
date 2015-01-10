
package app.controller;

import app.model.LoginModel;
import app.beans.Costumer;
import app.beans.Message;
import app.helper.SecurityHelper;
import java.io.*;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginController extends HttpServlet
{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
            res.setContentType("text/html");
            
            Costumer cust = new Costumer();
            cust.setMail(req.getParameter("mail"));

            String nonSHA = req.getParameter("password");
            String SHAPW = SecurityHelper.getSHAHash(nonSHA);

            cust.setPassword(SHAPW);

            if(cust.getErrors().isEmpty()){
                LoginModel login = new LoginModel();

                try{
                    req.getSession().setAttribute("user", login.login(cust) );
                    res.sendRedirect("/UserModule/GameListController");
                }
                catch(IllegalArgumentException e){ 
                    req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "User existiert nicht oder Passwort falsch"));
                    req.getRequestDispatcher("/WEB-INF/Pages/login.jsp").forward(req, res);
                }
                catch(SecurityException e){
                    req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Ihr Account wurde noch nicht bestätigt. Bitte prüfen Sie Ihre Emails."));
                    req.setAttribute("customer", cust);
                    req.getRequestDispatcher("/WEB-INF/Pages/login.jsp").forward(req, res);
                }
                catch(SQLException e){
                    req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Datenbankfehler " + e.getMessage()));
                    req.getRequestDispatcher("/WEB-INF/Pages/login.jsp").forward(req, res);
                }
            }          
       }
     @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
            
            res.setContentType("text/html");

            String action = req.getParameter("action"); 
            if(action != null){
                if(action.equals("logout")){
                    req.getSession().setAttribute("user", null);                        
                }
            }
            req.getRequestDispatcher("/WEB-INF/Pages/login.jsp").forward(req, res);
    }
}