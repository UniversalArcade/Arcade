
package app.model;
import app.helper.SQLHelper;
import app.helper.FileUpload;
import app.helper.UnZip;
import app.beans.FileStatus;

import app.beans.Game;
import javax.servlet.http.HttpServletRequest;


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
    
    public void uploadPicture(HttpServletRequest req){
        //int maxFileSize, int maxMemSize, String saveFolder, String tempFolder
        FileUpload upload = new FileUpload(5000 * 1024, 5000 * 1024, "c:\\FileUploadTest\\", "c:\\temp");
        FileStatus file = upload.uploadFile(req);
        
    }
    
    
    public int insertNewGame(int userID){
        
        
        sql.openCon();
            sql.execNonQuery("INSERT INTO `games` (userID) VALUES ('"+userID+"')");
          int gameID = sql.getLastID();
            
        sql.closeCon();
        
        return gameID;
    }
    
    public void updateDetails(){
        /*
        sql.openCon();
            sql.execNonQuery("INSERT INTO `games` (userID,title,description,credits) VALUES ('"+userID+"', '"+g.getTitle()+"', '"+g.getDescription()+"', '"+g.getCredits()+"')");
        sql.closeCon(); */
    }
    
    public void updateGame(){
    
    }
    
    public void deleteGame(){
    
    }
    
}
