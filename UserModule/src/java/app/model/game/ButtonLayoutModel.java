package app.model.game;

import app.beans.Game;
import app.helper.SQLHelper;


public class ButtonLayoutModel {
    
     public boolean updateButtonLayout(Game g){
        
        g.updateState("buttonlayout", "complete");
        String state = g.stateToJSON();
        String buttonLayout = g.buttonLayoutToJSON(); 
        
        
         SQLHelper sql = new SQLHelper();
        sql.openCon();
          boolean success = sql.execNonQuery("UPDATE `games` SET buttonConfig = '"+buttonLayout+"', editState='"+state+"' WHERE ID = "+ g.getGameID());
        sql.closeCon();
        
        return success;
    }
    
}
