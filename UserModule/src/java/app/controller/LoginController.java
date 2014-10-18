// Controller um Bestellung abzuschließen

package app.controller;

import app.model.LoginModel;
import app.beans.Costumer;
import app.beans.Message;
import app.beans.User;

import java.io.*;
import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.http.*;
//import Helper.SecurityHelper;
import java.security.MessageDigest;

public class LoginController extends HttpServlet
{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
    	  try{
                res.setContentType("text/html");
                RequestDispatcher view;
                
                Costumer cust = new Costumer();
                cust.setMail(req.getParameter("mail"));
                cust.setPassword(req.getParameter("password"));
                
              
                
                // Wenn keine fehlerhaften Eingaben vorhanden, Bestellung abschicken
                if(cust.getErrors().isEmpty()){
                    System.out.println("EMPTY");
                    //Basket b = (Basket)req.getSession().getAttribute("basket");
                    LoginModel login = new LoginModel();
                    User user = login.login(cust);
                    System.out.println("USERID: " + user.getUserID());
                    
                    
                    if(user.getUserID() > 0){
                        System.out.println("ID > 0");

                        req.getSession().setAttribute("user", user);
                        req.getSession().setAttribute("message", new Message(user.getUserID() +  " erfolgreich eingeloggt."));

                    }
                    
                    view = req.getRequestDispatcher("GameListController");  
                    res.sendRedirect("/UserModule/GameListController");
                    
                }
                // ansonsten rücksprung zum Formular mit Fehlermeldung
                else{
                    view = req.getRequestDispatcher("/WEB-INF/Pages/login.jsp");
                    req.setAttribute("customer", cust);
                }
                view.forward(req, res);
                      
          }
          catch(Exception e){}  
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