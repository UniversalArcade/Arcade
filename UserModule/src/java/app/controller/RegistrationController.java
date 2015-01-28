

package app.controller;

import app.model.RegistrationModel;

import app.beans.Costumer;
import app.beans.Message;
import app.exceptions.EmailAlreadyInUseException;
import app.helper.MailHelper;
import app.helper.SecurityHelper;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.SQLException;
import javax.mail.MessagingException;

public class RegistrationController extends HttpServlet
{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
     
            Costumer cust = new Costumer();
            Message message = new Message();

            String uncheckedMail =  req.getParameter("mail");
            boolean checked = MailHelper.checkFormat(req.getParameter("mail"));
            cust.setMail(req.getParameter("mail"));
            if(!checked){
               cust.addError("mail", ""); 
               message.addMessage(Message.Type.ERROR, "Es können nur @haw-hamburg.de Email Adressen genutzt werden.");
            }

            String nonSHA = req.getParameter("password");
            String nonSHAPW = req.getParameter("passwordWDH");
            String SHAPW = SecurityHelper.getSHAHash(nonSHA);
            boolean PWH =  SecurityHelper.checkPW(nonSHA, nonSHAPW);
            if(!PWH){
               cust.addError("mail", "");               
               message.addMessage(Message.Type.ERROR, "Das eingegeben Passwort stimmt nicht überein.");
            }
            boolean PWL = SecurityHelper.checkPWCorrect(nonSHA);
            if(!PWL){
                   cust.addError("mail", "");               
                   message.addMessage(Message.Type.ERROR, "Das Passwort muss zwischen 8-30 Zeichen lang sein und min 1 Sonderzeichen(!?@#$%^&+=) sowie min 1 Zahl enthalten.");
            }
            System.out.println(cust.getErrors());
            if( cust.getErrors().isEmpty() ){
                cust.setPassword(SHAPW);
                cust.setPasswordWDH(SHAPW);

                RegistrationModel register = new RegistrationModel();
                try{
                    cust = register.newRegistration(cust);
                }
                catch(EmailAlreadyInUseException e){ 
                    cust.addError("mail", "E-mail-Adresse existiert bereits"); 
                }
                catch(MessagingException e){ 
                    message.addMessage(Message.Type.ERROR, "Fehler beim versenden der mail!"); 
                }
                catch(SQLException e){ 
                    message.addMessage(Message.Type.ERROR, "Datenbankfehler: " + e.getMessage()); 
                }
            }
            req.getSession().setAttribute("message",message);
            req.setAttribute("customer", cust);
            req.getRequestDispatcher("/WEB-INF/Pages/register.jsp").forward(req, res);         

    }
    
 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
                
            boolean success = false;
            try{
                RegistrationModel model = new RegistrationModel();
                model.activateUser( req.getParameter("unique") );
                req.getSession().setAttribute("message", new Message("Ihre Email-Adresse wurde bestätigt. Sie können sich nun einloggen!"));
                success = true;
            }
            catch(IllegalArgumentException e){
                req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "fehlerhafte ID"));
            }
            catch(SQLException e){
                req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Datenbankfehler: " + e.getMessage()));
            }
            catch(NullPointerException e){ }
            finally{
                if(success) res.sendRedirect("/UserModule/login");  
                else req.getRequestDispatcher("/WEB-INF/Pages/register.jsp").forward(req, res);
            } 
    }
}

