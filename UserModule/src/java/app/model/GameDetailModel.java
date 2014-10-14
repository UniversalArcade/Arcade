// Holt Game aus der Datenbank und stellt die Details da

package app.model;



import app.beans.GamesDetail;
import app.helper.SQLHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameDetailModel {
    SQLHelper sql;
    
    public GameDetailModel(){
        sql = new SQLHelper();
    }
    
    public GamesDetail getGameDetails(GamesDetail g) {
        sql.openCon();
            
            ResultSet rs = sql.execQuery("SELECT userID, title,description, buttonConfig,credits,gameDuration,gameStarts,permanentStore FROM games WHERE ID='"+g.getGameID()+"'");
            try {
                if(rs.next()){
                    g.setUserID(rs.getInt("userID"));
                    g.setTitle(rs.getString("title"));
                    g.setDescription(rs.getString("description"));
                    g.setButtonConfig(rs.getString("buttonConfig"));
                    g.setCredits(rs.getString("credits"));
                    g.setGameDuration(rs.getInt("gameDuration"));
                    g.setGameStarts(rs.getInt("gameStarts"));
                    g.setPermanentStore(rs.getInt("permanentStore")); 
                }
                else{
                    
                    g = null;
                }
            } catch (SQLException ex) {
                Logger.getLogger(GameDetailModel.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        sql.closeCon();
        
        return g;
        
    }

    

   

 
}
