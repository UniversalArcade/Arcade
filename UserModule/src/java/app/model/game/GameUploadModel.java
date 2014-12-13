package app.model.game;

import app.beans.Game;
import app.helper.FileUpload;
import app.helper.SQLHelper;
import app.helper.UnZip;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;


public class GameUploadModel {
    
     public void uploadGame(HttpServletRequest req, Game g){
        
         if(g.isInEditMode()){
            File delete = null;
             
            if(g.getEmulationGame() == 0){
                delete = new File("C:/Users/Public/Arcade/Games/" + g.getGameID() + "/game");
            }
            else{
                delete = new File("C:/Users/Public/Arcade/Mame/roms/" + g.getTitle());
            }
             
            try {
                FileUtils.deleteDirectory(delete);
            } catch (IOException ex) {
                Logger.getLogger(GameUploadModel.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
         
        //int maxFileSize, int maxMemSize, String saveFolder, String tempFolder
        FileUpload upload = new FileUpload(5000 * 1024, 5000 * 1024, "C:/Users/Public/Arcade/Games/" + g.getGameID(), "C:/Users/Public/Arcade/Games/" + g.getGameID() + "/tmp/");
        File file = upload.uploadFile(req);
       
        if(g.getEmulationGame() == 1){
            UnZip.unzipit(file, "C:/Users/Public/Arcade/Mame/roms");
        }
        else{
            UnZip.unzipit(file, file.getParent() + "/game");
        }
        
        
        if( file.delete() ){
            System.out.println("gelöscht");
        }
        else{
            System.out.println("NICHT GELÖSCHT");
        }
        
        g.updateState("gameupload", "complete");
        String state = g.stateToJSON();
        
        SQLHelper sql = new SQLHelper();    
        sql.openCon();
           boolean success = sql.execNonQuery("UPDATE `games` SET editState='"+state+"' WHERE ID = "+ g.getGameID());  
        sql.closeCon();
        
    } 
}
