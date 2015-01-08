/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controller;

import app.beans.Game;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class getImageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

                res.setContentType("image/jpeg");
		Game game = (Game) req.getSession().getAttribute("game");
                
                StringBuilder filepath = new StringBuilder();
                filepath.append("C:\\Users\\Public\\Arcade\\Games\\");
                filepath.append(game.getGameID());
                filepath.append("\\assets\\");
                filepath.append(game.getGameID());
                filepath.append(".jpg");
                
                File f = new File(filepath.toString());
		BufferedImage bi = ImageIO.read(f);
                
                try (OutputStream out = res.getOutputStream()) {
                    ImageIO.write(bi, "jpg", out);
                }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
