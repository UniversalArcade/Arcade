/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.beans.Costumer;
import app.beans.Message;
import app.beans.User;
import app.model.LoginModel;
import app.model.ProfilModel;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author KM
 */


public class ProfilController extends HttpServlet {
     @Override
     //Wenn auf "übernehmen" geklickt wird

    /**
     *
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
         public void doPost(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
       {
        try{
               User u = (User)req.getSession().getAttribute("user");
               ProfilModel updateProfil = new ProfilModel();
               Costumer cust = new Costumer();
               cust.setMail(req.getParameter("mail"));
               cust.setPassword(req.getParameter("password"));
               updateProfil.updateUser(u,cust);
             
        }
        catch(Exception e){                     
          }
        req.getRequestDispatcher("/WEB-INF/Pages/profil.jsp").forward(req, res);
       }
     
     @Override
     //Wenn  auf "Profil" in der Navleiste geklickt wird
     protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
       req.getRequestDispatcher("/WEB-INF/Pages/profil.jsp").forward(req, res);
         
     }
} 

