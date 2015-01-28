package app.controller.game;

import app.beans.Game;
import app.beans.Message;
import app.helper.Permission;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import app.model.game.GameUploadModel;
import java.sql.SQLException;
import java.util.zip.ZipException;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;

public class GameUploadController extends HttpServlet
{
    public void processRequest(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
            res.setContentType("text/html");

            String action = req.getParameter("action");
            System.out.println("action: " + action);

            if( action != null && action.equals("update")){

                if ((req.getContentType().contains("multipart/form-data"))) {
                    Game game = (Game) req.getSession().getAttribute("game");

                    boolean success = false;
                    try{
                        GameUploadModel model = new GameUploadModel();
                        model.uploadGame(req, game);
                        req.getSession().setAttribute("message", new Message("Spiel erfolgreich hochgeladen"));
                        success = true;
                    }
                    catch (ZipException e){
                        req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Fehler beim entzippen!"));                                 
                    }
                    catch (FileUploadBase.SizeLimitExceededException e ){
                        req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Datei zu groß! maximale Dateigröße: 1GB"));                                 
                    }
                    catch (FileUploadException e){ 
                        req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Fehler beim Upload"));                                 
                    }
                    catch (SQLException e){
                        req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Datenbankfehler " + e.getMessage()));                                 
                    }
                    catch (Exception e) { 
                        req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Unbekannter Fehler beim Upload :" + e.getMessage()));                                 
                    }
                    finally{
                        if(success) res.sendRedirect("/UserModule/gameManager?component=gameupload");
                        else req.getRequestDispatcher("/WEB-INF/Pages/Game/gameUpload.jsp").forward(req, res);
                    } 

                }else{
                   req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Fehler : falscher ContentType"));
                   req.getRequestDispatcher("/WEB-INF/Pages/Game/gameUpload.jsp").forward(req, res);
                }  
            }
            else{
                req.getRequestDispatcher("/WEB-INF/Pages/Game/gameUpload.jsp").forward(req, res);
            }
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