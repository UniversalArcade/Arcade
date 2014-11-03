
package app.controller;

import app.model.LoginModel;
import app.beans.Costumer;
import app.beans.Message;
import app.beans.User;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
//import Helper.SecurityHelper;

public class LoginController extends HttpServlet
{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
    	  try{
                res.setContentType("text/html");
                RequestDispatcher view = null;               
                Costumer cust = new Costumer();
                cust.setMail(req.getParameter("mail"));
                cust.setPassword(req.getParameter("password"));
                if(cust.getErrors().isEmpty()){
                    System.out.println("EMPTY");                   
                    LoginModel login = new LoginModel();
                    User user = login.login(cust);
                    System.out.println("USERID: " + user.getUserID());                 
                    if(user.getUserID() > 0 && user.getRegistred()>0){
                        req.getSession().setAttribute("user", user);
                        req.getSession().setAttribute("message", new Message(user.getUserID() +  " erfolgreich eingeloggt."));                        
                        System.out.println(user.getUserID());
                        res.sendRedirect("/UserModule/GameListController");                       
                    }
                    else if(user.getUserID() > 0 && user.getRegistred()==0){
                        req.getSession().setAttribute("user", user);
                        req.getSession().setAttribute("message", new Message("Ihr Account wurde noch nicht bestätigt"));                                             
                        req.setAttribute("customer", cust);
                        view = req.getRequestDispatcher("/WEB-INF/Pages/login.jsp");
                    }
                    else{                  
                        req.getSession().setAttribute("message", new Message("User existiert nicht"));     
                        view = req.getRequestDispatcher("/WEB-INF/Pages/login.jsp");
                    
                }
                view.forward(req, res);                   
            }        
          } 
          catch(Exception e){                     
          }  
       }
     @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
         try{
                System.out.println("USER BETRITT SEITE");
               
                res.setContentType("text/html");
                RequestDispatcher view;
                
                
                String action; 
                
                if((action = req.getParameter("action")) != null){
                    if(action.equals("logout")){
                        req.getSession().setAttribute("user", null);                        
                    }
                }
                  
                view = req.getRequestDispatcher("/WEB-INF/Pages/login.jsp");  
                view.forward(req, res);
               
                      
          }
          catch(Exception e){}
            
        
    }
}