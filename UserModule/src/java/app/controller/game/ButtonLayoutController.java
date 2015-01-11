package app.controller.game;

import app.beans.Game;
import app.beans.ButtonLayout;
import app.beans.Message;
import app.exceptions.IllegalButtonCombinationException;
import app.helper.Permission;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import app.model.game.ButtonLayoutModel;
import java.sql.SQLException;

public class ButtonLayoutController extends HttpServlet
{
    public void processRequest(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
            res.setContentType("text/html");

            ButtonLayout buttonLayout = new ButtonLayout();
            req.setAttribute("buttons", new ButtonLayout());
           
            String action = req.getParameter("action");  
            if( action != null && action.equals("update") ){
                Game game = (Game) req.getSession().getAttribute("game");

                int buttonAmount = 10; 
                boolean valid = true;
                game.flushButtonLayout();

                for(int i = 1; i <= buttonAmount; i++){
                    String button = req.getParameter("button" + i);
                   
                    if (button == null){
                        valid = false;
                        break;
                    }

                    if(button.equals("unused")){
                        game.addButton("unused","");
                    }
                    else{
                        String function = req.getParameter("function" + i);
                        game.addButton(button, function);
                        buttonLayout.addButtonForIllegalTest(button); 
                    }
                }
              
                if(valid){
                    boolean success = false;
                    try{
                        buttonLayout.testForIllegalCombinations();
                        ButtonLayoutModel model = new ButtonLayoutModel();
                        model.updateButtonLayout(game);
                        req.getSession().setAttribute("message", new Message("Button Layout erfolgreich bearbeitet"));
                        success = true;
                    }
                    catch(IllegalButtonCombinationException e){
                       req.getSession().setAttribute("message", e.getCustomMessage()); 
                    }
                    catch(SQLException e){
                        req.getSession().setAttribute("message", new Message(Message.Type.ERROR, "Datenbankfehler " + e.getMessage()));
                    }
                    finally{
                        if(success) res.sendRedirect("/UserModule/gameManager?component=buttonlayout");
                        else req.getRequestDispatcher("/WEB-INF/Pages/Game/buttonLayout.jsp").forward(req, res);
                    }
                }
                else{
                    req.getRequestDispatcher("/WEB-INF/Pages/Game/buttonLayout.jsp").forward(req, res);
                }
            }
            else{
                req.getRequestDispatcher("/WEB-INF/Pages/Game/buttonLayout.jsp").forward(req, res);
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