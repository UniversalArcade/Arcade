// Holt Games aus der Datenbank und stellt die als Liste da

package app.model;


import app.beans.User;
import app.beans.GamesList;

import app.helper.SQLHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameListModel {
    
    
    public GamesList listGames(User u){
        
        GamesList gl = new GamesList();
        
        try(SQLHelper sql = new SQLHelper()){
        
            ResultSet rs = sql.execQuery("SELECT ID, title ,live FROM games WHERE userID='"+u.getUserID()+"'");
            while(rs.next()){
                gl.addGame(rs.getInt("ID"),rs.getString("title"),rs.getInt("live"));
            }
        }
        catch(SQLException e){}
        
        return gl;
        
    }
    
    public void deleteGame(){
        
    }
}
