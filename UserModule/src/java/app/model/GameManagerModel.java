package app.model;

import app.beans.Game;
import app.beans.GameComponents;
import app.helper.SQLHelper;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameManagerModel {
    
    public String mkDir(String path){
        File folder = new File(path);
    	if(!folder.exists()){
    		folder.mkdir();
    	}
        
        return path; 
    }
    
    public Game insertNewGame(int userID) throws SQLException{
        
        Game game = new Game();
        GameComponents gc = new GameComponents();
        
        for( String key : gc.getComponents().keySet()){
            game.addState(key, "incomplete");
        }
        String states = game.stateToJSON();
         
        for(int i=0; i<10; i++){
            game.addButton("unused", "");
        }
        
        try(SQLHelper sql = new SQLHelper()){
            sql.execNonQuery("INSERT INTO `games` (userID,editState,buttonConfig) VALUES ('"+userID+"','"+states+"','"+game.buttonLayoutToJSON()+"')");
            int gameID = sql.getLastID();

            game.setGameID(gameID);
        
            String baseDir = mkDir("C:/Users/Public/Arcade/Games/" + gameID);
            mkDir(baseDir + "/game");
            mkDir(baseDir + "/assets");
            mkDir(baseDir + "/tmp");
        }
        return game;
    }
    
    public void toggleLive(int toggle, Game g) throws SQLException{
       
        try(SQLHelper sql = new SQLHelper()){
            sql.execNonQuery("UPDATE `games` SET live = '"+toggle+"' WHERE ID = "+ g.getGameID());
        }
    }
    
    
    public void toggleEditMode(int toggle, Game g) throws SQLException{
        
        try(SQLHelper sql = new SQLHelper()){
            sql.execNonQuery("UPDATE `games` SET editMode = '"+toggle+"' WHERE ID = "+ g.getGameID());
        }
    }        
            
   
   public void deleteGame(Game g) throws SQLException{
        try(SQLHelper sql = new SQLHelper()){
            sql.execNonQuery("DELETE FROM games WHERE ID = "+ g.getGameID());
        }
    }
    
    public Game getGameByID(int gameID, int userID) throws SQLException, IllegalArgumentException{
        Game g = new Game();
        
        try(SQLHelper sql = new SQLHelper()){
            ResultSet rs = sql.execQuery("SELECT title,description,buttonConfig,credits,gameDuration,gameStarts,permanentStore,isEmulatorGame,editMode,editState FROM games WHERE ID='"+gameID+"' AND userID ='"+userID+"'");
            if(!rs.next()) throw new IllegalArgumentException();

            g.setGameID(gameID);
            g.setTitle(rs.getString("title"));
            g.setDescription(rs.getString("description"));
            g.JSONToButtonLayout(rs.getString("buttonConfig"));
            g.setCredits(rs.getString("credits"));
            g.setGameDuration(rs.getInt("gameDuration"));
            g.setGameStarts(rs.getInt("gameStarts"));
            g.setPermanentStore(rs.getInt("permanentStore"));
            g.setEmulationGame(rs.getInt("isEmulatorGame"));
            g.JSONToState(rs.getString("editState"));

            int editMode = rs.getInt("editMode");
            if(editMode == 1){
                g.setInEditMode(true);
            }
            else{
                g.setInEditMode(false);
            }
        }
        return g;
    }
}
