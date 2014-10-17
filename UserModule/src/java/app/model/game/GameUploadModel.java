package app.model.game;

import app.beans.Game;
import app.helper.FileUpload;
import app.helper.UnZip;
import java.io.File;
import javax.servlet.http.HttpServletRequest;


public class GameUploadModel {
    
     public void uploadGame(HttpServletRequest req, Game g){
        //int maxFileSize, int maxMemSize, String saveFolder, String tempFolder
        FileUpload upload = new FileUpload(5000 * 1024, 5000 * 1024, "C:/Users/Public/Arcade/Games/" + g.getGameID(), "C:/Users/Public/Arcade/Games/" + g.getGameID() + "/tmp/");
        File file = upload.uploadFile(req);
       
        UnZip.unzipit(file, file.getParent() + "/game");
        
        if( file.delete() ){
            System.out.println("gelöscht");
        }
        else{
            System.out.println("NICHT GELÖSCHT");
        }
    } 
}
