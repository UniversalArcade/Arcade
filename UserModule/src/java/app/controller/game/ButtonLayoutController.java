package app.controller.game;

import app.beans.Game;
import app.beans.ButtonLayout;
import app.beans.Message;
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
                
                
                ButtonLayout buttonLayout = new ButtonLayout();
                
                String action = req.getParameter("action");
                System.out.println("action: " + action);
                
                if( action != null ){
                    
                    Game game = (Game) req.getSession().getAttribute("game");
                    
                    if( action.equals("update") ){

                        int buttonAmount = 10; 

                        //String buttons = "";
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
                            
                            Message comboMessage = buttonLayout.testForIllegalCombinations();
                            
                            if(comboMessage == null){
                                System.out.println("BUTTONS VALID");
                                ButtonLayoutModel model = new ButtonLayoutModel();

                                if (model.updateButtonLayout(game) ){
                                    req.getSession().setAttribute("message", new Message("Button Layout erfolgreich bearbeitet"));
                                    res.sendRedirect("/UserModule/gameManager?component=buttonlayout");
                                }
                            }
                            else{
                                req.getSession().setAttribute("message", comboMessage);
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
        
        if(permission.isValid(req, "user") >= 0){
            this.processRequest(req,res);
        }
        else{
            req.getSession().setAttribute("message", new Message(Message.Type.ERROR,"Kein Zugriff"));
            req.getRequestDispatcher("/login").forward(req, res);
        }  
    }
}