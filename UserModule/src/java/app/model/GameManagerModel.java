
package app.model;


import app.beans.Game;
import app.helper.SQLHelper;
import java.io.File;


public class GameManagerModel {
    
    public GameManagerModel(){
       
    }
    

    public String mkDir(String path){
        File folder = new File(path);
    	if(!folder.exists()){
    		folder.mkdir();
    	}
        
        return path; 
    }
    
    public int insertNewGame(int userID){
        SQLHelper sql = new SQLHelper();
        
        sql.openCon();
            sql.execNonQuery("INSERT INTO `games` (userID) VALUES ('"+userID+"')");
            int gameID = sql.getLastID();
        sql.closeCon();
        
        String baseDir = mkDir("C:/Users/Public/Arcade/Games/" + gameID);
        mkDir(baseDir + "/game");
        mkDir(baseDir + "/assets");
        mkDir(baseDir + "/tmp");
         
        return gameID;
    }
    
    public boolean toggleLive(int toggle, Game g){
        SQLHelper sql = new SQLHelper();
        
        sql.openCon();
          boolean success = sql.execNonQuery("UPDATE `games` SET live = '"+toggle+"' WHERE ID = "+ g.getGameID());
        sql.closeCon();
        
        return success;
    }
   
    public void deleteGame(){
    
    }
    
}
