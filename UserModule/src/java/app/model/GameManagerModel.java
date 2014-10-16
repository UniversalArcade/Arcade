
package app.model;

import app.beans.FileStatus;
import app.beans.Game;
import app.helper.FileUpload;
import app.helper.SQLHelper;
import app.helper.UnZip;
import app.helper.UnZip4j;
import app.helper.UnZipCommons;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.imgscalr.Scalr;
import org.json.simple.JSONArray;

public class GameManagerModel {
    SQLHelper sql;
    public GameManagerModel(){
        sql = new SQLHelper();
    }
    
    public void uploadGame(HttpServletRequest req, Game g){
        //int maxFileSize, int maxMemSize, String saveFolder, String tempFolder
        FileUpload upload = new FileUpload(5000 * 1024, 5000 * 1024, "C:/Users/Public/Arcade/Games/" + g.getGameID(), "C:/Users/Public/Arcade/Games/" + g.getGameID() + "/tmp/");
        File file = upload.uploadFile(req);
        
        /*UnZip unZip = new UnZip();
        
        System.out.println("model - fileName: " + file.getAbsolutePath());
        System.out.println("model - Parent: " + file.getParent());
        
        unZip.unZipIt(file, file.getParent());
        */
        
        // Unzip with Zip4j
        UnZip4j.unzip(file.getAbsolutePath(), file.getParent() + "/game");
        
        // Unzip with commons IO
        // UnZipCommons.unzip(file, file.getParent() + "/game");
        
        
            if( file.delete() ){
                System.out.println("gelöscht");
            }
            else{
                System.out.println("NICHT GELÖSCHT");
            }
            
        
    }
    
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
    
    
    public String mkDir(String path){
        File folder = new File(path);
    	if(!folder.exists()){
    		folder.mkdir();
    	}
        
        return path;
        
    }
    
    public int insertNewGame(int userID){
        sql.openCon();
            sql.execNonQuery("INSERT INTO `games` (userID) VALUES ('"+userID+"')");
            int gameID = sql.getLastID();
        sql.closeCon();
        
        String baseDir = mkDir("C:/Users/Public/Arcade/Games/" + gameID);
        mkDir(baseDir + "/game");
        mkDir(baseDir + "/assets");
        mkDir(baseDir + "/tmp");
        
        
        
        
        
        /*
        File folder = new File("C:/Users/Public/Arcade/Games/" + gameID);
    	if(!folder.exists()){
    		folder.mkdir();
    	}
        */
        
        
        //System.out.println("Folder: " + folder.getAbsolutePath());
        
        
        return gameID;
    }
    
    public boolean updateDetails(Game g){
        sql.openCon();
          boolean success = sql.execNonQuery("UPDATE `games` SET title = '"+g.getTitle()+"', description = '"+g.getDescription()+"', credits = '"+g.getCredits()+"', permanentStore = '"+g.getPermanentStore()+"' WHERE ID = "+ g.getGameID());
        sql.closeCon();
        
        return success;
    }
    
    public boolean updateButtonLayout(String buttons, Game g){
        sql.openCon();
          boolean success = sql.execNonQuery("UPDATE `games` SET buttonConfig = '"+buttons+"' WHERE ID = "+ g.getGameID());
        sql.closeCon();
        
        return success;
    }
    
    public boolean updateExePath(String path, Game g){
        sql.openCon();
          boolean success = sql.execNonQuery("UPDATE `games` SET executePath = '"+path+"' WHERE ID = "+ g.getGameID());
        sql.closeCon();
        
        return success;
    }
    
    public Game getFileStructureAsJSON(Game g){
        
        JSONArray jsonarr = listfJSON("C:\\Users\\Public\\Arcade\\Games\\" + g.getGameID() + "\\game");
        g.setFilePathJSON(jsonarr);
        return g;
    }
    
    
    public JSONArray listfJSON(String directoryName) {
        JSONArray files = new JSONArray();
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                Map f = new LinkedHashMap();
                f.put("type", "file");
                f.put("name", file.getName());
                files.add(f);
            } 
            
            else if (file.isDirectory()) {
                Map folder = new LinkedHashMap();
                folder.put("type", "folder");
                folder.put("name", file.getName());
                folder.put("child", listfJSON(file.getAbsolutePath()));
                //folder.put(file.getName(), listfJSON(file.getAbsolutePath()));
                files.add(folder);
            }
        }
        
        return files;
    }
    
    public boolean toggleLive(int toggle, Game g){
        sql.openCon();
          boolean success = sql.execNonQuery("UPDATE `games` SET live = '"+toggle+"' WHERE ID = "+ g.getGameID());
        sql.closeCon();
        
        return success;
    }
   
    public void deleteGame(){
    
    }
    
}
