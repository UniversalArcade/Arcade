package app.controller.game;

import app.beans.Game;
import app.beans.ButtonLayout;
import app.helper.Permission;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import app.model.game.ButtonLayoutModel;

public class ButtonLayoutController extends HttpServlet
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
                    
                    if( action.equals("update") ){

                        int buttonAmount = 10; 

                        String buttons = "";
                        boolean valid = true;

                        for(int i = 1; i <= buttonAmount; i++){
                            String button = req.getParameter("button" + i);
                            if (button == null){
                                valid = false;
                                break;
                            }

                            buttons += req.getParameter("button" + i);
                            if(i < buttonAmount){
                                buttons += ";"; 
                            }
                        }

                        if(valid){
                            System.out.println("BUTTONS VALID");
                             ButtonLayoutModel model = new ButtonLayoutModel();

                             if (model.updateButtonLayout(buttons, game) ){
                                 res.sendRedirect("/UserModule/gameManager?component=buttonlayout");
                             }
                        }
                    }
                }
                //work in progress...
                req.setAttribute("buttons", new ButtonLayout());
                req.getRequestDispatcher("/WEB-INF/Pages/Game/buttonLayout.jsp").forward(req, res);
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
        
        if(permission.isValid(req, "user")){
            this.processRequest(req,res);
        }
        else{
            req.getRequestDispatcher("/WEB-INF/Pages/login.jsp").forward(req, res);
        }  
    }
}