// Holt Games aus der Datenbank und stellt die als Liste da

package app.model;


import app.beans.Game;
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
    
    public GamesDetail listGames(Game g) {
        
        GamesDetail dgl = new GamesDetail();
        
        sql.openCon();
            
            ResultSet rs = sql.execQuery("SELECT ID, title FROM games WHERE gameID='"+g.getGameID()+"'");
            try {
                while(rs.next()){
                    dgl.addDetails(rs.getInt("ID"),rs.getString("title"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(GameDetailModel.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        sql.closeCon();
        
        return dgl;
        
    }

    

   

 
}
