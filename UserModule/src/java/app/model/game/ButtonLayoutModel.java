package app.model.game;

import app.beans.Game;
import app.helper.SQLHelper;
import java.sql.SQLException;


public class ButtonLayoutModel {
    
     public boolean updateButtonLayout(Game g){
        
        g.updateState("buttonlayout", "complete");
        String state = g.stateToJSON();
        String buttonLayout = g.buttonLayoutToJSON(); 
        
        try(SQLHelper sql = new SQLHelper()){
            sql.execNonQuery("UPDATE `games` SET buttonConfig = '"+buttonLayout+"', editState='"+state+"' WHERE ID = "+ g.getGameID());
         }
         catch(SQLException e){}
        
          
        
        
        return true;
    }
    
}
