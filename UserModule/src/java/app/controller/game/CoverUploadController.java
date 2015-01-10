package app.controller.game;


import app.beans.User;
import app.beans.Game;
import app.beans.Message;
import app.helper.Permission;
import app.model.game.CoverUploadModel;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import app.model.GameManagerModel;
import java.sql.SQLException;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;

public class CoverUploadController extends HttpServlet
{
    public void processRequest(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
    	  try{
                res.setContentType("text/html");
                String action = req.getParameter("action");
                System.out.println("action: " + action);
                
                if( action != null ){
                    
                    Game game = (Game) req.getSession().getAttribute("game");
                    
                    Message message = new Message();
                    
                    if( action.equals("update") ){
                        
                        String contentType = req.getContentType();
                        if ( (contentType.indexOf("multipart/form-data") >= 0) ) {
                             CoverUploadModel model = new CoverUploadModel();
                             
                             try{
                                model.uploadImage(req, game);
                                message.addMessage(Message.Type.SUCCESS, "Cover erfolgreich hochgeladen");
                                System.out.println("ALLES GUT");
                                res.sendRedirect("/UserModule/gameManager?component=coverupload");
                             }
                             catch (FileUploadBase.SizeLimitExceededException e ){ message.addMessage(Message.Type.ERROR, "Datei zu groß! maximale Dateigröße: 2MB"); }
                             catch (FileUploadException e){ message.addMessage(Message.Type.ERROR, "Datei zu groß! maximale Dateigröße: 2MB"); } 
                             catch (SQLException e) { message.addMessage(Message.Type.ERROR, "Datenbankfehler: " + e.getMessage()); }
                             catch (Exception e) { message.addMessage(Message.Type.ERROR, "Fehler beim upload "); }
                             
                             finally{
                                 req.getSession().setAttribute("message",message);
                             }
                        }else{
                            req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Fehler : falscher ContentType"));
                        } 
                    }
                }
                req.getRequestDispatcher("/WEB-INF/Pages/Game/coverUpload.jsp").forward(req, res);
          }
          catch(Exception e){}  
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