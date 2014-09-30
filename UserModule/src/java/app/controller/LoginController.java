// Controller um Bestellung abzuschließen

package app.controller;

import app.model.LoginModel;

import app.beans.Costumer;
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
                    int id = login.login(cust);
                    
                    if(id > 0){
                        System.out.println("ID > 0");
                        //Basket b = (Basket)req.getSession().getAttribute("basket");
                        req.getSession().setAttribute("userID", id);
                        //req.getSession().setAttribute("userLvl", id);
                    }
                    
                    view = req.getRequestDispatcher("games.jsp");  
                }
                // ansonsten rücksprung zum Formular mit Fehlermeldung
                else{
                    view = req.getRequestDispatcher("register.jsp");
                    req.setAttribute("customer", cust);
                }
                view.forward(req, res);
                      
          }
          catch(Exception e){}  
    }
    
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
    }
}