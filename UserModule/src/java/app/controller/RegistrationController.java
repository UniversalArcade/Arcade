// Controller um Bestellung abzuschließen

package app.controller;

import app.model.RegistrationModel;

import app.beans.Costumer;
import app.beans.Message;
import app.helper.SecurityHelper;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
//import Helper.SecurityHelper;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrationController extends HttpServlet
{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
    	  
              
                //SecurityHelper sec = new SecurityHelper();
                //System.out.println(sec.getSHAHash("abc"));
                
               
                // SHA Experiment
                /*
                MessageDigest md = MessageDigest.getInstance( "SHA" );
                byte[] digest = md.digest( "ABCDEFGHIJKLMNO".getBytes() );
                String msg = "";
                for ( byte b : digest )
                    msg += b; // funzt so nicht
                
                System.out.println("AHA: " + msg);
                //System.out.println("JA : " + digest.toString());
                
                */        
                        
                Costumer cust = new Costumer();
                cust.setMail(req.getParameter("mail"));
                
                String test = req.getParameter("password");
                String SHATest = SecurityHelper.getSHAHash(test);
                
                cust.setPassword(SHATest);
                cust.setPasswordWDH(SHATest);
                
                System.out.println(cust.getErrors());
                if( cust.getErrors().isEmpty() ){
                    RegistrationModel register = new RegistrationModel();
                    cust = register.newRegistration(cust) ;
                    if ( cust.getErrors().isEmpty() ){
                        cust.setRegistrationComplete();
                    }                   
                }               
                req.setAttribute("customer", cust);
                req.getRequestDispatcher("/WEB-INF/Pages/register.jsp").forward(req, res);         
         
    }
    
 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
                
                String url = req.getParameter("unique");       
                if( url == null){
                    req.getRequestDispatcher("/WEB-INF/Pages/register.jsp").forward(req, res);
                    } 
                else {
                    RegistrationModel registerChange = new RegistrationModel();
                    try {
                        registerChange.activateUser(url);
                        
                        req.getSession().setAttribute("message", new Message("Ihre Email-Adresse wurde bestätigt. Sie können sich nun einloggen!"));
                        res.sendRedirect("/UserModule/login");  
                        
                        //Nachricht hier wieder entfernen ?!
                    } 
                    catch (Exception ex) {
                    Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    
                 }           
    }
}