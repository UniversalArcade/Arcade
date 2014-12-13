
package app.model;


import app.beans.Game;
import app.beans.GameComponents;
import app.beans.User;
import app.helper.SQLHelper;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



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
    
    public Game insertNewGame(int userID){
        SQLHelper sql = new SQLHelper();
        Game game = new Game();
        GameComponents gc = new GameComponents();
        
        for( String key : gc.getComponents().keySet()){
            game.addState(key, "incomplete");
        }
        
        String states = game.stateToJSON();
                
        sql.openCon();
            sql.execNonQuery("INSERT INTO `games` (userID,editState) VALUES ('"+userID+"','"+states+"')");
            int gameID = sql.getLastID();
        sql.closeCon();
        
        game.setGameID(gameID);
        
        String baseDir = mkDir("C:/Users/Public/Arcade/Games/" + gameID);
        mkDir(baseDir + "/game");
        mkDir(baseDir + "/assets");
        mkDir(baseDir + "/tmp");
         
        return game;
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
    
    public Game getGameByID(int gameID, int userID) {
        Game g = new Game();
        SQLHelper sql = new SQLHelper();
        
        sql.openCon();
            
            ResultSet rs = sql.execQuery("SELECT title,description,buttonConfig,credits,gameDuration,gameStarts,permanentStore,isEmulatorGame FROM games WHERE ID='"+gameID+"' AND userID ='"+userID+"'");
            try {
                if(rs.next()){
                    g.setGameID(gameID);
                    g.setTitle(rs.getString("title"));
                    g.setDescription(rs.getString("description"));
                    g.setButtonConfig(rs.getString("buttonConfig"));
                    g.setCredits(rs.getString("credits"));
                    g.setGameDuration(rs.getInt("gameDuration"));
                    g.setGameStarts(rs.getInt("gameStarts"));
                    g.setPermanentStore(rs.getInt("permanentStore"));
                    g.setEmulationGame(rs.getInt("isEmulatorGame"));
                }
                else{
                    g = null;
                }
            } catch (SQLException ex) {
                Logger.getLogger(GameManagerModel.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        sql.closeCon();
        
        return g;
        
    }
    
}
