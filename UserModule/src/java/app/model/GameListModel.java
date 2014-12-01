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
    SQLHelper sql;
    
    public GameListModel(){
        sql = new SQLHelper();
    }
    
    public GamesList listGames(User u) {
        
        GamesList gl = new GamesList();
        
        sql.openCon();
            
            ResultSet rs = sql.execQuery("SELECT ID, title FROM games WHERE userID='"+u.getUserID()+"'");
            try {
                while(rs.next()){
                    gl.addGame(rs.getInt("ID"),rs.getString("title"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(GameListModel.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        sql.closeCon();
        
        return gl;
        
    }
    
    public void deleteGame(){
        
    }
}
