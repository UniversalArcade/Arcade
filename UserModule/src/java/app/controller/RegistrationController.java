// Controller um Bestellung abzuschließen

package app.controller;

import app.model.RegistrationModel;

import app.beans.Costumer;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
//import Helper.SecurityHelper;
import java.security.MessageDigest;

public class RegistrationController extends HttpServlet
{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
    	  try{
              
                //SecurityHelper sec = new SecurityHelper();
                //System.out.println(sec.getSHAHash("abc"));
                boolean error = false;
              
                MessageDigest md = MessageDigest.getInstance( "SHA" );
                byte[] digest = md.digest( "ABCDEFGHIJKLMNO".getBytes() );
                String msg = "";
                for ( byte b : digest )
                    msg += b; // funzt so nicht
                
                System.out.println("AHA: " + msg);
                //System.out.println("JA : " + digest.toString());
                
                
                res.setContentType("text/html");
                RequestDispatcher view;
                
                Costumer cust = new Costumer();
                cust.setMail(req.getParameter("mail"));
                cust.setPassword(req.getParameter("password"));
                cust.setPasswordWDH(req.getParameter("passwordWDH"));
        
                
                if( cust.getErrors().isEmpty() ){
                    RegistrationModel register = new RegistrationModel();
                    cust = register.newRegistration(cust) ;
                    
                    if ( cust.getErrors().isEmpty() ){
                        cust.setRegistrationComplete();
                    }
                    
                }
                
                view = req.getRequestDispatcher("register.jsp");
                req.setAttribute("customer", cust);
                
                view.forward(req, res);
                      
          }
          catch(Exception e){}  
    }
    
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
    }
}