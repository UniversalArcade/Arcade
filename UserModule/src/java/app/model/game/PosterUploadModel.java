/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.model.game;

import app.beans.Game;
import app.helper.FileUpload;
import app.model.GameManagerModel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.imgscalr.Scalr;

/**
 *
 * @author Martin
 */
public class PosterUploadModel {
    
    public void uploadImage(HttpServletRequest req, Game g){
        int width = 500;
        int height = 800;

        //Bild upload
        FileUpload upload = new FileUpload(5000 * 1024, 5000 * 1024, "C:/Users/Public/Arcade/Games/" + g.getGameID() + "/assets", "C:/Users/Public/Arcade/Games/" + g.getGameID() + "/tmp/");
        File fileStatus = upload.uploadFile(req);
        
        // Bild umwandeln
        File file = new File(fileStatus.getAbsolutePath());
        
        try {
            BufferedImage img = ImageIO.read(file);
            if(img.getWidth() != width || img.getHeight() != height){ 
                img = Scalr.resize(img, Scalr.Method.SPEED,Scalr.Mode.FIT_EXACT , 500, 800);
            }
            File destFile = new File(fileStatus.getParent() + "/" + g.getGameID() +".jpg");
            ImageIO.write(img, "jpg", destFile);
        } catch (IOException ex) {
            Logger.getLogger(GameManagerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        fileStatus.delete();
    }
    
}
