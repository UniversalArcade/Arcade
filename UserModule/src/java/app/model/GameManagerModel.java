
package app.model;

import app.beans.FileStatus;
import app.beans.Game;
import app.helper.FileUpload;
import app.helper.SQLHelper;
import app.helper.UnZip;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.imgscalr.Scalr;


public class GameManagerModel {
    SQLHelper sql;
    public GameManagerModel(){
        sql = new SQLHelper();
    }
    
    
    public void uploadGame(HttpServletRequest req){
        //int maxFileSize, int maxMemSize, String saveFolder, String tempFolder
        FileUpload upload = new FileUpload(5000 * 1024, 5000 * 1024, "c:\\FileUploadTest\\", "c:\\temp");
        FileStatus file = upload.uploadFile(req);
        UnZip unZip = new UnZip();
        unZip.unZipIt(file.getFullPath(), file.getFilePath());
    }
    
    public void uploadImage(HttpServletRequest req, Game g){
        int width = 500;
        int height = 800;

        //Bild upload
        FileUpload upload = new FileUpload(5000 * 1024, 5000 * 1024, "c:\\FileUploadTest\\", "c:\\temp");
        FileStatus fileStatus = upload.uploadFile(req);
        
        // Bild umwandeln
        File file = new File(fileStatus.getFullPath());
        
        try {
            BufferedImage img = ImageIO.read(file);
            if(img.getWidth() != width || img.getHeight() != height){ 
                img = Scalr.resize(img, Scalr.Method.SPEED,Scalr.Mode.FIT_EXACT , 500, 800);
            }
            File destFile = new File(fileStatus.getFilePath() + g.getGameID() +".jpg");
            ImageIO.write(img, "jpg", destFile);
        } catch (IOException ex) {
            Logger.getLogger(GameManagerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public int insertNewGame(int userID){
        
        
        sql.openCon();
            sql.execNonQuery("INSERT INTO `games` (userID) VALUES ('"+userID+"')");
            int gameID = sql.getLastID();
        sql.closeCon();
        
        return gameID;
    }
    
    public boolean updateDetails(Game g){
        sql.openCon();
          boolean success = sql.execNonQuery("UPDATE `games` SET title = '"+g.getTitle()+"', description = '"+g.getDescription()+"', credits = '"+g.getCredits()+"' WHERE ID = "+ g.getGameID());
        sql.closeCon();
        
        return success;
    }
    
    public boolean updateButtonLayout(String buttons, Game g){
        
       
        
        sql.openCon();
          boolean success = sql.execNonQuery("UPDATE `games` SET buttonConfig = '"+buttons+"' WHERE ID = "+ g.getGameID());
        sql.closeCon();
        
        return success;
        
    }
    
    
    public void updateGame(){
    
    }
    
    public void deleteGame(){
    
    }
    
}
