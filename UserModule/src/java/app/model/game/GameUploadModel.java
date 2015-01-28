package app.model.game;

import app.beans.Game;
import app.helper.FileUpload;
import app.helper.SQLHelper;
import app.helper.UnZip;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.zip.ZipException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;


public class GameUploadModel {
    
     public void uploadGame(HttpServletRequest req, Game g) throws ZipException, FileUploadBase.SizeLimitExceededException, FileUploadException, SQLException, Exception {
        
        //int maxFileSize, int maxMemSize, String saveFolder, String tempFolder
        FileUpload upload = new FileUpload(2* 500000 * 1024, 5000 * 1024, "C:/Users/Public/Arcade/Games/" + g.getGameID(), "C:/Users/Public/Arcade/Games/" + g.getGameID() + "/tmp/");
         //try {
             File file = upload.uploadFile(req);
             if(file != null){
                FileUtils.cleanDirectory(new File("C:/Users/Public/Arcade/Games/" + g.getGameID() + "/game")); 
                ExeChooserModel exe = new ExeChooserModel();
                 
                if(g.getEmulationGame() == 1){ 
                    String fileName = FilenameUtils.getBaseName(file.getAbsolutePath());
                    exe.updateExePath(fileName, g);
                    
                    File destCopy = new File(file.getParent() + "/game/");
                    //try{
                        FileUtils.moveFileToDirectory(file, destCopy, true);
                    //}
                    //catch(IOException | NullPointerException n){ return -4; }
                }
                else{
                    UnZip.unzipit(file, file.getParent() + "/game");
                    
                    exe.updateExePath("", g);
                    g.setInEditMode(false);
                    g.setLife(0); 
                    FileUtils.deleteQuietly(file);
                }
                
                g.updateState("gameupload", "complete");
                String state = g.stateToJSON();

                try(SQLHelper sql = new SQLHelper()){
                    sql.execNonQuery("UPDATE `games` SET editState='"+state+"', editMode='"+(g.isInEditMode() ? 1:0)+"', live='"+g.getLife()+"' WHERE ID = "+ g.getGameID());  
                }
            }   
         //} 
        
         
      //return 1;  
    } 
}
