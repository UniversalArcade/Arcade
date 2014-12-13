package app.model.game;

import app.beans.Game;
import app.helper.FileUpload;
import app.helper.SQLHelper;
import app.helper.UnZip;
import java.io.File;
import javax.servlet.http.HttpServletRequest;


public class GameUploadModel {
    
     public void uploadGame(HttpServletRequest req, Game g){
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
