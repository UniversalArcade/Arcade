package app.controller;

import app.model.GameUploadModel;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GameUploadController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
    
        String contentType = req.getContentType();
        if ((contentType.indexOf("multipart/form-data") >= 0)) {
             GameUploadModel model = new GameUploadModel();
             model.uploadGame(req);
        }else{
            // Fehler beim hochladen
        }    
    }
}
